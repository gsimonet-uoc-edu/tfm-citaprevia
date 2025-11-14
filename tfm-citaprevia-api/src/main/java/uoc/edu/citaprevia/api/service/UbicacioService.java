package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.UbicacioDto;

public interface UbicacioService {

	List<UbicacioDto> getAllUbicacions(Locale locale);

}
