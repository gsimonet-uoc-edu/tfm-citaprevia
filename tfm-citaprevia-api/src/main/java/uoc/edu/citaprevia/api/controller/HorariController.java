package uoc.edu.citaprevia.api.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import uoc.edu.citaprevia.api.service.HorariService;
import uoc.edu.citaprevia.dto.HorariDto;

@RestController
@RequestMapping(value="/horaris")
public class HorariController {
	
	@Autowired
	private HorariService horariService;
	
	@GetMapping(value="/subaplicacions/{subaplCoa}")
	@Operation(summary="obtenir llistat d'horaris")
	public List<HorariDto> getHorarisBySubaplicacio(String subaplCoa,
													  Locale locale) {
		return horariService.getHorarisBySubaplicacio(subaplCoa, locale);
	}

}
