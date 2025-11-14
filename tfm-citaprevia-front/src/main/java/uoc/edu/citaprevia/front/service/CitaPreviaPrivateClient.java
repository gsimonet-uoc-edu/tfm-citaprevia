package uoc.edu.citaprevia.front.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.dto.HorariDto;
import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.dto.UbicacioDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;

public interface CitaPreviaPrivateClient {

	TecnicDto getTecnic(String tecCoa, Locale locale);

	List<AgendaDto> getAgendasBySubaplicacio(String subaplCoa, Locale locale);

	List<AgendaDto> getAgendasByTecnic(String tecCoa, Locale locale);

	ErrorDto deleteCita(Long citCon, Locale locale);

	CitaDto updateCita(Long con, CitaDto dto, Locale locale);

	List<UbicacioDto> getUbicacions(Locale locale);

	List<HorariDto> getHorarisBySubaplicacio(String subaplCoa, Locale locale);

	AgendaDto updateAgenda(Long ageCon, AgendaDto dto, Locale locale);

	ErrorDto deleteAgenda(Long ageCon, Locale locale);

	AgendaDto saveAgenda(AgendaDto cita, Locale locale);

}
