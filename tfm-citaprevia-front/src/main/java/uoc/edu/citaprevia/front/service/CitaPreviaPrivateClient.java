package uoc.edu.citaprevia.front.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.dto.HorariDto;
import uoc.edu.citaprevia.dto.SetmanaTipusDto;
import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.dto.TipusCitaDto;
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

	List<TipusCitaDto> getTipusCitesBySubaplicacio(String subaplCoa, Locale locale);

	HorariDto saveHorari(HorariDto horari, Locale locale);

	HorariDto updateHorari(Long horCon, HorariDto dto, Locale locale);

	ErrorDto deleteHorari(Long horCon, Locale locale);

	HorariDto getHorari(Long horCon, Locale locale);

	SetmanaTipusDto addSetmanaTipusToHorari(SetmanaTipusDto setmanaTipus, Locale locale);

	ErrorDto deleteSetmanaTipusOfHorari(Long horCon, SetmanaTipusDto settip, Locale locale);

	List<TecnicDto> getTecnicsBySubaplicacio(String subaplCoa, Locale locale);

	TecnicDto saveTecnic(TecnicDto tecnic, Locale locale);

	TecnicDto updateTecnic(String tecCoa, TecnicDto dto, Locale locale);

	ErrorDto deleteTecnic(String tecCoa, Locale locale);


}
