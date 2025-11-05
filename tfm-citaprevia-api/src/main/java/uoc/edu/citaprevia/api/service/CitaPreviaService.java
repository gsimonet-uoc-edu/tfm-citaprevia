package uoc.edu.citaprevia.api.service;

import java.time.LocalDateTime;
import java.util.Locale;

import uoc.edu.citaprevia.dto.CitaDto;

public interface CitaPreviaService {

	CitaDto getCita(Long con);

	CitaDto saveCita(CitaDto cita, Locale locale);

	CitaDto existeixCitaAgenda(Long ageCon, LocalDateTime inici, LocalDateTime fi, Long tipcitCon, Locale locale);

}
