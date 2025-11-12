package uoc.edu.citaprevia.api.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import uoc.edu.citaprevia.api.service.TecnicService;
import uoc.edu.citaprevia.dto.TecnicDto;

@RestController
@RequestMapping(value="/tecnics")
public class TecnicController {

	@Autowired
	private TecnicService tecnicService;
	
	@GetMapping(value="/{coa}")
	@Operation(summary="obtenir un tècnic pel seu identificador")
	public TecnicDto getTecnic(@PathVariable String coa, 
											Locale locale) {
		return tecnicService.getTecnicByCoa(coa);
	}
	
}
