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
	
	
	public static List<ErrorDto> getBindingResultErrors (BindingResult result, MessageSource messageSource, Locale locale) {
		List<ErrorDto> errors = new ArrayList<ErrorDto>();
		if (result.hasErrors()) {	        
	        // 1. ITERAR SOBRE ELS ERRORS DEL BINDING
	        for (ObjectError error : result.getAllErrors()) {
	            
	            // Obté el nom del camp (només per a FieldErrors)
	            String fieldName = (error instanceof FieldError) ? ((FieldError) error).getField() : "global";
	            
	            // 2. OBTENIR EL MISSATGE D'ERROR LOCALITZAT
	            // El MessageSource pot resoldre els missatges definits a les anotacions de validació.
	            String errorMessage = messageSource.getMessage(error, locale);

	            // 3. CREAR UN OBJECTE ERROR DETALLAT
	            // S'utilitza el nom del camp com a codi i el missatge resolt com a descripció.
	            // S'assumeix que TipusError i Constants estan correctament definits.
	            ErrorDto errorDto = new ErrorDto(
	                9999L, // CODI_ERROR_FATAL no és adequat, utilitzem el nom del camp o "global"
	                TipusError.ERROR.getValor(), // Tipus d'error
	                fieldName + ": " + errorMessage // Descripció de l'error
	            );
	            
	            errors.add(errorDto);
	        }

	    }      
        // Retorna el DTO amb la llista d'errors omplerta
        return errors;
	}

}
