package uoc.edu.citaprevia.api.service;

import java.util.Locale;

import uoc.edu.citaprevia.dto.SubaplicacioDto;

public interface SubaplicacioService {

	SubaplicacioDto getSubaplicacioByCoa(String coa, Locale locale);

}
