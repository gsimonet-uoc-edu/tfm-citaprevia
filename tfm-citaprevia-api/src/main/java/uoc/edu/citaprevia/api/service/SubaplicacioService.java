package uoc.edu.citaprevia.api.service;

import java.util.Locale;

import uoc.edu.citaprevia.dto.SubaplicacioDto;

public interface SubaplicacioService {

	/**
	 * Consultar dades d'una subaplicació
	 * @param coa codi alfanumèric de la subaplicació
	 * @param locale idioma
	 * @return dades de la subaplicació
	 */
	SubaplicacioDto getSubaplicacioByCoa(String coa, Locale locale);

}
