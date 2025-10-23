package uoc.edu.citaprevia.api.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import uoc.edu.citaprevia.api.service.CitaPreviaService;
import uoc.edu.citaprevia.dto.CitaDto;

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

}
