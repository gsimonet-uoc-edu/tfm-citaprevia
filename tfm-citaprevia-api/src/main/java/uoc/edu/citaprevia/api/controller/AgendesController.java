package uoc.edu.citaprevia.api.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import uoc.edu.citaprevia.api.service.AgendaService;
import uoc.edu.citaprevia.dto.AgendaDto;

@RestController
@RequestMapping(value="/agendes")
public class AgendesController {
	
	@Autowired
	private AgendaService agendaService;
	
	@GetMapping(value="/tipus-cites/{tipCitCon}/subaplicacions/{subaplCoa}")
	@Operation(summary="obtenir agendes donada una subaplicació i un tipus de cita")
	public List<AgendaDto> getAgendesByTipusCitaAndSubaplicacio(@PathVariable Long tipCitCon,
																@PathVariable String subaplCoa,
																Locale locale) {
		return agendaService.getAgendesByTipusCitaAndSubaplicacio(tipCitCon, subaplCoa, locale);
	}
	
	@GetMapping(value="/{con}")
	@Operation(summary="obtenir una genda")
	public AgendaDto getAgenda(@PathVariable Long con,
							Locale locale) {
		return agendaService.getAgenda(con, locale);
	}


}
