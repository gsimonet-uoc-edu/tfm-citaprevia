package uoc.edu.citaprevia.front.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.TecnicDto;

public interface CitaPreviaPrivateClient {

	TecnicDto getTecnic(String tecCoa, Locale locale);

	List<AgendaDto> getAgendasBySubaplicacio(String subaplCoa, Locale locale);

	List<AgendaDto> getAgendasByTecnic(String tecCoa, Locale locale);

}
