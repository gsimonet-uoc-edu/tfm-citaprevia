package uoc.edu.citaprevia.front.service;

import java.util.Locale;

import uoc.edu.citaprevia.dto.SeleccioTipusCitaDto;

public interface CitaPreviaPublicClient {


	SeleccioTipusCitaDto getAllTipusCites(String subaplCoa, Locale locale);

}
