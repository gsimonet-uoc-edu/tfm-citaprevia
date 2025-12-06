package uoc.edu.citaprevia.front.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.dto.HorariDto;
import uoc.edu.citaprevia.dto.SetmanaTipusDto;
import uoc.edu.citaprevia.dto.SubaplicacioDto;
import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.dto.TipusCitaDto;
import uoc.edu.citaprevia.dto.UbicacioDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.front.dto.CampConfigDto;
import uoc.edu.citaprevia.front.dto.CitaFormDto;
import uoc.edu.citaprevia.front.privat.dto.AgendaFormDto;
import uoc.edu.citaprevia.front.privat.dto.HorariFormDto;
import uoc.edu.citaprevia.front.privat.dto.SetmanaTipusDeleteFormDto;
import uoc.edu.citaprevia.front.privat.dto.SetmanaTipusFormDto;
import uoc.edu.citaprevia.front.privat.dto.TecnicFormDto;
import uoc.edu.citaprevia.front.privat.dto.TipusCitaFormDto;
import uoc.edu.citaprevia.front.privat.dto.UbicacioFormDto;
import uoc.edu.citaprevia.front.service.CitaPreviaPrivateClient;
import uoc.edu.citaprevia.front.service.CitaPreviaPublicClient;
import uoc.edu.citaprevia.front.service.MetacampService;
import uoc.edu.citaprevia.model.ModalitatTipusCita;
import uoc.edu.citaprevia.model.Perfil;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@Controller
@RequestMapping("/private")
/**
 * Controlador que processa les operacions de la part privada de la cita prèvia
 */
public class PrivateController {
	
	private static final Logger LOG = LoggerFactory.getLogger(PrivateController.class);
	
	@Value("${citaprevia.service.api.host}")
	private String urlCitapreviaApi;

	@Autowired
	private CitaPreviaPublicClient citaPreviaPublicClient;
	
    
    @Autowired
    private CitaPreviaPrivateClient citaPreviaPrivateClient;
    
    @Autowired
    private MetacampService metacampService;
    
	@Autowired
	private MessageSource bundle;

	@GetMapping("/login")
	public String login() {
		return "private/login";
	}
	
