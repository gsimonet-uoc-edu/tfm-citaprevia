package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.TecnicDto;

public interface TecnicService {

	TecnicDto getTecnicByCoa(String coa);

	List<TecnicDto> getTecnicsSubaplicacio(String subaplCoa, Locale locale);

}
