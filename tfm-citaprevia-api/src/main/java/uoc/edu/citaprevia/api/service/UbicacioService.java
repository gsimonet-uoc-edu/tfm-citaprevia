package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.UbicacioDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;

public interface UbicacioService {

	/**
	 * Consulta totes les ubicacions de la BBDD
	 * @param locale idioma
	 * @return llistat
	 */
	List<UbicacioDto> getAllUbicacions(Locale locale);

	/**
	 * Consulta totes les ubicacions d'una subaplicació
	 * @param subaplCoa codi alfanumèric
	 * @param locale idioma
	 * @return llistat
	 */
	List<UbicacioDto> getUbicacionsBySubaplicacio(String subaplCoa, Locale locale);

	/**
	 * Desa les dades d'una ubicació a la BBDD
	 * @param ubicacio dades de la ubicació
	 * @param locale idioma
	 * @return dades de la ubicació, error en cas contrari
	 */
	UbicacioDto saveUbicacio(UbicacioDto ubicacio, Locale locale);
	
	/**
	 * Actualitza les dades d'una ubicació a la BBDD
	 * @param ubicacio dades de la ubicació
	 * @param locale idioma
	 * @return dades e la ubicació actualitzada, error en cas contrari
	 */
	UbicacioDto updateUbicacio(UbicacioDto ubicacio, Locale locale);

	/**
	 * Elimina una subaplicació de la BBDD
	 * @param con codi numèric
	 * @param locale idioma
	 * @return null si esborrada, error en cas contrari
	 */
	ErrorDto deleteUbicacio(Long con, Locale locale);

}
