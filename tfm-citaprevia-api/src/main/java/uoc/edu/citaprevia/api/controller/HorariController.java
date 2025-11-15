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
import uoc.edu.citaprevia.api.service.HorariService;
import uoc.edu.citaprevia.api.utils.ApiUtils;
import uoc.edu.citaprevia.dto.HorariDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.model.TipusError;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@RestController
@RequestMapping(value="/horaris")
public class HorariController {
	
	@Autowired
	private HorariService horariService;
	
	@Autowired
	private MessageSource messageSource;
	
	@GetMapping(value="/subaplicacions/{subaplCoa}")
	@Operation(summary="obtenir llistat d'horaris")
	public List<HorariDto> getHorarisBySubaplicacio(@PathVariable String subaplCoa,
													Locale locale) {
		return horariService.getHorarisBySubaplicacio(subaplCoa, locale);
	}
	
	@PostMapping("")
	@Operation(summary="donar d'alta un horari")
	public HorariDto saveHorari(@Valid @RequestBody HorariDto dto,
								   BindingResult result,
								   Locale locale) {
		
		if (result.hasErrors()) {
			dto.setErrors(ApiUtils.getBindingResultErrors(result, messageSource, locale));
			return dto;
		}
		
		if (!Utils.isEmpty(dto.getCon())){
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL,TipusError.ERROR.getValor(),messageSource.getMessage(Constants.ERROR_API_CRUD_HORARI, null, locale)));
			return dto;
		}
		
		return horariService.saveHorari(dto, locale);
		
	}
	
	@PutMapping("/{con}")
	@Operation(summary="actualitzar les dades d'un horari")
	public HorariDto updateHorari(@PathVariable Long con,
							      @Valid @RequestBody HorariDto dto,
						          BindingResult result,
						          Locale locale) {
		
		
		if (result.hasErrors()) {
			dto.setErrors(ApiUtils.getBindingResultErrors(result, messageSource, locale));
			return dto;
		}
		
		if (Utils.isEmpty(dto.getCon()) || !con.equals(dto.getCon())){
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL,TipusError.ERROR.getValor(),messageSource.getMessage(Constants.ERROR_API_CRUD_HORARI, null, locale)));
			return dto;
		}
		
		return horariService.updateHorari(dto, locale);
		
	}
	
	
	@DeleteMapping("/{con}")
	@Operation(summary="eliminar un horari per codi")
	public ErrorDto deleteHorari(@PathVariable Long con,
				 			    Locale locale) {
		
		return  horariService.deleteHorari(con, locale);
		 
	}

}
