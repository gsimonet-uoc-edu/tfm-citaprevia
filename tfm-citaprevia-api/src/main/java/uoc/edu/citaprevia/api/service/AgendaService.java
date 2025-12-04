package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;

public interface AgendaService {

	List<AgendaDto> getAgendesByTipusCitaAndSubaplicacio(Long tipCitCon, String subaplCoa, Locale locale);

	AgendaDto getAgenda(Long con, Locale locale);

	List<AgendaDto> getAgendesBySubaplicacio(String subaplCoa, Locale locale);

	List<AgendaDto> getAgendesByTecnic(String tecCoa, Locale locale);

	AgendaDto saveAgenda(AgendaDto agenda, Locale locale);

	AgendaDto updateAgenda(AgendaDto agenda, Locale locale);

	ErrorDto deleteAgenda(Long con, Locale locale);

	List<AgendaDto> getAgendesObertesByTipusCitaAndSubaplicacio(Long tipCitCon, String subaplCoa, Locale locale);

}
