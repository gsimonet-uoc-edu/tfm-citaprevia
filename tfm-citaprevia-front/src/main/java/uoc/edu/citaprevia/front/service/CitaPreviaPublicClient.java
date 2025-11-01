package uoc.edu.citaprevia.front.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.CalendariDto;
import uoc.edu.citaprevia.dto.SeleccioTipusCitaDto;
import uoc.edu.citaprevia.dto.SetmanaTipusDto;
import uoc.edu.citaprevia.dto.TipusCitaDto;

public interface CitaPreviaPublicClient {


	SeleccioTipusCitaDto getAllTipusCites(String subaplCoa, Locale locale);

	List<AgendaDto> getAgendasBySubaplicacioAndTipusCita(String subaplCoa, Long tipcitCon, Locale locale);

	CalendariDto getCalendariCites(String subaplCoa, Long tipCitCon, Locale locale);

	TipusCitaDto getTipusCita(Long tipCitCon, Locale locale);

	List<SetmanaTipusDto> getSetmanesTipusByHorari(Long horCon, Locale locale);
	

}
