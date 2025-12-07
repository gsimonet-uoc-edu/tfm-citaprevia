package uoc.edu.citaprevia.api.service;

import java.time.LocalDateTime;
import java.util.Locale;

import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;

public interface CitaService {

	/**
	 * Desa una cita a la BBD
	 * @param cita dades de la cita
	 * @param locale
	 * @return cita desada o error en cas contrari
	 */
	CitaDto saveCita(CitaDto cita, Locale locale);

	/**
	 * Cormprova que existeix una cita
	 * @param ageCon codi numèric de l'agendra
	 * @param inici hora inici de la cita
	 * @param fi hora final de la cita
	 * @param tipcitCon codi numèric del tipus de cita
	 * @param locale idioma
	 * @return dades de la cita si existeix
	 */
	CitaDto existeixCitaAgenda(Long ageCon, LocalDateTime inici, LocalDateTime fi, Long tipcitCon, Locale locale);

	/**
	 * Esborra una cita donat un codi de cita i el nombre de document de la persona associada a la cita (part pública)
	 * @param con codi numèric de la cita
	 * @param numdoc DNI/NIE/NIF/Passaport/Altres
	 * @param locale idioma
	 * @return elimina la cita associada al nombre de document de la persona
	 */
	ErrorDto deleteCitaPersona(Long con, String numdoc, Locale locale);

	/**
	 * Elimina una cita donat el seu codi (part privada)
	 * @param con codi de la cita
	 * @param locale idioma
	 * @return null si cita esborrada, error en cas contrari
	 */
	ErrorDto deleteCita(Long con, Locale locale);

	/**
	 * Actualitza les dades d'una cita
	 * @param cita dades actualitzades
	 * @param locale idioma
	 * @return cita actualitzada o error en cas contrari
	 */
	CitaDto updateCita(CitaDto cita, Locale locale);

	/**
	 * Consulta les dades d'una cita dona el seu codi
	 * @param con codi numèric de la cita
	 * @param locale idioma
	 * @return dades de la cita
	 */
	CitaDto getCita(Long con, Locale locale);

}
