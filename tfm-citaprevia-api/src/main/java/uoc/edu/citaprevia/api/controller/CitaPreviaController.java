package uoc.edu.citaprevia.api.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import uoc.edu.citaprevia.api.service.CitaPreviaService;
import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.util.Utils;

@RestController
@RequestMapping(value="/cites")
public class CitaPreviaController {
	
	@Autowired
	private CitaPreviaService citaPreviaService;
	
	@GetMapping(value="/{con}")
	@Operation(summary="obtenir una cita pel seu identificador")
	public CitaDto getCita(@PathVariable Long con
						  ,Locale locale) {
		return citaPreviaService.getCita(con);
	}
	
	@PostMapping("/cites")
	@Operation(summary="donar d'alta un cita")
	public CitaDto saveCita(@Valid @RequestBody CitaDto dto,
								   BindingResult result,
								   Locale locale) {
		
		if (result.hasErrors()) {
			
			return dto;
		}
		
		if (!Utils.isEmpty(dto.getCon())){

			return dto;
		}
		
		return citaPreviaService.saveCita(dto, locale);
		
	}

}
