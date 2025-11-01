package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.SetmanaTipusDto;

public interface SetmanaTipusService {

	List<SetmanaTipusDto> getSetmanaTipusByHorari(Long horCon, Locale locale);

}
