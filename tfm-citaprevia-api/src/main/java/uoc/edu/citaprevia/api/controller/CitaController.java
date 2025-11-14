package uoc.edu.citaprevia.api.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import uoc.edu.citaprevia.api.service.CitaService;
import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.model.TipusError;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@RestController
@RequestMapping(value="/cites")
public class CitaController {
	
	@Autowired
	private CitaService citaService;
	
	@Autowired
	MessageSource messageSource;
	
	@GetMapping(value="/{con}")
	@Operation(summary="obtenir una cita pel seu identificador")
	public CitaDto getCita(@PathVariable Long con
						  ,Locale locale) {
		return citaService.getCita(con);
	}
	
	@DeleteMapping("/{con}/documents-identificatius/{numdoc}")
	@Operation(summary="eliminar una cita assignada a un número de document d'una persona")
	public ErrorDto deleteCitaPersona(@PathVariable Long con,
										 @PathVariable String numdoc,
				 			 		     Locale locale) {
		
		return  citaService.deleteCitaPersona(con, numdoc, locale);
		 
	}
	
	@DeleteMapping("/{con}")
	@Operation(summary="eliminar una cita per codi")
	public ErrorDto deleteCita(@PathVariable Long con,
				 			    Locale locale) {
		
		return  citaService.deleteCita(con, locale);
		 
	}
	
	@PostMapping("")
	@Operation(summary="donar d'alta un cita")
	public CitaDto saveCita(@Valid @RequestBody CitaDto dto,
								   BindingResult result,
								   Locale locale) {
		
		/*if (result.hasErrors()) {
			
			return dto;
		}*/
		
		if (!Utils.isEmpty(dto.getCon())){
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL,TipusError.ERROR.getValor(),messageSource.getMessage("error.alta.cita", null, locale)));
			return dto;
		}
		
		return citaService.saveCita(dto, locale);
		
	}
	
	@PutMapping("/{con}")
	@Operation(summary="actualitzar les dades d'una cita")
	public CitaDto updateCita(@PathVariable Long con,
							@Valid @RequestBody CitaDto dto,
						    BindingResult result,
						    Locale locale) {
		
		/*if (result.hasErrors()) {
			
			return dto;
		}*/
		
		if (Utils.isEmpty(dto.getCon()) || !con.equals(dto.getCon())){
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL,TipusError.ERROR.getValor(),messageSource.getMessage("error.alta.cita", null, locale)));
			return dto;
		}
		
		return citaService.updateCita(dto, locale);
		
	}
	

	
	@GetMapping(value="/exists/agendes/{ageCon}/tipus-cites/{tipCitCon}")
	@Operation(summary="comprovar si existeix una cita ja ocupada")
	public CitaDto existeixCitaAgenda(@PathVariable Long ageCon,
								   @PathVariable Long tipCitCon,
								   @RequestParam(required = true) String dathorini,
								   @RequestParam(required = true) String dathorfin,
									Locale locale) { // Exemple format: 2024-03-14T01:00:00
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.PATTERN_FORMAT_LOCAL_DATE_TIME);
        LocalDateTime datini = LocalDateTime.parse(dathorini, formatter);
        LocalDateTime datfin = LocalDateTime.parse(dathorfin, formatter);
        return citaService.existeixCitaAgenda(ageCon, datini, datfin, tipCitCon, locale);
	}
        
}
