package uoc.edu.citaprevia.front.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.CalendariDto;
import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.dto.SeleccioTipusCitaDto;
import uoc.edu.citaprevia.dto.SetmanaTipusDto;
import uoc.edu.citaprevia.dto.SubaplicacioDto;
import uoc.edu.citaprevia.dto.TipusCitaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;

public interface CitaPreviaPublicClient {


	SeleccioTipusCitaDto getAllTipusCites(String subaplCoa, Locale locale);

	List<AgendaDto> getAgendasBySubaplicacioAndTipusCita(String subaplCoa, Long tipcitCon, Locale locale);

	CalendariDto getCalendariCites(String subaplCoa, Long tipCitCon, Locale locale);

	TipusCitaDto getTipusCita(Long tipCitCon, Locale locale);

	List<SetmanaTipusDto> getSetmanesTipusByHorari(Long horCon, Locale locale);

	AgendaDto getAgenda(Long ageCon, Locale locale);

	CitaDto saveCita(CitaDto cita, Locale locale);

	CitaDto existeixCitaAgenda(Long ageCon, LocalDateTime inici, LocalDateTime fi, Long tipcitCon, Locale locale);

	CitaDto getCita(Long citCon, Locale locale);

	ErrorDto deleteCitaPersona(Long citCon, String numdoc, Locale locale);

	SubaplicacioDto getSubaplicacio(String subaplCoa, Locale locale);
	

}
