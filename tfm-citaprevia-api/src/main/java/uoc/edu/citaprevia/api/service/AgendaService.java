package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.AgendaDto;

public interface AgendaService {

	List<AgendaDto> getAgendesByTipusCitaAndSubaplicacio(Long tipCitCon, String subaplCoa, Locale locale);

	AgendaDto getAgenda(Long con, Locale locale);

}
