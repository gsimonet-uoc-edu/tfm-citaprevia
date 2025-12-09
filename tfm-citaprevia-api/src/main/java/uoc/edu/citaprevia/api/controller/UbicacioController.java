package uoc.edu.citaprevia.api.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import uoc.edu.citaprevia.api.service.UbicacioService;
import uoc.edu.citaprevia.api.utils.ApiUtils;
import uoc.edu.citaprevia.dto.UbicacioDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.model.TipusError;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@RestController
@RequestMapping(value="/ubicacions")
/**
 * Conjunt d'endpoints per gestionar les ubicacions
 */
public class UbicacioController {
	
	@Autowired
	private UbicacioService ubicacioService;
	
	@Autowired
	MessageSource messageSource;
	
	@GetMapping(value="")
	@Operation(summary="obtenir llista d'ubicacions")
	public List<UbicacioDto> getUbicacions(Locale locale) {
		return ubicacioService.getAllUbicacions(locale);
	}
	
	@GetMapping(value="/subaplicacions/{subaplCoa}")
	@Operation(summary="obtenir llista d'ubicacions d'una subaplicació")
	public List<UbicacioDto> getUbicacionsBySubaplicacio(@PathVariable String subaplCoa, Locale locale) {
		return ubicacioService.getUbicacionsBySubaplicacio(subaplCoa, locale);
	}

	@PostMapping("")
	@Operation(summary="donar d'alta una ubicació")
	public UbicacioDto saveUbicacio(@Valid @RequestBody UbicacioDto dto,
								   BindingResult result,
								   Locale locale) {
		
		if (result.hasErrors()) {
			dto.setErrors(ApiUtils.getBindingResultErrors(result, messageSource, locale));
			return dto;
		}
		
		if (!Utils.isEmpty(dto.getCon())){
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL,TipusError.ERROR.getValor(),messageSource.getMessage(Constants.ERROR_API_CRUD_UBICACIONS, null, locale)));
			return dto;
		}
		
		return ubicacioService.saveUbicacio(dto, locale);
		
	}
	
	@PutMapping("/{con}")
	@Operation(summary="actualitzar les dades d'una ubicació")
	public UbicacioDto updateUbicacii(@PathVariable Long con,
							      @Valid @RequestBody UbicacioDto dto,
						          BindingResult result,
						          Locale locale) {
		
		
		if (result.hasErrors()) {
			dto.setErrors(ApiUtils.getBindingResultErrors(result, messageSource, locale));
			return dto;
		}
		
		if (Utils.isEmpty(dto.getCon()) || !con.equals(dto.getCon())){
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL,TipusError.ERROR.getValor(),messageSource.getMessage(Constants.ERROR_API_CRUD_UBICACIONS, null, locale)));
			return dto;
		}
		
		return ubicacioService.updateUbicacio(dto, locale);
		
	}
	
	
	@DeleteMapping("/{con}")
	@Operation(summary="eliminar una ubicació per codi")
	public ErrorDto deleteUbicacio(@PathVariable Long con,
				 			    	Locale locale) {
		
		return  ubicacioService.deleteUbicacio(con, locale);
		 
	}
	
}
