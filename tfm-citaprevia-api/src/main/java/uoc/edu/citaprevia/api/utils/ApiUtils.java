package uoc.edu.citaprevia.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.model.TipusError;

public class ApiUtils {
	
	
	/**
	 * Mètode genèric i típic per obtenir els errors del bindings
	 * @param result
	 * @param messageSource
	 * @param locale
	 * @return
	 */
	public static List<ErrorDto> getBindingResultErrors (BindingResult result, MessageSource messageSource, Locale locale) {
		List<ErrorDto> errors = new ArrayList<ErrorDto>();
		if (result.hasErrors()) {	        

			for (ObjectError error : result.getAllErrors()) {
	            
	            // Obtenir el nom del camp 
	            String fieldName = (error instanceof FieldError) ? ((FieldError) error).getField() : "global";
	            
	            // Obtenir missatge
	            String errorMessage = messageSource.getMessage(error, locale);

	            // Crear error
	            ErrorDto errorDto = new ErrorDto(9999L, TipusError.ERROR.getValor(), fieldName + ": " + errorMessage
	            );
	            
	            errors.add(errorDto);
	        }

	    }      
        return errors;
	}

}
