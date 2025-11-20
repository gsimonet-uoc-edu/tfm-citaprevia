package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.UbicacioDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;

public interface UbicacioService {

	List<UbicacioDto> getAllUbicacions(Locale locale);

	List<UbicacioDto> getUbicacionsByUbicacio(String subaplCoa, Locale locale);

	UbicacioDto saveUbicacio(UbicacioDto ubicacio, Locale locale);

	UbicacioDto updateUbicacio(UbicacioDto ubicacio, Locale locale);

	ErrorDto deleteUbicacio(Long con, Locale locale);

}
