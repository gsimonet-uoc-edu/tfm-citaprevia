package uoc.edu.citaprevia.api.service;

import java.util.Locale;

import uoc.edu.citaprevia.dto.CalendariDto;

public interface CalendariService {

	CalendariDto getCalendariCites(String subaplCoa, Long tipcitCon, Locale locale);

}
