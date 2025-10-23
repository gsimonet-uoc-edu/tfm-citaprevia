package uoc.edu.citaprevia.api.service;

import java.util.Locale;

import uoc.edu.citaprevia.dto.CitaDto;

public interface CitaPreviaService {

	CitaDto getCita(Long con);

	CitaDto saveCita(CitaDto cita, Locale locale);

}
