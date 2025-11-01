package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.TipusCitaDto;

public interface TipusCitaService {

	List<TipusCitaDto> getAllTipusCitaBySubaplCoa(String subaplCoa, Locale locale);

	TipusCitaDto getTipusCitaByCon(Long con, Locale locale);

}
