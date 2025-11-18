package uoc.edu.citaprevia.api.controller;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import uoc.edu.citaprevia.api.service.AgendaService;
import uoc.edu.citaprevia.api.service.TecnicService;
import uoc.edu.citaprevia.api.utils.ApiUtils;
import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.model.TipusError;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@RestController
@RequestMapping(value="/tecnics")
public class TecnicController {

	@Autowired
	private TecnicService tecnicService;
	
	@Autowired
	private AgendaService agendaService;
	
	@Autowired
	MessageSource messageSource;
	
	@GetMapping(value="/{coa}")
	@Operation(summary="obtenir un tècnic pel seu identificador")
	public TecnicDto getTecnic(@PathVariable String coa, 
											Locale locale) {
		return tecnicService.getTecnicByCoa(coa);
	}
	
	@GetMapping(value="/{coa}/agendes")
	@Operation(summary="obtenir agendes d'un tècnic")
	public List<AgendaDto> getAgendesByTecnic(@PathVariable String coa,
											   Locale locale) {
		return agendaService.getAgendesByTecnic(coa, locale);
	}
	
	@GetMapping(value="/subaplicacions/{subaplCoa}")
	@Operation(summary="obtenir els tècnics d'una subaplicació")
	public List<TecnicDto> getTecnicsSubaplicacio(@PathVariable String subaplCoa,
											      Locale locale) {
		return tecnicService.getTecnicsSubaplicacio(subaplCoa, locale);
	}
	
	@PostMapping("")
	@Operation(summary="donar d'alta un tècnic")
	public TecnicDto saveTecnic(@Valid @RequestBody TecnicDto dto,
								   BindingResult result,
								   Locale locale) {
		
		if (result.hasErrors()) {
			dto.setErrors(ApiUtils.getBindingResultErrors(result, messageSource, locale));
			return dto;
		}
		
		if (Utils.isEmpty(dto.getCoa())){
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL,TipusError.ERROR.getValor(),messageSource.getMessage(Constants.ERROR_API_CRUD_TECNIC, null, locale)));
			return dto;
		}
		
		return tecnicService.saveTecnic(dto, locale);
		
	}
	
	@PutMapping("/{coa}")
	@Operation(summary="actualitzar les dades d'un tècnic")
	public TecnicDto updateTecnic(@PathVariable String coa,
							      @Valid @RequestBody TecnicDto dto,
						          BindingResult result,
						          Locale locale) {
		
		
		if (result.hasErrors()) {
			dto.setErrors(ApiUtils.getBindingResultErrors(result, messageSource, locale));
			return dto;
		}
		
		if (Utils.isEmpty(dto.getCoa()) || !coa.equals(dto.getCoa())){
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL,TipusError.ERROR.getValor(),messageSource.getMessage(Constants.ERROR_API_CRUD_TECNIC, null, locale)));
			return dto;
		}
		
		return tecnicService.updateTecnic(dto, locale);
		
	}
	
}
