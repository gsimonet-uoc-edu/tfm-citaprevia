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
import uoc.edu.citaprevia.api.service.AgendaService;
import uoc.edu.citaprevia.api.utils.ApiUtils;
import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.model.TipusError;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@RestController
@RequestMapping(value="/agendes")
/**
 * Conjunt d'endpoints per gestionar les agendes
 */
public class AgendaController {
	
	@Autowired
	private AgendaService agendaService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(value="/tipus-cites/{tipCitCon}/subaplicacions/{subaplCoa}")
	@Operation(summary="obtenir agendes donada una subaplicació i un tipus de cita")
	public List<AgendaDto> getAgendesByTipusCitaAndSubaplicacio(@PathVariable Long tipCitCon,
																@PathVariable String subaplCoa,
																Locale locale) {
		return agendaService.getAgendesByTipusCitaAndSubaplicacio(tipCitCon, subaplCoa, locale);
	}
	
	@GetMapping(value="/obertes/tipus-cites/{tipCitCon}/subaplicacions/{subaplCoa}")
	@Operation(summary="obtenir agendes obertes donada una subaplicació i un tipus de cita")
	public List<AgendaDto> getAgendesObertesByTipusCitaAndSubaplicacio(@PathVariable Long tipCitCon,
																	   @PathVariable String subaplCoa,
																       Locale locale) {
		return agendaService.getAgendesObertesByTipusCitaAndSubaplicacio(tipCitCon, subaplCoa, locale);
	}
	
	@GetMapping(value="/subaplicacions/{subaplCoa}")
	@Operation(summary="obtenir agendes donada una subaplicació")
	public List<AgendaDto> getAgendesBySubaplicacio(@PathVariable String subaplCoa,
																Locale locale) {
		return agendaService.getAgendesBySubaplicacio(subaplCoa, locale);
	}
	
	@GetMapping(value="/{con}")
	@Operation(summary="obtenir una genda")
	public AgendaDto getAgenda(@PathVariable Long con,
							Locale locale) {
		return agendaService.getAgenda(con, locale);
	}


	@PostMapping("")
	@Operation(summary="donar d'alta una agenda")
	public AgendaDto saveAgenda(@Valid @RequestBody AgendaDto dto,
								   BindingResult result,
								   Locale locale) {
		
		if (result.hasErrors()) {
			dto.setErrors(ApiUtils.getBindingResultErrors(result, messageSource, locale));
			return dto;
		}
		
		if (!Utils.isEmpty(dto.getCon())){
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL,TipusError.ERROR.getValor(),messageSource.getMessage(Constants.ERROR_API_CRUD_AGENDA, null, locale)));
			return dto;
		}
		
		return agendaService.saveAgenda(dto, locale);
		
	}
	
	@PutMapping("/{con}")
	@Operation(summary="actualitzar les dades d'una agenda")
	public AgendaDto updateAgenda(@PathVariable Long con,
							      @Valid @RequestBody AgendaDto dto,
						          BindingResult result,
						          Locale locale) {
		
		
		if (result.hasErrors()) {
			dto.setErrors(ApiUtils.getBindingResultErrors(result, messageSource, locale));
			return dto;
		}
		
		if (Utils.isEmpty(dto.getCon()) || !con.equals(dto.getCon())){
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL,TipusError.ERROR.getValor(),messageSource.getMessage(Constants.ERROR_API_CRUD_AGENDA, null, locale)));
			return dto;
		}
		
		return agendaService.updateAgenda(dto, locale);
		
	}
	
	
	@DeleteMapping("/{con}")
	@Operation(summary="eliminar una agenda per codi")
	public ErrorDto deleteAgenda(@PathVariable Long con,
				 			    Locale locale) {
		
		return  agendaService.deleteAgenda(con, locale);
		 
	}
	
	
}
