package uoc.edu.citaprevia.api.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import uoc.edu.citaprevia.api.service.UbicacioService;
import uoc.edu.citaprevia.dto.UbicacioDto;

@RestController
@RequestMapping(value="/ubicacions")
public class UbicacioController {
	
	@Autowired
	private UbicacioService ubicacioService;
	
	@GetMapping(value="")
	@Operation(summary="obtenir llista d'ubicacions")
	public List<UbicacioDto> getUbicacions(Locale locale) {
		return ubicacioService.getAllUbicacions(locale);
	}

}
