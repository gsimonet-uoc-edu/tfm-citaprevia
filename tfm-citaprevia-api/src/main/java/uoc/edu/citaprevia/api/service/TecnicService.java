package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;

public interface TecnicService {

	/**
	 * Consulla llistat de tècnics que pertanyen a una subaplicació
	 * @param subaplCoa codi alfanumèric
	 * @param locale idioma
	 * @return llistat
	 */
	List<TecnicDto> getTecnicsSubaplicacio(String subaplCoa, Locale locale);

	/**
	 * Desa les dades d'un tècnic a la BBDD
	 * @param tecnic dades del tècnic
	 * @param locale idioma
	 * @return dades les tècnic, error en cas contrari
	 */
	TecnicDto saveTecnic(TecnicDto tecnic, Locale locale);

	/**
	 * Actualitza les dades d'un tècnic a la BBDD
	 * @param tecnic dades del tècnic
	 * @param locale idioma
	 * @return dades les tècnic actualitzat, error en cas contrari
	 */
	TecnicDto updateTecnic(TecnicDto tecnic, Locale locale);

	/**
	 * Elimina un tècnic de la BBDD
	 * @param coa codi alfanumèric
	 * @param locale idioma
	 * @return null si esborrat, error en cas contrari
	 */
	ErrorDto deleteTecnic(String coa, Locale locale);

	/**
	 * Consulta les dades d'un tècnic
	 * @param coa codi alfanumèric
	 * @param locale idioma
	 * @return dades del tècnic
	 */
	TecnicDto getTecnicByCoa(String coa, Locale locale);

}
