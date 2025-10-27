package uoc.edu.citaprevia.front.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.util.Utils;


public class RestTemplateResponseErrorHandler extends DefaultResponseErrorHandler  {
		private ObjectMapper jacksonObjectMapper = new ObjectMapper();
		
		private static final Logger LOG = LoggerFactory.getLogger(RestTemplateResponseErrorHandler.class);
		
	    @Override
	    public void handleError(ClientHttpResponse response) throws IOException {
	        HttpStatus statusCode = HttpStatus.resolve(response.getRawStatusCode());
			if (statusCode == null) {
				throw new ImiRestException(new ErrorDto((long)response.getRawStatusCode(), "E", body(response)),
					response.getRawStatusCode());
			}
			handleError(response, statusCode);
	    }
	    @Override
	    protected void handleError(ClientHttpResponse response, HttpStatus statusCode) throws IOException {
	    	ErrorDto error = null;
	    	Series serie = response.getStatusCode().series();
	    	if (serie == HttpStatus.Series.SERVER_ERROR || serie == HttpStatus.Series.CLIENT_ERROR) {
	    		error = bodyToImiErrorDto(body(response), statusCode.value());
	        }
	    	if(LOG.isInfoEnabled()) {
	    		LOG.info("### handleError: serie = (name={}, value={}), error={}, statusCode={}",
	    			serie.name(), serie.value(),
	    			(error!=null ? error.toString() : null),
	    			(statusCode!=null ? ("(reasonPhrase= " + statusCode.getReasonPhrase() + ", name= " + statusCode.name() + ", value= " + statusCode.value() + ")") : null));
	    	}
	    	throw new ImiRestException(error, statusCode!=null ? statusCode.value() : null); // statusCode no pot ser null
		}

	    private String body(ClientHttpResponse response) {
	    	if(response == null) { return ""; }
	    	byte[] body = getResponseBody(response);
	    	return new String(body, StandardCharsets.UTF_8);
	    }

		private ErrorDto bodyToImiErrorDto(String httpBody, int status) throws IOException { 
			String body = Utils.getStr(httpBody);
			ErrorDto error = null;
			if(body.indexOf("\"con\" :") >= 0) { // ImiErrorDto
				try {
					error = jacksonObjectMapper.readValue(body, ErrorDto.class);
				} catch(JsonProcessingException e) {
					error = new ErrorDto((long)status, body); // Tractament igual que a Altra (no ImiErrorDto).
				}
			} else { // Altra
				error = new ErrorDto((long)status, body); // Convertir el body a un objecte i mostrar-ho de forma distinta de JSON?
			}
			return error;
		}
	}
