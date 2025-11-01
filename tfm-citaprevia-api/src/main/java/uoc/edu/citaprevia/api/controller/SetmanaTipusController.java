package uoc.edu.citaprevia.api.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import uoc.edu.citaprevia.api.service.SetmanaTipusService;
import uoc.edu.citaprevia.dto.SetmanaTipusDto;

@RestController
@RequestMapping(value="/setmanes-tipus")
public class SetmanaTipusController {
	
	@Autowired
	private SetmanaTipusService setmanaTipusService;
	
	@GetMapping(value="/horaris/{horCon}")
	@Operation(summary="obtenir les setmanes tipus donat un horari")
	public List<SetmanaTipusDto> getSetmanesTipusByHorari(@PathVariable Long horCon,
																	  Locale locale) {
		return setmanaTipusService.getSetmanaTipusByHorari(horCon, locale);
	}

}