	@GetMapping("/calendari") 
	/**
	 * Mostra la vista del calendari privat per a la gestió de cites.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
	 * @param model Model de Spring utilitzat per afegir atributs que es passaran a la vista ThymeLeaf.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
	 * @param locale La configuració regional (idioma) de l'usuari
	 * @return El nom de la vista o una redirecció en cas d'error.
	 * @throws Exception Qualsevol altre error excepcional.
	 */
	public String calendari(Authentication authentication,
							Model model,
							RedirectAttributes redirectAttributes,
							Locale locale) throws Exception {
		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.calendari startTime={}", startTime);
    	try {
			String subaplCoa = this.getSubaplCoa(authentication);

	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	            return "redirect:/private/login";
	        }
	        
	        // Obtenir subaplicació per mostrar al header
	        if (!Utils.isEmpty(subaplCoa)) {
	        	SubaplicacioDto subAplicacio = citaPreviaPublicClient.getSubaplicacio(subaplCoa, locale);
	        	model.addAttribute("subAplicacio", subAplicacio); 
	        }
	        
			String coa = authentication.getName();
			TecnicDto tecnic = citaPreviaPrivateClient.getTecnic(coa, locale);
	
			if (tecnic == null || Utils.isEmpty(tecnic.getCoa())) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TECNICS, null, locale));
	            return "redirect:/private/login";
			}
	
	        boolean isAdministrador = this.isAdministrador(authentication);
	        
	        List<AgendaDto> agendes;
	        if (isAdministrador) {
	            agendes = citaPreviaPrivateClient.getAgendasBySubaplicacio(subaplCoa, locale);
	        } else {
	            agendes = citaPreviaPrivateClient.getAgendasByTecnic(tecnic.getCoa(), locale);    
	        }
	        

	        
	        model.addAttribute("isAdministrador", isAdministrador);
			model.addAttribute("subaplCoa", subaplCoa); 
			model.addAttribute("tipusCita", new TipusCitaDto());
			model.addAttribute("frangesHorariesGrouped", generarEvents(agendes, locale));
	
			model.addAttribute("dataInici", LocalDate.now());
			model.addAttribute("dataFi", LocalDate.now().plusDays(30));
			model.addAttribute("tecnic", tecnic);
	
			// Camps dinàmics
		    List<CampConfigDto> campos = metacampService.getCamps(subaplCoa, locale);
		    model.addAttribute("camposCita", campos);
	    }  catch (Exception e) {
	        LOG.error("### Error PrivateController.calendari {}", e);
        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.MSG_ERR_GET_CALENDARI, null, locale));
	        return "redirect:/private/login";
	    } finally {
	    	long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.calendari totalTime={}", totalTime);
		}
		return "private/calendari-private";
	}
	
	@PostMapping("/cita/reserva")
	/**
	 * Processa la petició de reserva d'una nova cita en l'àrea privada i la desa a la BBDD
	 * @param form Objecte de formulari que conté les dades de la cita introduïdes per l'usuari.
	 * @param result Objecte BindingResult de Spring que comprova i gestiona errors de validació del formulari.
	 * @param model Model de Spring utilitzat per afegir atributs que es passaran a la vista ThymeLeaf.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
	 * @param locale La configuració regional (idioma) de l'usuari
	 * @return El nom de la vista ("private/calendari-private") o una redirecció en cas d'error.
	 * @throws Exception Qualsevol altre error excepcional.
	 */
	public String reservaCitaPrivada(
	        @ModelAttribute CitaFormDto form,
	        BindingResult result,
	        Model model,
	        RedirectAttributes redirectAttributes,
	        Authentication authentication,
	        Locale locale) throws Exception {
		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.reservaCitaPrivada startTime={}, citaForm={}", startTime, form.toString());
		
		CitaDto citaSaved = new CitaDto();
		
    	try {

		    if (result.hasErrors()) {
		    	redirectAttributes.addFlashAttribute("error", Constants.ERROR_BINDINGS_FORM);
		        return "redirect:/private/calendari";
		    }
	
			String subaplCoa = this.getSubaplCoa(authentication);
	
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }
	        
		    String coa = authentication.getName();
		    TecnicDto tecnic = citaPreviaPrivateClient.getTecnic(coa, locale);
		    if (tecnic == null || Utils.isEmpty(tecnic.getCoa())) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TECNICS, null, locale));
	        	 return "redirect:/private/calendari";
		    }
	
		    // Obtener agenda y tipus de cita
		    AgendaDto agenda = citaPreviaPublicClient.getAgenda(form.getAgendaCon(), locale);
		    if (agenda == null || !subaplCoa.equals(agenda.getHorari().getSubapl().getCoa())) {
		    	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_AGENDES, null, locale));
		        return "redirect:/private/calendari";
		    }
	
		    TipusCitaDto tipusCita = agenda.getHorari().getTipusCita();
	
		    // Crear cita
		    CitaDto citaToSave = new CitaDto();
		    // Camps estàtics
		    citaToSave.setDathorini(form.getDataHoraInici().minusSeconds(1));
		    citaToSave.setDathorfin(form.getDataHoraFin().minusSeconds(1));
		    citaToSave.setNom(form.getNom());
		    citaToSave.setLlis(form.getLlis());
		    citaToSave.setNumdoc(form.getNumdoc());
		    citaToSave.setNomcar(form.getNomcar());
		    citaToSave.setTel(Long.valueOf(form.getTel()));
		    citaToSave.setEma(form.getEma());
		    citaToSave.setObs(form.getObs());
		    // Camps dinàmics
		    citaToSave.setLit1(form.getLit1());
		    citaToSave.setLit2(form.getLit2());
		    citaToSave.setLit3(form.getLit3());
		    citaToSave.setLit4(form.getLit4());
		    citaToSave.setLit5(form.getLit5());
		    citaToSave.setLit6(form.getLit6());
		    citaToSave.setLit7(form.getLit7());
		    citaToSave.setLit8(form.getLit8());
		    citaToSave.setLit9(form.getLit9());
		    citaToSave.setLit10(form.getLit10());
		    citaToSave.setAgenda(agenda);
		    citaToSave.setTipusCita(tipusCita);
	
		    citaSaved = citaPreviaPublicClient.saveCita(citaToSave, locale);
		    if (citaSaved.hasErrors()) {
		    	redirectAttributes.addFlashAttribute("error", citaSaved.getErrors().get(0).getDem());
		        return "redirect:/private/calendari";
		    }
	    }  catch (Exception e) {
	        LOG.error("### Error PrivateController.reservaCitaPrivada: ", e);
        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.MSG_ERR_GET_CALENDARI, null, locale));
        	return "redirect:/private/calendari";
	    } finally {
	    	long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.reservaCitaPrivada totalTime={}, citaSaved={}", totalTime, citaSaved.toString());
		}
	    return "redirect:/private/cita/confirmacio?con=" + citaSaved.getCon() + "&accion=creada";
	}

	@GetMapping("/cita/confirmacio")
	/**
	 * Mostra la pàgina de confirmació després de la creació o cancel·lació d'una cita.
	 * @param con El codi de la cita sobre la qual s'ha realitzat l'acció.
	 * @param accion Acció realitzada, actualitzada o cancelada
	 * @param model Model de Spring utilitzat per afegir atributs que es passaran a la vista ThymeLeaf.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
	 * @param locale La configuració regional (idioma) de l'usuari
	 * @return El nom de la vista  o una redirecció en cas d'error.
	 * @throws Exception Qualsevol altre error excepcional.
	 */
	public String confirmacioCita(
	        @RequestParam Long con,
	        @RequestParam String accion,
	        Model model,
	        RedirectAttributes redirectAttributes,
	        Authentication authentication,
	        Locale locale) throws Exception {
		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.confirmacioCita startTime={}, citCon={}, accion={}", startTime, con, accion);
				
    	try {
    		  		
			String subaplCoa = this.getSubaplCoa(authentication);
			
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }
	        
	        String coa = authentication.getName();
		    TecnicDto tecnic = citaPreviaPrivateClient.getTecnic(coa, locale);
		    if (tecnic == null || Utils.isEmpty(tecnic.getCoa())) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TECNICS, null, locale));
	        	 return "redirect:/private/calendari";
		    }

		    CitaDto cita = new CitaDto();
		    if ( !"cancelada".equals(accion)) {
			    cita = citaPreviaPublicClient.getCita(con, locale);
			    if (cita == null || Utils.isEmpty(cita.getCon()) || !subaplCoa.equals(cita.getAgenda().getHorari().getSubapl().getCoa())) {
			        return "redirect:/private/calendari?error";
			    }
		    } else {
		    	cita.setCon(con);
		    }
		    model.addAttribute("cita", cita);
		    model.addAttribute("subaplCoa", subaplCoa);
		    model.addAttribute("accion", accion);
	    }  catch (Exception e) {
	        LOG.error("### Error PrivateController.confirmacioCita {}", e);
        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale));
        	return "redirect:/private/calendari";
	    } finally {
	    	long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.confirmacioCita totalTime={}, citCon={}, accion={}", totalTime, con, accion);
		}
	    return "private/confirmacio-private";
	}
	
	@PostMapping("/cita/actualitzar")
	/**
	 * Processa la petició d'actualització d'una cita existent en l'àrea privada.
	 * @param form Objecte de formulari que conté les dades de la cita introduïdes per l'usuari.
	 * @param result Objecte BindingResult de Spring que comprova i gestiona errors de validació del formulari.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
	 * @param locale La configuració regional (idioma) de l'usuari
	 * @return Redirecció a la vista de confirmació si la petició és correcta.
	 * @throws Exception Qualsevol altre error excepcional.
	 */
	public String actualitzarCita(
	        @ModelAttribute CitaFormDto form,
	        BindingResult result,
	        RedirectAttributes redirectAttributes,
	        Authentication authentication,
	        Locale locale) throws Exception {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.actualitzarCita startTime={}, citaForm={}", startTime, form.toString());
		
		CitaDto citaUpdated = new CitaDto();
		
    	try {
    		
		    if (result.hasErrors()) {
		    	redirectAttributes.addFlashAttribute("error", Constants.ERROR_BINDINGS_FORM);
		        return "redirect:/private/calendari";
		    }
	
			String subaplCoa = this.getSubaplCoa(authentication);
			
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }
	        
	        String coa = authentication.getName();
		    TecnicDto tecnic = citaPreviaPrivateClient.getTecnic(coa, locale);
		    if (tecnic == null || Utils.isEmpty(tecnic.getCoa())) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TECNICS, null, locale));
	        	 return "redirect:/private/calendari";
		    }
		    
	
		    CitaDto cita = citaPreviaPublicClient.getCita(form.getCitaCon(), locale);
		    if (cita == null || !subaplCoa.equals(cita.getAgenda().getHorari().getSubapl().getCoa())) {
		        redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale));
		        return "redirect:/private/calendari";
		    }
	
		    // Actualitzar camps
		    cita.setDathorini(form.getDataHoraInici().minusSeconds(1));
		    cita.setDathorfin(form.getDataHoraFin().minusSeconds(1));
		    cita.setNom(form.getNom());
		    cita.setLlis(form.getLlis());
		    cita.setNumdoc(form.getNumdoc());
		    cita.setNomcar(form.getNomcar());
		    cita.setTel(Long.valueOf(form.getTel()));
		    cita.setEma(form.getEma());
		    cita.setObs(form.getObs());
		    cita.setLit1(form.getLit1()); 
		    cita.setLit2(form.getLit2());
		    cita.setLit3(form.getLit3()); 
		    cita.setLit4(form.getLit4());
		    cita.setLit5(form.getLit5()); 
		    cita.setLit6(form.getLit6());
		    cita.setLit7(form.getLit7()); 
		    cita.setLit8(form.getLit8());
		    cita.setLit9(form.getLit9()); 
		    cita.setLit10(form.getLit10());
	
		    citaUpdated = citaPreviaPrivateClient.updateCita(form.getCitaCon(), cita, locale);
		    if (citaUpdated == null || Utils.isEmpty(citaUpdated.getCon()) || citaUpdated.hasErrors()) {
		        redirectAttributes.addFlashAttribute("error", citaUpdated.hasErrors() ? citaUpdated.getErrors().get(0).getDem() :bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale));
		    } else {
		        return "redirect:/private/cita/confirmacio?con=" + citaUpdated.getCon() + "&accion=actualitzada";
		    }
	    }  catch (Exception e) {
	        LOG.error("### Error PrivateController.actualitzarCita {}", e);
        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale));
        	return "redirect:/private/calendari";
	    } finally {
	    	long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.actualitzarCita totalTime={}, citaUpdated={}", totalTime, citaUpdated.toString());
		}
	    return "redirect:/private/calendari";
	}

	@PostMapping("/cita/cancelar")
	/**
	 * Processa la petició de cancel.lació d'una cita existent en l'àrea privada.
	 * @param con El codi de la cita sobre la qual s'ha realitzat l'acció.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
	 * @param locale La configuració regional (idioma) de l'usuari
	 * @return Redirecció a la vista de confirmació si la petició és correcta.
	 * @throws Exception Qualsevol altre error excepcional.
	 */
	public String cancelarCita(
	        @RequestParam Long citaCon,
	        Authentication authentication,
	        RedirectAttributes redirectAttributes,
	        Locale locale) throws Exception {

		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.cancelarCita startTime={}, citaCon={}", startTime, citaCon);
				
    	try {
    		   	
			String subaplCoa = this.getSubaplCoa(authentication);
			
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }
	        
	        String coa = authentication.getName();
		    TecnicDto tecnic = citaPreviaPrivateClient.getTecnic(coa, locale);
		    if (tecnic == null || Utils.isEmpty(tecnic.getCoa())) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TECNICS, null, locale));
	        	 return "redirect:/private/calendari";
		    }
		    
	
		    CitaDto cita = citaPreviaPublicClient.getCita(citaCon, locale);
		    if (cita == null || Utils.isEmpty(cita.getCon()) || !subaplCoa.equals(cita.getAgenda().getHorari().getSubapl().getCoa())) {
		        redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_CITES, null, locale));
		        return "redirect:/private/calendari";
		    }
	
		    // Controlar que no es puguin cancelar cites ja passades
		    if (cita.getDathorini().isBefore(LocalDateTime.now())) {
		        redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_DELETE_CITA_PASSADA, null, locale));
		        return "redirect:/private/calendari";
		    }
		    
		    ErrorDto error = citaPreviaPrivateClient.deleteCita(citaCon, locale);
		    if (error == null) {
		        return "redirect:/private/cita/confirmacio?con=" + citaCon + "&accion=cancelada";
		    } else {
		        redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_CITES, null, locale));
		        return "redirect:/private/calendari";
		    }
	    }  catch (Exception e) {
	        LOG.error("### Error PrivateController.cancelarCitaa {}", e);
        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale));
        	return "redirect:/private/calendari";
	    } finally {
	    	long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.cancelarCita totalTime={}, citaDeleted={}", totalTime, citaCon);
		}   	
	}
	
	/**
	 * Genera un mapa d'esdeveniments de calendari
	 * @param agendes llistat d'agendea
	 * @param locale
	 * @return TreeMap, clau és la data i el valor és un llistat de mapes d'String/Object que representen els les franges horàries
	 */
	private Map<LocalDate, List<Map<String, Object>>> generarEvents(List<AgendaDto> agendes, Locale locale) {
		
	    Map<LocalDate, List<Map<String, Object>>> grouped = new TreeMap<>();
	    
	    long startTime=System.currentTimeMillis();
	    LOG.info("### Inici PrivateController.generarEvents startTime={}, agendesSize={}", startTime, agendes != null ? agendes.size() : 0);
	            
	    try {
	        
	        for (AgendaDto agenda : agendes) {
	            
	            LocalDate dataInici = agenda.getDatini();
	            LocalDate dataFi = agenda.getDatfin();

	            for (LocalDate data = dataInici; !data.isAfter(dataFi); data = data.plusDays(1)) {
	                int diaSetmana = data.getDayOfWeek().getValue();

	                List<SetmanaTipusDto> franges = citaPreviaPublicClient.getSetmanesTipusByHorari(agenda.getHorari().getCon(), locale);

	                List<Map<String, Object>> aux = new ArrayList<>();

	                for (SetmanaTipusDto franja : franges) {
	                    if (franja.getDiasetCon() == diaSetmana) {
	                        LocalDateTime inici = LocalDateTime.of(data, franja.getHorini());
	                        LocalDateTime fi = LocalDateTime.of(data, franja.getHorfin());
	                        inici = inici.plusSeconds(1);
	                        fi = fi.plusSeconds(1);
	                        
	                        boolean ocupada = false;
	                                
	                        CitaDto existeixCita = citaPreviaPublicClient.existeixCitaAgenda(agenda.getCon(), inici.minusSeconds(1), fi.minusSeconds(1), agenda.getHorari().getTipusCita().getCon(), locale);

	                        if (existeixCita != null && !Utils.isEmpty(existeixCita.getCon())) {
	                            ocupada = true;
	                        }

	                        Map<String, Object> event = new HashMap<>();
	                        event.put("title", franja.getHorini() + " - " + franja.getHorfin());
	                        event.put("start", inici);
	                        event.put("end", fi);
	                        event.put("classNames", ocupada ? "hora-ocupada" : "hora-lliure");
	                        event.put("lliure", !ocupada);
	                        event.put("tecnic", agenda.getTecnic().getNom());
	                        event.put("agendaCon", agenda.getCon());

	                        if (ocupada) {
	                            event.put("citaCon", existeixCita.getCon());                        
	                            event.put("nom", existeixCita.getNom());
	                            event.put("llis", existeixCita.getLlis());
	                            event.put("numdoc", existeixCita.getNumdoc());
	                            event.put("nomcar", existeixCita.getNomcar());
	                            event.put("tel", existeixCita.getTel() != null ? String.valueOf(existeixCita.getTel()) : ""); 
	                            event.put("ema", existeixCita.getEma());
	                            event.put("obs", existeixCita.getObs());
	                            
	                            // Camps dinàmics (lit1 a lit10)
	                            event.put("lit1", existeixCita.getLit1());
	                            event.put("lit2", existeixCita.getLit2());
	                            event.put("lit3", existeixCita.getLit3());
	                            event.put("lit4", existeixCita.getLit4());
	                            event.put("lit5", existeixCita.getLit5());
	                            event.put("lit6", existeixCita.getLit6());
	                            event.put("lit7", existeixCita.getLit7());
	                            event.put("lit8", existeixCita.getLit8());
	                            event.put("lit9", existeixCita.getLit9());
	                            event.put("lit10", existeixCita.getLit10());
	                        }
	                        aux.add(event);
	                    }
	                }
	                
	                // Concatena els esdeveniments per a la mateixa data
	                if (!aux.isEmpty()) {
	                    List<Map<String, Object>> existingEvents = grouped.getOrDefault(data, new ArrayList<>());
	                    
	                    // Afegim tots els nous esdeveniments a la llista existent
	                    existingEvents.addAll(aux);
	                    
	                    grouped.put(data, existingEvents);
	                }
	            }
	        }
	    }  catch (Exception e) {
	        LOG.error("### Error PrivateController.generarEvents: ", e);
	    } finally {
	        long totalTime = (System.currentTimeMillis() - startTime);
	        LOG.info("### Final PrivateController.generarEvents totalTime={}, agendesSize={}", totalTime, agendes != null ? agendes.size() : 0);
	    }  
	    return grouped;
	}
	
	/**
	 * Funció per determinar si l'usuari autenticat té un perfil d'administrador.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
	 * @return true si es admistrador, false en cas contrari.
	 */
    private boolean isAdministrador(Authentication authentication) {
        if (authentication == null || authentication.getAuthorities() == null) {
            return false;
        }        
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String prefixePerfil = StringUtils.substringBefore(authority.getAuthority(), "_");
            if (prefixePerfil.equals(Perfil.ADMINISTRADOR.getValor())) {
                return true;
            }
        }
        return false;
    }

   /**
    * Funció per obtenir la subaplicació de l'usuari autenticat
	* @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
    * @return codi de l'aplicació
    */
   private String getSubaplCoa(Authentication authentication) {
        if (authentication == null || authentication.getAuthorities() == null) {
            return null;
        }
        for (GrantedAuthority auth : authentication.getAuthorities()) {
            String authority = auth.getAuthority();
            if (authority.contains("_")) {
                return StringUtils.substringAfter(authority, "_");
            }
        }
        return null;
    }
    
    
    @GetMapping("/gestio/agendes")
    /**
     * Recupera el llistat d'agendes del tècnic connectat
	 * @param model Model de Spring utilitzat per afegir atributs que es passaran a la vista ThymeLeaf.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
	 * @param locale La configuració regional (idioma) de l'usuari
	 * @return El nom de la vista o una redirecció en cas d'error.
     */   
    public String gestioAgendes(Model model,
    							Authentication authentication,
					    		RedirectAttributes redirectAttributes, 
					    		Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.gestioAgendes startTime={}", startTime);
    	try {
    		
			String subaplCoa = this.getSubaplCoa(authentication);
			
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }
	        
	        // Obtenir subaplicació per mostrar al header
	        	SubaplicacioDto subAplicacio = citaPreviaPublicClient.getSubaplicacio(subaplCoa, locale);
	        	model.addAttribute("subAplicacio", subAplicacio); 
	        
	        String tecCoa = authentication.getName();
		    TecnicDto tecnic = citaPreviaPrivateClient.getTecnic(tecCoa, locale);
		    if (tecnic == null || Utils.isEmpty(tecnic.getCoa())) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TECNICS, null, locale));
	        	 return "redirect:/private/calendari";
		    }
	        
	        //  Obtenir llistes de lookups
	        List<UbicacioDto> ubicacions = citaPreviaPrivateClient.getUbicacionsBySubaplicacio(subaplCoa, locale);
	        List<HorariDto> horaris = citaPreviaPrivateClient.getHorarisBySubaplicacio(subaplCoa, locale);
	
	        // Obtenir llistat d'agendes del tècnic connectat
	        List<AgendaDto> agendes = citaPreviaPrivateClient.getAgendasByTecnic(tecCoa, locale);
	        
	        // Afegir a la vista
	        model.addAttribute("agendes", agendes);
	        model.addAttribute("ubicacions", ubicacions);
	        model.addAttribute("horaris", horaris);
	        
	        // Formulari buit
	        if (!model.containsAttribute("agendaForm")) {
	        	AgendaFormDto form = new AgendaFormDto();
	            model.addAttribute("agendaForm", form);
	        }
	        
	        model.addAttribute("isAdministrador", this.isAdministrador(authentication));
    	}  catch (Exception e) {
            LOG.error("### Error PrivateController.gestioAgendes: ", e);
            redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_AGENDES, null, locale));
            return "redirect:/private/calendari";
        } finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.gestioAgendes totalTime={}", totalTime);
		}
        
        return "private/gestio-agendes"; 
    }
    
    @PostMapping("/gestio/agendes/save")
    /**
     * Processa la petició de l'alta o modificació d'una agenda en l'àrea privada i la desa a la BBDD
	 * @param form Objecte de formulari que conté les dades introduïdes per l'usuari.
	 * @param result Objecte BindingResult de Spring que comprova i gestiona errors de validació del formulari.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
     * @param locale La configuració regional (idioma) de l'usuari
     * @return Redirecció a la vista si la petició és correcta.
     */
    public String saveUpdateAgenda(@ModelAttribute("agendaForm") @Valid AgendaFormDto form,
		                           BindingResult result,
		                           RedirectAttributes redirectAttributes,
		                           Authentication authentication, 
		                           Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.saveUpdateAgenda startTime={}, form={}", startTime, form.toString());
		AgendaDto savedAgenda = new AgendaDto();
		try {
			
			if (result.hasErrors()) {
	            redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SAVE_AGENDES, null, locale));
	            return "redirect:/private/gestio/agendes"; 
	        }
     
            // Crear el dto per enviar a l'api
            AgendaDto agendaToSave = new AgendaDto();
            agendaToSave.setCon(form.getCon());
            agendaToSave.setDatini(form.getDatini());
            agendaToSave.setDatfin(form.getDatfin());
            
            // Lookups
            UbicacioDto centre = new UbicacioDto(); 
            centre.setCon(form.getCentreCon());
            HorariDto horari = new HorariDto(); 
            horari.setCon(form.getHorariCon());
            
            // Tècnic connectat
            TecnicDto tecnic = new TecnicDto(); 
            tecnic.setCoa(authentication.getName());
            
            agendaToSave.setCentre(centre);
            agendaToSave.setHorari(horari);
            agendaToSave.setTecnic(tecnic);
            
            // Guardar/Actualitzar           
            if (!Utils.isEmpty(form.getCon())) {
            	 savedAgenda = citaPreviaPrivateClient.updateAgenda(form.getCon(), agendaToSave, locale);
            } else {
            	savedAgenda = citaPreviaPrivateClient.saveAgenda(agendaToSave, locale);
            }

            if (savedAgenda.hasErrors()) {
                redirectAttributes.addFlashAttribute("error", savedAgenda.getErrors().get(0).getDem());
            } else {
                redirectAttributes.addFlashAttribute("success", form.getCon() == null ? bundle.getMessage(Constants.SUCCESS_FRONT_SAVE_AGENDES, null, locale) : bundle.getMessage(Constants.SUCCESS_FRONT_UPDATE_AGENDES, null, locale));
            }
    	}  catch (Exception e) {
            LOG.error("### Error PrivateController.saveUpdateAgenda {}", e);
            redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_AGENDES, null, locale));
        } finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.saveUpdateAgenda totalTime={}, savedAgenda={}", totalTime, savedAgenda.toString());
		}
            
        return "redirect:/private/gestio/agendes";
    }
    
    @PostMapping("/gestio/agendes/delete")
    /**
     * Processa la eliminació d'una agenda en l'àrea privada i la desa a la BBDD
     * @param con Codi de l'agenda a eliminar
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
     * @param locale La configuració regional (idioma) de l'usuari
     * @return Redirecció a la vista si la petició és correcta.
     */
    public String deleteAgenda(@RequestParam Long con, 
                               RedirectAttributes redirectAttributes, 
                               Locale locale) {
    	
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.deleteAgenda startTime={}, ageCon={}", startTime, con);
        try {
            ErrorDto error = citaPreviaPrivateClient.deleteAgenda(con, locale);
            if (error != null) {
            	// Errors de l'api
                redirectAttributes.addFlashAttribute("error", error.getDem());
            } else {
            	redirectAttributes.addFlashAttribute("success", bundle.getMessage(Constants.SUCCESS_FRONT_DELETE_AGENDES, null, locale));
            }
            
        } catch (Exception e) {
            LOG.error("### PrivateController.deleteAgenda {}", e);
            redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_AGENDES, null, locale));
        } finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.deleteAgenda totalTime={}, ageCon={}", totalTime, con);
		}
        return "redirect:/private/gestio/agendes";
    }
    
    @GetMapping("/gestio/horaris")
    /**
     * Recupera el llistat d'horaris del tècnic connectat
	 * @param model Model de Spring utilitzat per afegir atributs que es passaran a la vista ThymeLeaf.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
	 * @param locale La configuració regional (idioma) de l'usuari
	 * @return El nom de la vista o una redirecció en cas d'error.
     */
    public String gestioHoraris(Model model,
					    		Authentication authentication,
					    		RedirectAttributes redirectAttributes,
					    		Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.gestioHoraris startTime={}", startTime);
	    try {	
	    	
			String subaplCoa = this.getSubaplCoa(authentication);
			
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }
	        
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	            return "redirect:/private/login";
	        }
	        
	        // Obtenir subaplicació per mostrar al header
        	SubaplicacioDto subAplicacio = citaPreviaPublicClient.getSubaplicacio(subaplCoa, locale);
        	model.addAttribute("subAplicacio", subAplicacio); 
	        
	        // Obtenir llisat d'horaris
	        List<HorariDto> horaris = citaPreviaPrivateClient.getHorarisBySubaplicacio(subaplCoa, locale);
	        model.addAttribute("horaris", horaris);
	        
	        // Obtener lookups necessàries
	        List<TipusCitaDto> tipusCites = citaPreviaPrivateClient.getTipusCitesBySubaplicacio(subaplCoa, locale);
	        model.addAttribute("tipusCites", tipusCites);
	        
	        // Formulari buit
	        if (!model.containsAttribute("horariForm")) {
	            HorariFormDto form = new HorariFormDto();
	            model.addAttribute("horariForm", form);
	        }
	    }  catch (Exception e) {
	        LOG.error("### Error PrivateController.gestioHoraris:", e);
	        redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_HORARIS, null, locale));
	        return "redirect:/private/calendari";
	    } finally {
	    	long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.gestioHoraris totalTime={}", totalTime);
		}
        return "private/gestio-horaris";
    }

    @PostMapping("/gestio/horaris/save")
    /**
     * Processa la petició de l'alta o modificació d'un horari en l'àrea privada i la desa a la BBDD
	 * @param form Objecte de formulari que conté les dades introduïdes per l'usuari.
	 * @param result Objecte BindingResult de Spring que comprova i gestiona errors de validació del formulari.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
     * @param locale La configuració regional (idioma) de l'usuari
     * @return Redirecció a la vista si la petició és correcta.
     */
    public String saveUpdateHorari(@ModelAttribute("horariForm") @Valid HorariFormDto form,
                             BindingResult result,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes,
                             Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.saveUpdateHorari startTime={}, form={}", startTime, form.toString());
    	try {
        
    		if (result.hasErrors()) {
    			redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SAVE_HORARIS, null, locale));
    			return "redirect:/private/gestio/agendes"; 
    		}

            // Crear el dto per enviar a l'api
            HorariDto horariToSave = new HorariDto();
            horariToSave.setCon(form.getCon());
            horariToSave.setDec(form.getDec());
            horariToSave.setDem(form.getDem());
            
            // Lookups
            TipusCitaDto tipusCita = new TipusCitaDto();
            tipusCita.setCon(form.getTipusCitaCon());
            horariToSave.setTipusCita(tipusCita);
            
            // Assignar Subaplicació
			String subaplCoa = this.getSubaplCoa(authentication);
			
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }
	        
            SubaplicacioDto subapl = new SubaplicacioDto();
            subapl.setCoa(subaplCoa);
            horariToSave.setSubapl(subapl);
            
            // Guardar o actualitzar
            HorariDto savedAgenda = new HorariDto();
            if (!Utils.isEmpty(form.getCon())) {
            	 savedAgenda = citaPreviaPrivateClient.updateHorari(form.getCon(), horariToSave, locale);
            } else {
            	savedAgenda = citaPreviaPrivateClient.saveHorari(horariToSave, locale);
            }

            if (savedAgenda.hasErrors()) {
                // Maneig d'errors del backend
                redirectAttributes.addFlashAttribute("error", savedAgenda.getErrors().get(0).getDem());
            } else {
                redirectAttributes.addFlashAttribute("success", form.getCon() == null ? bundle.getMessage(Constants.SUCCESS_FRONT_SAVE_HORARIS, null, locale) : bundle.getMessage(Constants.SUCCESS_FRONT_UPDATE_HORARIS, null, locale));
            }
            
    	}  catch (Exception e) {
            LOG.error("### Error PrivateController.saveUpdateHorari {}", e);
            redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_HORARIS, null, locale));
        } finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.saveUpdateHorari , totalTime={}, form={}", totalTime, form.toString());
		}
              
        return "redirect:/private/gestio/horaris";
    }

    @PostMapping("/gestio/horaris/delete")
    
    /**
     * Processa la eliminació d'un horari a en l'àrea privada i la desa a la BBDD
     * @param con Codi de l'horari a eliminar
     * Processa la eliminació d'una agenda en l'àrea privada i la desa a la BBDD
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
     * @param locale La configuració regional (idioma) de l'usuari
     * @return Redirecció a la vista si la petició és correcta.
     */
    public String deleteHorari(@RequestParam("con") Long horCon,
                               RedirectAttributes redirect,
                               Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.deleteHorari startTime={}, horCon={}", startTime, horCon);
        try {
        	    	
            ErrorDto error = citaPreviaPrivateClient.deleteHorari(horCon, locale);
            if (error != null) {
            	// Errors de l'api
                redirect.addFlashAttribute("error", error.getDem());
            } else {
            	redirect.addFlashAttribute("success", bundle.getMessage(Constants.SUCCESS_FRONT_DELETE_HORARIS, null, locale));
            }
            
        } catch (Exception e) {
            LOG.error("### Error delete horari {}", e);
            redirect.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_HORARIS, null, locale));
        } finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.deleteHorari totalTime={}, horCon={}", totalTime, horCon);
		}
        
        return "redirect:/private/gestio/horaris";
    }
    
    /**
     * Obté la lookup de dies de la setmana
     * @param locale La configuració regional (idioma) de l'usuari
     * @return dies de la setmana traduits
     */
    private Map<Long, String> getDiesSetmana(Locale locale) {
        Map<Long, String> diesSetmana = new TreeMap<>();

        for (long i = 1; i <= 7; i++) {
            String key = "dia.setmana." + i; // day.1, day.2, etc.
            String nomDia = bundle.getMessage(key, null, locale); 
            diesSetmana.put(i, nomDia);
        }

        return diesSetmana;
    }
    
    @GetMapping("/gestio/horaris/{horCon}/setmanes-tipus")
    /**
     * 
     * Recupera el llistat de setmanes tipus (franges horàries) del horari seleccionat
	 * @param model Model de Spring utilitzat per afegir atributs que es passaran a la vista ThymeLeaf.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
     * @param locale La configuració regional (idioma) de l'usuari
     * @return El nom de la vista o una redirecció en cas d'error.
     */
    
    public String gestioSetmanesTipus(@PathVariable Long horCon, 
                                        Model model, 
                                        RedirectAttributes redirectAttributes, 
                                        Locale locale) {
        long startTime=System.currentTimeMillis();
        LOG.info("### Inici PrivateController.gestioSetmanesTipus startTime={}, horCon={}", startTime, horCon);

        try {
            HorariDto horariDto = citaPreviaPrivateClient.getHorari(horCon, locale);
            if (horariDto == null || horariDto.getCon() == null) {
                redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_SETMANES_TIPUS, null, locale));
                return "redirect:/private/gestio/horaris";
            }
                   	
	        if (horariDto.getSubapl()== null || Utils.isEmpty(horariDto.getSubapl().getCoa())) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	            return "redirect:/private/login";
	        }
	        
	        // Obtenir subaplicació per mostrar al header
        	SubaplicacioDto subAplicacio = citaPreviaPublicClient.getSubaplicacio(horariDto.getSubapl().getCoa(), locale);
        	model.addAttribute("subAplicacio", subAplicacio);
        	
            List<SetmanaTipusDto> setmanesTipus = citaPreviaPublicClient.getSetmanesTipusByHorari(horCon, locale);
            model.addAttribute("horariDto", horariDto);
            model.addAttribute("horariCon", horCon); 
            model.addAttribute("diesSetmana", getDiesSetmana(locale)); // Dies de la setmana
            model.addAttribute("setmanesTipus", setmanesTipus);
            
    
	        if (!model.containsAttribute("setmanaTipusForm")) {
	        	SetmanaTipusFormDto form = new SetmanaTipusFormDto();
	            model.addAttribute("setmanaTipusForm", form);
	        }
           
            return "private/gestio-setmanes-tipus";
            
        } catch (Exception e) {
            LOG.error("### Error gestioSetmanesTipus horCon={}", horCon, e);
            redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_SETMANES_TIPUS, null, locale));
            return "redirect:/private/gestio/horaris";
        } finally {
            long totalTime = (System.currentTimeMillis() - startTime);
            LOG.info("### Final PrivateController.gestioSetmanesTipus totalTime={}, horCon={}", totalTime, horCon);
        }
    }
    
 
    @PostMapping("/horaris/{horCon}/setmanes-tipus/add")
    /**
     * Processa la petició d'afegir una franja a l'horari
	 * @param form Objecte de formulari que conté les dades introduïdes per l'usuari.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
     * @param locale La configuració regional (idioma) de l'usuari
     * @return Redirecció a la vista si la petició és correcta.
     */
    public String addSetmanaTipusToHorari(@PathVariable Long horCon,
                                       	  SetmanaTipusFormDto form,
                                          RedirectAttributes redirect,
                                          Locale locale) {
     long startTime = System.currentTimeMillis();
     LOG.info("### Inici PrivateController.addSetmanaTipusToHorari startTime={}, horCon={}, form={}", startTime, horCon, form.toString());
     SetmanaTipusDto settipAdded = new SetmanaTipusDto();
    
     try {

    	 HorariDto horari = new HorariDto();
         horari.setCon(horCon);
         SetmanaTipusDto settipToAdd = new SetmanaTipusDto();
         settipToAdd.setHorari(horari);
         settipToAdd.setDiasetCon(form.getDiasetCon());
         settipToAdd.setHorini(form.getHorini());
         settipToAdd.setHorfin(form.getHorfin());
         
         // Cridar a l'api per afegir la franja a l'horari
         settipAdded = citaPreviaPrivateClient.addSetmanaTipusToHorari(settipToAdd, locale);

         if (settipAdded.hasErrors()) {
             // Error de l'api
             String errorMessage = settipAdded.getErrors().get(0).getDem();
             redirect.addFlashAttribute("error", errorMessage);
         } else {
             redirect.addFlashAttribute("success", bundle.getMessage(Constants.SUCCESS_FRONT_SAVE_SETMANES_TIPUS, null, locale));
         }
         
     } catch (Exception e) {
         LOG.error("### Error PrivateController.addSetmanaTipusToHorari {}", e);
         // Error general
         String errorMessage = bundle.getMessage(Constants.ERROR_FRONT_GESTIO_SETMANES_TIPUS, null, locale);
         redirect.addFlashAttribute("error", errorMessage);
     } finally {
         long totalTime = (System.currentTimeMillis() - startTime);
         LOG.info("### Final PrivateController.addSetmanaTipusToHorari totalTime={, } horCon={}, settipAdded={}", totalTime, horCon, settipAdded.toString());
     }
     
     return "redirect:/private/gestio/horaris/" + horCon + "/setmanes-tipus";
 }

    @PostMapping("/horaris/{horCon}/setmanes-tipus/delete")
    /**
     * Processa la petició d'eliminar una franja horària a l'horari
     * @param horCon Codi d'horari al qual pertany la franja horària
	 * @param form Objecte de formulari que conté les dades introduïdes per l'usuari.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
     * @param locale La configuració regional (idioma) de l'usuari
     * @return Redirecció a la vista si la petició és correcta.
     */
    public String deleteSetmanaTipusOfHorari(@PathVariable Long horCon,
                                             SetmanaTipusDeleteFormDto form,
                                             RedirectAttributes redirect,
                                             Locale locale) {
     long startTime = System.currentTimeMillis();
     LOG.info("### Inici PrivateController.deleteSetmanaTipusOfHorari startTime={}, horCon={}, form={}", startTime, horCon, form.toString());

     try {
         SetmanaTipusDto settipToDelete = new SetmanaTipusDto();
         HorariDto horari = new HorariDto();
         horari.setCon(horCon);
         settipToDelete.setHorari(horari);
         settipToDelete.setDiasetCon(form.getDiasetCon());
         settipToDelete.setHorini(form.getHorini());
         settipToDelete.setHorfin(form.getHorfin());

         ErrorDto resultatDelete = citaPreviaPrivateClient.deleteSetmanaTipusOfHorari(horCon, settipToDelete, locale);

         if (resultatDelete != null && !Utils.isEmpty(resultatDelete.getDem())) {
             // Error de l'api
             redirect.addFlashAttribute("error", resultatDelete.getDem());
         } else {
             redirect.addFlashAttribute("success", bundle.getMessage(Constants.SUCCESS_FRONT_DELETE_SETMANES_TIPUS, null, locale));
         }
     } catch (Exception e) {
         LOG.error("### Error PrivateController.deleteSetmanaTipusOfHorari {}", e);
         redirect.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_API_CRUD_SETMANES_TIPUS, null, locale));
     } finally {
         long totalTime = (System.currentTimeMillis() - startTime);
         LOG.info("### Final PrivateController.deleteSetmanaTipusOfHorari totalTime={}, horCon={}", totalTime, horCon);
     }

     return "redirect:/private/gestio/horaris/" + horCon + "/setmanes-tipus";
 }

    /**
     * Recupera el llistat d'agendes del tècnic connectat
	 * @param model Model de Spring utilitzat per afegir atributs que es passaran a la vista ThymeLeaf.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
	 * @param locale La configuració regional (idioma) de l'usuari
	 * @return El nom de la vista o una redirecció en cas d'error.
     */ 
    @GetMapping("/gestio/tecnics")
    public String gestioTecnics(Model model,
					    		RedirectAttributes redirectAttributes,
					    		Authentication authentication, 
					    		Locale locale) {
        long startTime = System.currentTimeMillis();
        LOG.info("### Inici PrivateController.gestioTecnics startTime={}", startTime);
        
        try {
        	
			String subaplCoa = this.getSubaplCoa(authentication);
			
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }
	        
	        // Obtenir subaplicació per mostrar al header
	        	SubaplicacioDto subAplicacio = citaPreviaPublicClient.getSubaplicacio(subaplCoa, locale);
	        	model.addAttribute("subAplicacio", subAplicacio); 

            // Llistat de Tècnics
            List<TecnicDto> tecnicsList = citaPreviaPrivateClient.getTecnicsBySubaplicacio(subaplCoa, locale);
            model.addAttribute("tecnics", tecnicsList);
            
	        // Formulari buit
	        if (!model.containsAttribute("tecnicForm")) {
	        	TecnicFormDto form = new TecnicFormDto();
	            model.addAttribute("tecnicForm", form);
	        }

	    }  catch (Exception e) {
	        LOG.error("### Error PrivateController.gestioTecnics {}", e);
	        redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_HORARIS, null, locale));
	        return "redirect:/private/calendari";
	    } finally {
	    	long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.gestioHoraris totalTime={}", totalTime);
		}
        
        return "private/gestio-tecnics";
    }
    
    @PostMapping("/gestio/tecnics/save")
    /**
     * Processa la petició de l'alta o modificació d'un tècnic en l'àrea privada i la desa a la BBDD
	 * @param form Objecte de formulari que conté les dades introduïdes per l'usuari.
	 * @param result Objecte BindingResult de Spring que comprova i gestiona errors de validació del formulari.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
     * @param locale La configuració regional (idioma) de l'usuari
     * @return Redirecció a la vista si la petició és correcta.
     */
    public String saveUpdateTecnic(@Valid @ModelAttribute TecnicFormDto form,
						    		Authentication authentication,
						            BindingResult result,
						            RedirectAttributes redirectAttributes,
						            Locale locale) {

        long startTime = System.currentTimeMillis();
        LOG.info("### Inici PrivateController.saveUpdateTecnic startTime={}, form={}", startTime, form.toString());   
        TecnicDto savedTecnic = new TecnicDto();

        try {
        	
			if (result.hasErrors()) {
	            redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TECNICS, null, locale));
	            return "redirect:/private/gestio/tecnics"; 
	        }
			
			String subaplCoa = this.getSubaplCoa(authentication);
			
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }

            // Crear el dto per enviar a l'api
            TecnicDto tecnicToSave = new TecnicDto();
            tecnicToSave.setCoa(form.getCoa());
            tecnicToSave.setPass(form.getPass());
            tecnicToSave.setNom(form.getNom());
            tecnicToSave.setLl1(form.getLl1());
            tecnicToSave.setLl2(form.getLl2());
            tecnicToSave.setPrf(form.getPrf() + "_" + subaplCoa);
	            
            // Guardar/Actualitzar  
	        if (!Utils.isEmpty(form.getOriginalCoa())) {
	            savedTecnic = citaPreviaPrivateClient.updateTecnic(form.getCoa(), tecnicToSave, locale);
	        } else {
	            savedTecnic = citaPreviaPrivateClient.saveTecnic(tecnicToSave, locale);
	        }
	        
            if (savedTecnic.hasErrors()) {
                redirectAttributes.addFlashAttribute("error", savedTecnic.getErrors().get(0).getDem());
            } else {
                redirectAttributes.addFlashAttribute("success", form.getCoa() == null ? bundle.getMessage(Constants.SUCCESS_FRONT_SAVE_TECNICS, null, locale) : bundle.getMessage(Constants.SUCCESS_FRONT_UPDATE_TECNICS, null, locale));
            }

        } catch (Exception e) {       	
        	LOG.error("### Error PrivateController.saveUpdateTecnic {}", e);
            redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TECNICS, null, locale));
        } finally {
            long totalTime = (System.currentTimeMillis() - startTime);
            LOG.info("### Final PrivateController.saveUpdateTecnic totalTime={}, savedTecnic={}", totalTime, savedTecnic.toString());
        }

        return "redirect:/private/gestio/tecnics";
    }
    
    @PostMapping("/gestio/tecnics/delete")
    /**
     * Processa la eliminació d'una tècnic en l'àrea privada i la desa a la BBDD
     * @param coa Codi del tècnic a eliminar
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
     * @param locale La configuració regional (idioma) de l'usuari
     * @return Redirecció a la vista si la petició és correcta.
     */
    public String deleteTecnic(@RequestParam String coa,
                               RedirectAttributes redirectAttributes,
                               Authentication authentication,
                               Locale locale) {

        long startTime = System.currentTimeMillis();
        LOG.info("### Inici PrivateController.deleteTecnic startTime, coa={}", startTime, coa);
        try {
			String subaplCoa = this.getSubaplCoa(authentication);
			
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }

            // Esborrar el tècnic
            ErrorDto error = citaPreviaPrivateClient.deleteTecnic(coa, locale);

            if (error != null && !Utils.isEmpty(error.getDem())) {
            	// Errors de l'api
                redirectAttributes.addFlashAttribute("error", error.getDem());
            } else {
            	redirectAttributes.addFlashAttribute("success", bundle.getMessage(Constants.SUCCESS_FRONT_DELETE_TECNICS, null, locale));
            }           

        } catch (Exception e) {
            LOG.error("### Error PrivateController.deleteTecnic: ", e);
            redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TECNICS, null, locale));
        } finally {
            long totalTime = (System.currentTimeMillis() - startTime);
            LOG.info("### Final PrivateController.deleteTecnic totalTime={}, coa={}", totalTime, coa);
        }

        return "redirect:/private/gestio/tecnics";
    }


    @GetMapping("/gestio/tipus-cites")
    /**
     * Recupera el llistat de tipus de cites (part administrativa)
	 * @param model Model de Spring utilitzat per afegir atributs que es passaran a la vista ThymeLeaf.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
	 * @param locale La configuració regional (idioma) de l'usuari
	 * @return El nom de la vista o una redirecció en cas d'error.
     */   
    public String gestioTipusCites(Model model,
                                    Authentication authentication,
    					    		RedirectAttributes redirectAttributes, 
                                    Locale locale) {
        
        long startTime = System.currentTimeMillis();
        LOG.info("### Inici PrivateController.gestioTipusCites startTime, coa={}", startTime);
        
    	try {
        	
			String subaplCoa = this.getSubaplCoa(authentication);
			
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }
	        
	        // Obtenir subaplicació per mostrar al header
	        SubaplicacioDto subAplicacio = citaPreviaPublicClient.getSubaplicacio(subaplCoa, locale);
	        model.addAttribute("subAplicacio", subAplicacio); 
        	
	        // Afegir al model el llistat de tipus de cites
            List<TipusCitaDto> tipusCites = citaPreviaPrivateClient.getTipusCitesBySubaplicacio(subaplCoa, locale);
            model.addAttribute("tipusCites", tipusCites != null ? tipusCites : Collections.emptyList());
        
	        // Formulari buit
	        if (!model.containsAttribute("tipusCitaForm")) {
	        	TipusCitaFormDto form = new TipusCitaFormDto();
	            model.addAttribute("tipusCitaForm", form);
	        }

    	}  catch (Exception e) {
            LOG.error("### Error PrivateController.gestioTipusCites", e);
            redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TIPUS_CITES, null, locale));
            return "redirect:/private/calendari";
        } finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.gestioTipusCites totalTime={}", totalTime);
		}

        return "private/gestio-tipus-cites";
    }

    @PostMapping("/gestio/tipus-cites/save")
    /**
     * Processa la petició de l'alta o modificació d'un tipus de cita en l'àrea privada i la desa a la BBDD
	 * @param form Objecte de formulari que conté les dades introduïdes per l'usuari.
	 * @param result Objecte BindingResult de Spring que comprova i gestiona errors de validació del formulari.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
     * @param locale La configuració regional (idioma) de l'usuari
     * @return Redirecció a la vista si la petició és correcta.
     */
    public String saveUpdateTipusCita(@Valid @ModelAttribute("tipusCitaForm") TipusCitaFormDto form,
                                	  BindingResult result,
                                	  RedirectAttributes redirectAttributes,
                                	  Authentication authentication,
                                	  Locale locale) {

        long startTime = System.currentTimeMillis();
        LOG.info("### Inici PrivateController.saveUpdateTipusCita startTime, form={}", startTime, form.toString());
    	TipusCitaDto savedTipusCita = new TipusCitaDto();	
    	try {    	
    			if (result.hasErrors()) {
    				redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TECNICS, null, locale));
    				return "redirect:/private/gestio/tipus-cites"; 
    			}
		  	
	        // Assignar Subaplicació
			String subaplCoa = this.getSubaplCoa(authentication);
			
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }

	        //Guardar/Actualitzar       
	        TipusCitaDto tipusCitaToSave = new TipusCitaDto();
	        tipusCitaToSave.setCon(form.getCon());
	        tipusCitaToSave.setDec(form.getDec());
	        tipusCitaToSave.setDem(form.getDem());
	        tipusCitaToSave.setTipcitmod(ModalitatTipusCita.valueOf(form.getTipcitmod()));
	        SubaplicacioDto subapl = new SubaplicacioDto();
	        subapl.setCoa(subaplCoa);
	        tipusCitaToSave.setSubapl(subapl);
	        
	        if (!Utils.isEmpty(form.getCon())) {
	        	savedTipusCita = citaPreviaPrivateClient.updateTipusCita(form.getCon(), tipusCitaToSave, locale);
	        } else {
	        	savedTipusCita = citaPreviaPrivateClient.saveTipusCita(tipusCitaToSave, locale);
	        }
	
	        if (savedTipusCita.hasErrors()) {
	            // Errors de l'api
	            redirectAttributes.addFlashAttribute("error", savedTipusCita.getErrors().get(0).getDem());
	        } else {
	            redirectAttributes.addFlashAttribute("success", form.getCon() == null ? bundle.getMessage(Constants.SUCCESS_FRONT_SAVE_TIPUS_CITES, null, locale) : bundle.getMessage(Constants.SUCCESS_FRONT_UPDATE_TIPUS_CITES, null, locale));
	        }
	        
    	}  catch (Exception e) {
            LOG.error("### Error PrivateController.saveUpdateTipusCita: ", e);
            redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TIPUS_CITES, null, locale));
        } finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.saveUpdateTipusCita totalTime={}, savedTipusCita={}", totalTime, savedTipusCita.toString());
		}

        return "redirect:/private/gestio/tipus-cites";
    }

    @PostMapping("/gestio/tipus-cites/delete")
    /**
     * Processa la eliminació d'un tipus de cita en l'àrea privada i la desa a la BBDD
     * @param coa Codi del tipus de cita a eliminar
	 * @param redirectAttributes Afegir missatges 'flash' (amb errors) que es mantindran després d'una redirecció.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
     * @param locale La configuració regional (idioma) de l'usuari
     * @return Redirecció a la vista si la petició és correcta.
     */
    public String deleteTipusCita(@RequestParam Long con,
                                  RedirectAttributes redirectAttributes,
                                  Authentication authentication,
                                  Locale locale) {
    	
        long startTime = System.currentTimeMillis();
        LOG.info("### Inici PrivateController.deleteTipusCita startTime, con={}", startTime, con);
        try {
			String subaplCoa = this.getSubaplCoa(authentication);
			
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }

            ErrorDto error = citaPreviaPrivateClient.deleteTipusCita(con, locale);

            if (error != null && !Utils.isEmpty(error.getDem())) {
            	// Error de l'api
                redirectAttributes.addFlashAttribute("error", error.getDem());
            } else {
                redirectAttributes.addFlashAttribute("success", bundle.getMessage(Constants.SUCCESS_FRONT_DELETE_TIPUS_CITES, null, locale));
            }

        } catch (Exception e) {
            LOG.error("### Error PrivateController.deleteTipusCita: ", e);
            redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TIPUS_CITES, null, locale));
        } finally {
            long totalTime = (System.currentTimeMillis() - startTime);
            LOG.info("### Final PrivateController.deleteTecnic totalTime={}, con={}", totalTime, con);
        }

        return "redirect:/private/gestio/tipus-cites";
    }

    /**
     * Recupera el llistat d'ubicacions (part administrativa)
	 * @param model Model de Spring utilitzat per afegir atributs que es passaran a la vista ThymeLeaf.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
	 * @param locale La configuració regional (idioma) de l'usuari
	 * @return El nom de la vista o una redirecció en cas d'error.
     */   
    @GetMapping("/gestio/ubicacions")
    public String gestioUbicacions(Model model,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes, 
                                   Locale locale) {

		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.gestioUbicacions startTime={}", startTime);
	    try {	
	    	
			String subaplCoa = this.getSubaplCoa(authentication);
			
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }
	        
	        // Obtenir subaplicació per mostrar al header
	        SubaplicacioDto subAplicacio = citaPreviaPublicClient.getSubaplicacio(subaplCoa, locale);
	        model.addAttribute("subAplicacio", subAplicacio); 
	        
	        // Llistat d'ubicacions de la subaplicació
            List<UbicacioDto> ubicacions = citaPreviaPrivateClient.getUbicacionsBySubaplicacio(subaplCoa, locale);
            model.addAttribute("ubicacions", ubicacions != null ? ubicacions : Collections.emptyList());

	        // Formulari buit
	        if (!model.containsAttribute("ubicacioForm")) {
	        	UbicacioFormDto form = new UbicacioFormDto();
	            model.addAttribute("ubicacioForm", form);
	        }

	    }  catch (Exception e) {
	        LOG.error("### Error PrivateController.gestioUbicacions: ", e);
	        redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_UBICACIONS, null, locale));
	        return "redirect:/private/calendari";
	    } finally {
	    	long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.gestioUbicacions totalTime={}", totalTime);
		}

        return "private/gestio-ubicacions";
    }

    @PostMapping("/gestio/ubicacions/save")
    /**
     * Processa la petició de l'alta o modificació d'una ubicació en l'àrea privada i la desa a la BBDD
	 * @param form Objecte de formulari que conté les dades introduïdes per l'usuari.
	 * @param result Objecte BindingResult de Spring que comprova i gestiona errors de validació del formulari.
	 * @param redirectAttributes Afegir missatges 'flash' (com errors) que es mantindran després d'una redirecció.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
     * @param locale La configuració regional (idioma) de l'usuari
     * @return Redirecció a la vista si la petició és correcta.
     */
    public String saveUpdateUbicacio(@Valid @ModelAttribute("ubicacioForm") UbicacioFormDto form,
		                               BindingResult result,
		                               RedirectAttributes redirectAttributes,
		                               Authentication authentication,
		                               Locale locale) {
        
    	long startTime = System.currentTimeMillis();
        LOG.info("### Inici PrivateController.saveUpdateUbicacio startTime, form={}", startTime, form.toString());
    	
    	UbicacioDto savedUbicacio = new UbicacioDto();
    	try {
	            			
    		if (result.hasErrors()) {
    			redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_UBICACIONS, null, locale));
    	        return "redirect:/private/gestio/ubicacions"; 
    	    }
    		   		
    		String subaplCoa = getSubaplCoa(authentication);
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }
	
        	// Guardar
            UbicacioDto ubicacioToSave = new UbicacioDto();
            ubicacioToSave.setCon(form.getCon());
            ubicacioToSave.setNom(form.getNom());
            ubicacioToSave.setNomcar(form.getNomcar());
            ubicacioToSave.setObs(form.getObs());
            SubaplicacioDto subaplicacio = new SubaplicacioDto();
            subaplicacio.setCoa(subaplCoa);
            ubicacioToSave.setSubapl(subaplicacio);

            if (!Utils.isEmpty(form.getCon())) {
            	savedUbicacio = citaPreviaPrivateClient.updateUbicacio(form.getCon(), ubicacioToSave, locale);
            } else {
            	savedUbicacio = citaPreviaPrivateClient.saveUbicacio(ubicacioToSave, locale);
            }

            if (savedUbicacio.hasErrors()) {
                // Maneig d'errors de de l'api
                redirectAttributes.addFlashAttribute("error", savedUbicacio.getErrors().get(0).getDem());
            } else {
                redirectAttributes.addFlashAttribute("success", form.getCon() == null ? bundle.getMessage(Constants.SUCCESS_FRONT_SAVE_UBICACIONS, null, locale) : bundle.getMessage(Constants.SUCCESS_FRONT_UPDATE_UBICACIONS, null, locale));
            }
            
        } catch (Exception e) {       	
        	LOG.error("### Error PrivateController.saveUpdateUbicacio: ", e);
            redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_UBICACIONS, null, locale));
        } finally {
            long totalTime = (System.currentTimeMillis() - startTime);
            LOG.info("### Final PrivateController.saveUpdateUbicacio totalTime={}, savedUbicacio={}", totalTime, savedUbicacio.toString());
        }

        return "redirect:/private/gestio/ubicacions";
    }

    @PostMapping("/gestio/ubicacions/delete")
    /**
     * Processa la eliminació d'una ubicació en l'àrea privada i la desa a la BBDD
     * @param coa Codi de la ubicació a eliminar
	 * @param redirectAttributes Afegir missatges 'flash' (amb errors) que es mantindran després d'una redirecció.
	 * @param authentication Objecte d'autenticació de Spring Security que conté la informació de l'usuari que ha fet login.
     * @param locale La configuració regional (idioma) de l'usuari
     * @return Redirecció a la vista si la petició és correcta.
     */
    public String deleteUbicacio(@RequestParam Long con,
                                 RedirectAttributes redirectAttributes,
                                 Authentication authentication,
                                 Locale locale) {
        long startTime = System.currentTimeMillis();
        LOG.info("### Inici PrivateController.deleteUbicacio startTime, con={}", startTime, con);
        try {
			String subaplCoa = this.getSubaplCoa(authentication);
			
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }

            ErrorDto error = citaPreviaPrivateClient.deleteUbicacio(con, locale);

            if (error != null && !Utils.isEmpty(error.getDem())) {
            	// Error de l'api
                redirectAttributes.addFlashAttribute("error", error.getDem());
            } else {
                redirectAttributes.addFlashAttribute("success", bundle.getMessage(Constants.SUCCESS_FRONT_DELETE_UBICACIONS, null, locale));
            }

        } catch (Exception e) {
            LOG.error("### Error PrivateController.deleteUbicacio: ", e);
            redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_UBICACIONS, null, locale));
        } finally {
            long totalTime = (System.currentTimeMillis() - startTime);
            LOG.info("### Final PrivateController.deleteUbicacio totalTime={}, con={}", totalTime, con);
        }

        return "redirect:/private/gestio/ubicacions";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication,
			Model model,
			RedirectAttributes redirectAttributes,
			Locale locale) {
    	
        long startTime = System.currentTimeMillis();
        LOG.info("### Inici PrivateController.dashboard startTime, con={}", startTime);
        try {
    	
        	String subaplCoa = this.getSubaplCoa(authentication);
		
	        if (Utils.isEmpty(subaplCoa)) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "redirect:/private/calendari";
	        }
	    	
			model.addAttribute("subaplCoa", subaplCoa);
			
	        SubaplicacioDto subAplicacio = citaPreviaPublicClient.getSubaplicacio(subaplCoa, locale);
	        model.addAttribute("subAplicacio", subAplicacio);
	        
	    } catch (Exception e) {
	        LOG.error("### Error PrivateController.dashboard: ", e);
	        redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_UBICACIONS, null, locale));
	    } finally {
	        long totalTime = (System.currentTimeMillis() - startTime);
	        LOG.info("### Final PrivateController.dashboard totalTime={}", totalTime);
	    }
        return "private/dashboard";
    }
    
}