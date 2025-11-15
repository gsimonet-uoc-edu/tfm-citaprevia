package uoc.edu.citaprevia.api.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import uoc.edu.citaprevia.api.service.TipusCitaService;
import uoc.edu.citaprevia.dto.TipusCitaDto;

@RestController
@RequestMapping(value="/tipus-cites")
public class TipusCitaController {
	
	@Autowired
	private TipusCitaService tipusCitaService;
	
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
}
