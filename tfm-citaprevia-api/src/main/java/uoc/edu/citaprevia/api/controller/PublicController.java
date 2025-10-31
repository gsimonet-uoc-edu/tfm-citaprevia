package uoc.edu.citaprevia.api.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import uoc.edu.citaprevia.api.service.CalendariService;
import uoc.edu.citaprevia.api.service.TipusCitaService;
import uoc.edu.citaprevia.dto.CalendariDto;
import uoc.edu.citaprevia.dto.SeleccioTipusCitaDto;

@RestController
@RequestMapping(value="/public")
public class PublicController {
	
	@Autowired
	private TipusCitaService tipusCitaService;
	
	@Autowired
	private CalendariService calendariService;
	
	@GetMapping(value={"/subaplicacions/{subaplCoa}/selecciotipuscites"})
	@Operation(summary="Obté un objecte que conté una llista de tipus de cita donada una subaplicació")
	public SeleccioTipusCitaDto getAllTipusCites(@PathVariable String subaplCoa,
												  Locale locale){
		SeleccioTipusCitaDto gestioCita= new SeleccioTipusCitaDto();
		gestioCita.setTipusCites(tipusCitaService.getAllTipusCitaBySubaplCoa(subaplCoa, locale));
		return gestioCita;
		
	}
	
	@GetMapping(value={"/calendaris/subaplicacions/{subaplCoa}/tipus-cites/{tipCitCon}"})
	@Operation(summary="Obté el calendari de cites")
	public CalendariDto getCalendari(@PathVariable String subaplCoa,
									 @PathVariable Long tipCitCon,
									  Locale locale){
		CalendariDto calendari= new CalendariDto();
		calendari = calendariService.getCalendariCites(subaplCoa, tipCitCon, locale);
		return calendari;
		
	}

}
