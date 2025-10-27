package uoc.edu.citaprevia.front.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import uoc.edu.citaprevia.dto.SubaplicacioDto;


public class SubaplicacioUtil {
	
	private SubaplicacioUtil() { /* Constructor buit */ }
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SubaplicacioUtil.class.getCanonicalName());
	
	private static final String SESSION_SUBAPLICACIO = "ssa";
	
	/**
	 * Métode que torna la Subaplicacio, ja sigui de sessió o consultant l'API amb BBDD. 
	 * <br/> ¡IMPORTANT!: Mètode que només s'ha de fer servir en la part PÚBLICA
	 * @param request
	 * @param imicorpClient
	 * @param application
	 * @param subaplCoa
	 * @return GenSubaplicacioDto
	 * @throws ImiException 
	 */
	public static SubaplicacioDto getSubaplicacioAndSetSessionPublic(HttpServletRequest request,
	ImicorpClient imicorpClient, MessageSource bundle, String application,  String subaplCoa, Locale locale)
	throws ImiException {
		SubaplicacioDto subAplicacio = null;
		try {
			subAplicacio = getSessionSubaplicacio(request);
		} catch(FaltaSubaplSessioException e) {
			// No fer res.
		}
		// Si la subAplicacio es null o no coincide con la subaplCoa que viene por @PathVariable, se consultan los datos
		// en BBDD y se setean en sesión
		if(subAplicacio == null || (subAplicacio.getCoa() != null && !subAplicacio.getCoa().equalsIgnoreCase(subaplCoa))) {
			//De BBDD
			removeSubaplicacio(request);
			subAplicacio = imicorpClient.getSubaplicacio(application, subaplCoa, locale);
			//Usuari Public
			UsuariDto usuari = new UsuariDto();
			usuari.setCoa("Public");
			List<GenSubaplicacioDto> subaplicacions = new ArrayList<>();
			subaplicacions.add(subAplicacio);	//Accés a subaplicació actual al usuari PÚBLIC
			usuari.setSubaplicacions(subaplicacions);
			setSessionSubaplicacio(request, bundle, subAplicacio, usuari);
		}
		//Si la subAplicacio sigue siendo null no existe y se retorna error 404 Recurs no disponible
		if (subAplicacio == null || Util.isStrEmpty(subAplicacio.getCoa())) {
			throw new ImiException((long) HttpStatus.NOT_FOUND.value(), null, bundle.getMessage("dades_no_trobades",
				null, locale));
		}
		return subAplicacio;
	}
	
	public static GenSubaplicacioDto getSessionSubaplicacio(HttpServletRequest request) {
		GenSubaplicacioDto subAplicacio = null;
		final Object attSubApl = request.getSession().getAttribute(SESSION_SUBAPLICACIO);
		if(attSubApl instanceof GenSubaplicacioDto subApl
				&& (subApl.getCoa() != null && subApl.getAplicacio() != null // De sessió
				&& subApl.getAplicacio().getCoa() != null)) {
			subAplicacio = subApl;
		}
		if(subAplicacio == null) { throw new FaltaSubaplSessioException("Sense subaplicacio"); }
		return subAplicacio;
	}
	
	/**
	 * Setea la Subaplicació a Sesió (Part PUBLICA/PRIVADA). A part PRIVADA: Es valida si té permis l'usuari a la Subaplicació
	 * @param request
	 * @param imicorpClient ImicorpClient
	 * @param application String
	 * @param subaplCoa String
	 * @param usuari UsuariDto
	 * @param locale Locale
	 */
	public static void setSessionSubaplicacio(HttpServletRequest request, ImicorpClient imicorpClient,
	MessageSource bundle, String application, String subaplCoa, UsuariDto usuari, Locale locale) {
		final GenSubaplicacioDto subAplicacio = imicorpClient.getSubaplicacio(application, subaplCoa, locale);
		setSessionSubaplicacio(request, bundle, subAplicacio, usuari);
	}
	
	/**
	 * Setea la Subaplicació a Sesió (Part PUBLICA/PRIVADA). A part PRIVADA: Es valida si té permis l'usuari a la Subaplicació
	 * @param request
	 * @param subAplicacio GenSubaplicacioDto
	 * @param usuari UsuariDto
	 */
	public static void setSessionSubaplicacio(HttpServletRequest request, MessageSource bundle, GenSubaplicacioDto subAplicacio, UsuariDto usuari) {
		ImiErrorDto error = null;
		HttpSession session = request.getSession(true);
		//Si s'accedeix desde la part Pública, no hi ha usuari a sessió per tant es setea la subaplicació a sessió
		//PUBLIC
		usuari = usuari != null ? usuari : new UsuariDto();
		if(usuari.getCoa().equalsIgnoreCase("Public") && usuari.getName() == null) {
			session.setAttribute(SESSION_SUBAPLICACIO, subAplicacio);
		} else {
			//PRIVATE: Validació subaplicacions de l'usuari
			List<GenSubaplicacioDto> subaplicacionsUsuari = usuari.getSubaplicacions();
			for (GenSubaplicacioDto subaplicacioUsuari : subaplicacionsUsuari) {
				//Si la subaplicació seleccionada coincideix amb una de les subaplicacions asociades al Usuari, té permís i es grava a sessió
				if(subaplicacioUsuari.getCoa().equals(subAplicacio.getCoa())) {
					session.setAttribute(SESSION_SUBAPLICACIO, subAplicacio);
					break;
				}
			}
		}
		if(session.getAttribute(SESSION_SUBAPLICACIO) == null) {
			error = new ImiErrorDto(0L, "E",
				bundle.getMessage("L'usuari " + usuari.getCoa() + " no té la Subaplicació '" + subAplicacio.getCoa()
					+ "' asignada al seu Usuari/Perfil", null, new Locale("ca")));
			LOGGER.error(error.getDem());
			throw new FaltaSubaplAsignadaUsuariException(
				bundle.getMessage("imiloginerror.notesubaplasignada", null, new Locale("ca"))
				+ " Usuari:" + usuari.getCoa());
		}
	}
	
	private static void removeSubaplicacio(HttpServletRequest request) {
		request.getSession().removeAttribute(SESSION_SUBAPLICACIO);
	}

	public static GenSubaplicacioDto subApp(String app, String subapp) {
		GenAplicacioDto genApp = new GenAplicacioDto();
		genApp.setCoa(app);
		return new GenSubaplicacioDto(genApp, subapp, "", "");
	}
}
