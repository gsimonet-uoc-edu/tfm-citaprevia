package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;

public interface AgendaService {

	/**
	 * Obté llistat agendes segons paràmetres
	 * @param tipCitCon codi numèric tipus de cita
	 * @param subaplCoa codi alfanumèrica de subaplicacio
	 * @param locale idimoa
	 * @return llistat agendes
	 */
	List<AgendaDto> getAgendesByTipusCitaAndSubaplicacio(Long tipCitCon, String subaplCoa, Locale locale);
	
	/**
	 * Consulta les dades d'una agenda donat el codi
	 * @param con codi numèric d'agenda
	 * @param locale idioma
	 * @return agenda
	 */
	AgendaDto getAgenda(Long con, Locale locale);

	/**
	 * Obté llistat d'agendes d'una subaplicació
	 * @param subaplCoa codi alfanumèrica de la subaplicació
	 * @param locale idioma
	 * @return llista agendes
	 */
	List<AgendaDto> getAgendesBySubaplicacio(String subaplCoa, Locale locale);

	/**
	 * Llista d'agendes d'un tècnic
	 * @param tecCoa codi alfanumèrica del tècnic
	 * @param locale idimoa
	 * @return agendes
	 */
	List<AgendaDto> getAgendesByTecnic(String tecCoa, Locale locale);

	/**
	 * Desa les dades d'una agenda a la BBDD
	 * @param agenda entitat
	 * @param locale idioma
	 * @return agenda desada, o error en cas contrari
	 */
	AgendaDto saveAgenda(AgendaDto agenda, Locale locale);

	/**
	 * Actualitza les dades d'una agenda a la BBDD
	 * @param agenda entitat
	 * @param locale idioma
	 * @return agenda actualitzada, error en cas contrari
	 */
	AgendaDto updateAgenda(AgendaDto agenda, Locale locale);

	/**
	 * Elimina una agenda de la BBDD
	 * @param con codi numèrica de l'agendra
	 * @param locale idioma
	 * @return null si s'ha esborrat, error en cas contrari
	 */
	ErrorDto deleteAgenda(Long con, Locale locale);

	/**
	 * Llistat d'agende obertes (datfin menor o igual a sysdate)
	 * @param tipCitCon codi numèric de tipus de cita
	 * @param subaplCoa codi alfanumèrica de subaplicació
	 * @param locale idioma
	 * @return agendes
	 */
	List<AgendaDto> getAgendesObertesByTipusCitaAndSubaplicacio(Long tipCitCon, String subaplCoa, Locale locale);

}
