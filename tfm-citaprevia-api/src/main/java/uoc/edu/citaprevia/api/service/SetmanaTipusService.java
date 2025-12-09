package uoc.edu.citaprevia.api.service;

import java.util.List;
import java.util.Locale;

import uoc.edu.citaprevia.dto.SetmanaTipusDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;

public interface SetmanaTipusService {

	/**
	 * Consultar franges horàries d'un horari
	 * @param horCon codi numèrica horari
	 * @param locale idioma
	 * @return llistat
	 */
	List<SetmanaTipusDto> getSetmanaTipusByHorari(Long horCon, Locale locale);

	/**
	 * Desa una franja horària a la BBDD
	 * @param settip dades de la franja horària
	 * @param locale idioma
	 * @return dades franja horària, error en cas contrari
	 */
	SetmanaTipusDto saveSetmanaTipus(SetmanaTipusDto settip, Locale locale);

	/**
	 * Actualitza una franja horària de la BBDD
	 * @param settip dades de la franja horària
	 * @param locale idioma
	 * @return dades franja horària actualitzada, error en cas contrari
	 */
	SetmanaTipusDto updateSetmanaTipus(SetmanaTipusDto settip, Locale locale);

	/**
	 * Elimina una franja horària d'un horari de la BBDD
	 * @param settip dades de la franja horària
	 * @param locale idioma
	 * @return null si operació correcta, error en cas contrari
	 */
	ErrorDto deleteSetmanaTipus(SetmanaTipusDto settip, Locale locale);

}
