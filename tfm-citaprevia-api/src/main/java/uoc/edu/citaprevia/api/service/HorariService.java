package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.HorariDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;

public interface HorariService {

	/**
	 * Llistat d'horaris d'una subaplicació
	 * @param subaplCoa codi alfanumèric de la subaplicació
	 * @param locale idioma
	 * @return llista d'horaris
	 */
	List<HorariDto> getHorarisBySubaplicacio(String subaplCoa, Locale locale);

	/**
	 * Alta d'un horari
	 * @param horari dades de l'horari
	 * @param locale idioma
	 * @return dades de l'horari desat o error en cas contrari
	 */
	HorariDto saveHorari(HorariDto horari, Locale locale);
	
	/**
	 * Actualitza les dades d'un horari
	 * @param horari horari amb dades actualitzades
	 * @param locale idioma
	 * @return horari actualitzat o error en cas contrari
	 */
	HorariDto updateHorari(HorariDto horari, Locale locale);
	
	/**
	 * Elimina un horari de la BBDD
	 * @param con codi numèric de l'horari
	 * @param locale idioma
	 * @return null si esborrat, error en cas contrari
	 */
	ErrorDto deleteHorari(Long con, Locale locale);

	/**
	 * Consulta les dades d'un horari
	 * @param con codi numèric de l'horari
	 * @param locale idioma
	 * @return dades de l'horari
	 */
	HorariDto getHorari(Long con, Locale locale);

}
