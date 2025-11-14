package uoc.edu.citaprevia.api.service;

import java.time.LocalDateTime;
import java.util.Locale;

import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;

public interface CitaService {

	CitaDto getCita(Long con);

	CitaDto saveCita(CitaDto cita, Locale locale);

	CitaDto existeixCitaAgenda(Long ageCon, LocalDateTime inici, LocalDateTime fi, Long tipcitCon, Locale locale);

	ErrorDto deleteCitaPersona(Long con, String numdoc, Locale locale);

	ErrorDto deleteCita(Long con, Locale locale);

	CitaDto updateCita(CitaDto cita, Locale locale);

}
