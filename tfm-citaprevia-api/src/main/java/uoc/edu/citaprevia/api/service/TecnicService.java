package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;

public interface TecnicService {

	List<TecnicDto> getTecnicsSubaplicacio(String subaplCoa, Locale locale);

	TecnicDto saveTecnic(TecnicDto tecnic, Locale locale);

	TecnicDto updateTecnic(TecnicDto tecnic, Locale locale);

	ErrorDto deleteTecnic(String coa, Locale locale);

	TecnicDto getTecnicByCoa(String coa, Locale locale);

}
