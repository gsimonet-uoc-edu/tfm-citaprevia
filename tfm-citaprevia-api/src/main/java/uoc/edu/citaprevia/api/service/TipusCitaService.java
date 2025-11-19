package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.TipusCitaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;

public interface TipusCitaService {

	List<TipusCitaDto> getAllTipusCitaBySubaplCoa(String subaplCoa, Locale locale);

	TipusCitaDto getTipusCitaByCon(Long con, Locale locale);

	TipusCitaDto saveTipusCita(TipusCitaDto tipusCita, Locale locale);

	TipusCitaDto updateTipusCita(TipusCitaDto tipusCita, Locale locale);

	ErrorDto deleteTipusCita(Long con, Locale locale);

}
