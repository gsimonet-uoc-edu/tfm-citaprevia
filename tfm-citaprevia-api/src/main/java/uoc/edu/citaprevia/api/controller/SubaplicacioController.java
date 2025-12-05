package uoc.edu.citaprevia.api.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import uoc.edu.citaprevia.api.service.SubaplicacioService;
import uoc.edu.citaprevia.dto.SubaplicacioDto;

@RestController
@RequestMapping(value="/subaplicacions")
/**
 * Conjunt d'endpoint per gestionar les subaplicacions
 */
public class SubaplicacioController {
	
	@Autowired
	private SubaplicacioService subaplicacioService;
	
	@GetMapping(value="/{coa}")
	@Operation(summary="obtenir una subaplicació pel seu identificador")
	public SubaplicacioDto getSubaplicacio(@PathVariable String coa, 
											Locale locale) {
		return subaplicacioService.getSubaplicacioByCoa(coa);
	}

}
