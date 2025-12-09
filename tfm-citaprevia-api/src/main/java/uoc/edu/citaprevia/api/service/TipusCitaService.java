package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.TipusCitaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;

public interface TipusCitaService {

	/**
	 * Consulta tots el tipus de cita d'una subaplicació
	 * @param subaplCoa codi alfanumèric
	 * @param locale idioma
	 * @return llistat
	 */
	List<TipusCitaDto> getAllTipusCitaBySubaplCoa(String subaplCoa, Locale locale);

	/**
	 * Consulta les dades d'un tipus de cita
	 * @param con codi numèric
	 * @param locale idioma
	 * @return dades del tipus de cita
	 */
	TipusCitaDto getTipusCitaByCon(Long con, Locale locale);

	/**
	 * Desa un tipus de cita a la BBDD
	 * @param tipusCita dades del tipus de cita
	 * @param locale idioma
	 * @return dades del tipus de cita, error en cas contrari
	 */
	TipusCitaDto saveTipusCita(TipusCitaDto tipusCita, Locale locale);

	/**
	 * Actualitza un tipus de cita a la BBDD
	 * @param tipusCita dades del tipus de cita
	 * @param locale idioma
	 * @return dades del tipus de cita actualitzada, error en cas contrari
	 */
	TipusCitaDto updateTipusCita(TipusCitaDto tipusCita, Locale locale);

	/**
	 * Elimina un tipus de cita de la BBDD
	 * @param con codi numèric
	 * @param locale idioma
	 * @return null si esborrat, error en cas contrari
	 */
	ErrorDto deleteTipusCita(Long con, Locale locale);

}
