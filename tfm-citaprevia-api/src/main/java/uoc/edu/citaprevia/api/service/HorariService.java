package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.HorariDto;

public interface HorariService {

	List<HorariDto> getHorarisBySubaplicacio(String subaplCoa, Locale locale);

}
