package uoc.edu.citaprevia.front.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.CalendariDto;
import uoc.edu.citaprevia.dto.SeleccioTipusCitaDto;

public interface CitaPreviaPublicClient {


	SeleccioTipusCitaDto getAllTipusCites(String subaplCoa, Locale locale);

	List<AgendaDto> getAgendasByTipusCita(String subaplCoa, Long tipcitCon, Locale locale);

	CalendariDto getCalendariCites(String subaplCoa, Long tipCitCon, Locale locale);
	

}
