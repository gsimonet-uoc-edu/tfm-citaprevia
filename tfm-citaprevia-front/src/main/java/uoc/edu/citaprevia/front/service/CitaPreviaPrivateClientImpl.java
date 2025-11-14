package uoc.edu.citaprevia.front.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.front.util.RestTemplateResponseErrorHandler;

@Service("citprePrivateService")
public class CitaPreviaPrivateClientImpl implements CitaPreviaPrivateClient{

	@Value("${citaprevia.service.api.host}")
	private String citaPreviaApi;
	
	private String getBaseApiUrl() { return citaPreviaApi; }
	
	private static final String PARAM_SUBAPL_COA = "subaplCoa";
	private static final String PARAM_TIPCIT_CON = "tipCitCon";
	private static final String PARAM_AGE_CON = "ageCon";
	private static final String PARAM_HOR_CON = "horCon";
	private static final String DATA_HORA_INICI = "dathorini";
	private static final String DATA_HORA_FINAL = "dathorfin";
	private static final String PARAM_TEC_COA = "tecCoa";
	private static final String PARAM_LOCALE = "lang";
	private static final String PARAM_CIT_CON = "citCon";
	
	
	private RestTemplate restTemplate = new RestTemplate();
	public CitaPreviaPrivateClientImpl (RestTemplateBuilder restTemplateBuilder) {
	    this.restTemplate = restTemplateBuilder
		    .errorHandler(new RestTemplateResponseErrorHandler())
		    .build();
	}
	
	@Override
	public TecnicDto getTecnic (String tecCoa, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_TEC_COA, tecCoa);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/tecnics/{tecCoa}?lang={lang}";
		return restTemplate.getForObject(url, TecnicDto.class, params);
	}
	
	@Override
	public List<AgendaDto> getAgendasBySubaplicacio(String subaplCoa, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_SUBAPL_COA, subaplCoa);		
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/agendes/subaplicacions/{subaplCoa}?lang={lang}";	
		AgendaDto[] list = restTemplate.getForObject(url, AgendaDto[].class, params);
		return list == null ? new ArrayList<>() : Arrays.asList(list);
	}
	
	@Override
	public List<AgendaDto> getAgendasByTecnic(String tecCoa, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_TEC_COA, tecCoa);		
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/tecnics/{tecCoa}/agendes?lang={lang}";	
		AgendaDto[] list = restTemplate.getForObject(url, AgendaDto[].class, params);
		return list == null ? new ArrayList<>() : Arrays.asList(list);
	}
	
	@Override
	public CitaDto updateCita(Long con, CitaDto dto, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_CIT_CON, con);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/cites/{citCon}/?lang={lang}";	
	    ResponseEntity<CitaDto> response = restTemplate.exchange(url, 
		        HttpMethod.PUT, 
		        new HttpEntity<>(dto),               
		        CitaDto.class,     
		        params             
		    );
		return response.getBody() == null ? new CitaDto() : response.getBody();
	}
	
	@Override
	public ErrorDto deleteCita(Long citCon, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_CIT_CON, citCon);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/cites/{citCon}?lang={lang}";
	    ResponseEntity<ErrorDto> response = restTemplate.exchange(url, 
	        HttpMethod.DELETE, 
	        null,               
	        ErrorDto.class,     
	        params             
	    );

	    return response.getBody();
	}
	
}
