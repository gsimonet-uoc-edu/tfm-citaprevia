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
import uoc.edu.citaprevia.api.service.SetmanaTipusService;
import uoc.edu.citaprevia.api.utils.ApiUtils;
import uoc.edu.citaprevia.dto.HorariDto;
import uoc.edu.citaprevia.dto.SetmanaTipusDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.model.TipusError;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@RestController
@RequestMapping(value="/setmanes-tipus")
public class SetmanaTipusController {
	
	@Autowired
	private SetmanaTipusService setmanaTipusService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(value="/horaris/{horCon}")
	@Operation(summary="obtenir les setmanes tipus donat un horari")
	public List<SetmanaTipusDto> getSetmanesTipusByHorari(@PathVariable Long horCon,
														  Locale locale) {
		return setmanaTipusService.getSetmanaTipusByHorari(horCon, locale);
	}

	@PostMapping("")
	@Operation(summary="afegir una setmana tipus (franja) a un horari")
	public SetmanaTipusDto addSetmanaTipusToHorari(@Valid @RequestBody SetmanaTipusDto dto,
								   					BindingResult result,
								   					Locale locale) {
		
		if (result.hasErrors()) {
			dto.setErrors(ApiUtils.getBindingResultErrors(result, messageSource, locale));
			return dto;
		}
		
		if (dto == null || dto.getHorari() == null || Utils.isEmpty(dto.getHorari().getCon()) || Utils.isEmpty(dto.getDiasetCon()) || dto.getHorini() == null || dto.getHorfin() == null) {
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL,TipusError.ERROR.getValor(),messageSource.getMessage(Constants.ERROR_API_CRUD_SETMANES_TIPUS, null, locale)));
			return dto;
		}
		
		return setmanaTipusService.saveSetmanaTipus(dto, locale);
		
	}
	
	@PutMapping("/horaris/{horCon}")
	@Operation(summary="actualitzar les dades d'una setmana tipus (franja) a un horari")
	public SetmanaTipusDto updateSetmanaTipusToHorari(@PathVariable Long horCon,
								      				  @Valid @RequestBody SetmanaTipusDto dto,
								      				  BindingResult result,
								      				  Locale locale) {
		
		
		if (result.hasErrors()) {
			dto.setErrors(ApiUtils.getBindingResultErrors(result, messageSource, locale));
			return dto;
		}
		
		if (dto.getHorari() == null || Utils.isEmpty(dto.getHorari().getCon()) || !horCon.equals(dto.getHorari().getCon())){
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL,TipusError.ERROR.getValor(),messageSource.getMessage(Constants.ERROR_API_CRUD_SETMANES_TIPUS, null, locale)));
			return dto;
		}
		
		return setmanaTipusService.updateSetmanaTipus(dto, locale);
		
	}
	
}
