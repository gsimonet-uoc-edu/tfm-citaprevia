package uoc.edu.citaprevia.front.service;

import java.util.Locale;

import uoc.edu.citaprevia.dto.SubaplicacioDto;

public interface SubaplicacioClient {

	SubaplicacioDto getSubaplicacio(String subaplCoa, Locale locale);

}
