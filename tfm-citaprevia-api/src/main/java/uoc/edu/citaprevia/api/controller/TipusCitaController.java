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
import uoc.edu.citaprevia.api.service.TipusCitaService;
import uoc.edu.citaprevia.api.utils.ApiUtils;
import uoc.edu.citaprevia.dto.SeleccioTipusCitaDto;
import uoc.edu.citaprevia.dto.TipusCitaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.model.TipusError;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@RestController
@RequestMapping(value="/tipus-cites")
/**
 * Conjunts d'endpoints per gestiones els tipus de cites
 */
public class TipusCitaController {
	
	@Autowired
	private TipusCitaService tipusCitaService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(value="/{con}")
	@Operation(summary="obtenir una tipus de cita pel seu identificador")
	public TipusCitaDto getTipusCita(@PathVariable Long con
						  	    ,Locale locale) {
		return tipusCitaService.getTipusCitaByCon(con, locale);
	}

	
	@GetMapping(value="/subaplicacions/{subaplCoa}")
	@Operation(summary="obtenir tipus de cites donat una subaplicacio")
	public List<TipusCitaDto> getTipusCitesBySubaplCoa(@PathVariable String subaplCoa,
													  Locale locale) {
		return tipusCitaService.getAllTipusCitaBySubaplCoa(subaplCoa, locale);
	}
	
	
	@PostMapping("")
	@Operation(summary="donar d'alta un tipus de cita")
	public TipusCitaDto saveTipusCita(@Valid @RequestBody TipusCitaDto dto,
								   BindingResult result,
								   Locale locale) {
		
		if (result.hasErrors()) {
			dto.setErrors(ApiUtils.getBindingResultErrors(result, messageSource, locale));
			return dto;
		}
		
		if (!Utils.isEmpty(dto.getCon())){
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL,TipusError.ERROR.getValor(),messageSource.getMessage(Constants.ERROR_API_CRUD_TIPUS_CITA, null, locale)));
			return dto;
		}
		
		return tipusCitaService.saveTipusCita(dto, locale);
		
	}
	
	@PutMapping("/{con}")
	@Operation(summary="actualitzar les dades d'un tipus de cita")
	public TipusCitaDto updateTipusCita(@PathVariable Long con,
							      @Valid @RequestBody TipusCitaDto dto,
						          BindingResult result,
						          Locale locale) {
		
		
		if (result.hasErrors()) {
			dto.setErrors(ApiUtils.getBindingResultErrors(result, messageSource, locale));
			return dto;
		}
		
		if (Utils.isEmpty(dto.getCon()) || !con.equals(dto.getCon())){
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL,TipusError.ERROR.getValor(),messageSource.getMessage(Constants.ERROR_API_CRUD_TIPUS_CITA, null, locale)));
			return dto;
		}
		
		return tipusCitaService.updateTipusCita(dto, locale);
		
	}
	
	
	@DeleteMapping("/{con}")
	@Operation(summary="eliminar una tipus de cita per codi")
	public ErrorDto deleteTipusCita(@PathVariable Long con,
				 			    	Locale locale) {
		
		return  tipusCitaService.deleteTipusCita(con, locale);
		 
	}
	
	@GetMapping(value={"/subaplicacions/{subaplCoa}/selecciotipuscites"})
	@Operation(summary="Obté un objecte que conté una llista de tipus de cita donada una subaplicació")
	public SeleccioTipusCitaDto getAllTipusCites(@PathVariable String subaplCoa,
												  Locale locale){
		SeleccioTipusCitaDto gestioCita= new SeleccioTipusCitaDto();
		gestioCita.setTipusCites(tipusCitaService.getAllTipusCitaBySubaplCoa(subaplCoa, locale));
		return gestioCita;
		
	}
}
