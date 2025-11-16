package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.HorariDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;

public interface HorariService {

	List<HorariDto> getHorarisBySubaplicacio(String subaplCoa, Locale locale);

	HorariDto saveHorari(HorariDto horari, Locale locale);

	HorariDto updateHorari(HorariDto horari, Locale locale);

	ErrorDto deleteHorari(Long con, Locale locale);

	HorariDto getHorari(Long con, Locale locale);

}
