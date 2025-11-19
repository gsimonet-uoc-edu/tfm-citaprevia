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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
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
import uoc.edu.citaprevia.front.service.CitaPreviaPrivateClient;
import uoc.edu.citaprevia.front.service.CitaPreviaPublicClient;
import uoc.edu.citaprevia.front.service.MetacamapService;
import uoc.edu.citaprevia.model.Perfil;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@Controller
@RequestMapping("/private")
public class PrivateController {
	
	private static final Logger LOG = LoggerFactory.getLogger(PrivateController.class);
	
	@Value("${citaprevia.service.api.host}")
	private String urlCitapreviaApi;

	@Autowired
	private CitaPreviaPublicClient citaPreviaPublicClient;
	
    
    @Autowired
    private CitaPreviaPrivateClient citaPreviaPrivateClient;
    
    @Autowired
    private MetacamapService metacamapService;
    
	@Autowired
	private MessageSource bundle;

	@GetMapping("/login")
	public String login() {
		return "private/login";
	}
	
	@GetMapping("/calendari") 
	public String calendari(Authentication authentication, Model model, Locale locale) throws Exception {
		
        /*TODO if (authentication == null || authentication.getAuthorities() == null) {
	        redirect.addFlashAttribute("error", "Dades incorrectes");
	        return "redirect:/private/calendari";
        }*/
        
		String coa = authentication.getName();
		TecnicDto tecnic = citaPreviaPrivateClient.getTecnic(coa, locale);

		if (tecnic == null) {
			return "redirect:/private/login";
		}

        List<AgendaDto> agendes;
        String subaplCoa = StringUtils.substringAfter(tecnic.getPrf(), "_"); // Extreim el codi de la subaplicació a partir sufixe del perfil.
        //String prefixePerfil = StringUtils.substringBefore(tecnic.getPrf(), "_");
        boolean isAdministrador = this.isAdministrador(authentication);
        if (isAdministrador) {
            agendes = citaPreviaPrivateClient.getAgendasBySubaplicacio(subaplCoa, locale);
        } else {
            agendes = citaPreviaPrivateClient.getAgendasByTecnic(tecnic.getCoa(), locale);    
        }
        

        model.addAttribute("isAdministrador", isAdministrador);
		model.addAttribute("subaplCoa", subaplCoa); 
		model.addAttribute("tipusCita", new TipusCitaDto()); //TODO: mirar
		//model.addAttribute("tipusCita", null); // No hay selección de tipo en private
		model.addAttribute("frangesHorariesGrouped", generarEvents(agendes, citaPreviaPublicClient, locale));

		model.addAttribute("dataInici", LocalDate.now());
		model.addAttribute("dataFi", LocalDate.now().plusDays(30));
		model.addAttribute("tecnic", tecnic);

		// AÑADIR CAMPOS DINÁMICOS
	    List<CampConfigDto> campos = metacamapService.getCampos(subaplCoa, locale);
	    model.addAttribute("camposCita", campos);
	    
		return "/private/calendari-private";
	}
	
	@PostMapping("/cita/reserva")
	public String reservaCitaPrivada(
	        @ModelAttribute CitaFormDto form,
	        BindingResult result,
	        Model model,
	        RedirectAttributes redirect,
	        Authentication authentication,
	        Locale locale) throws Exception {

	    if (result.hasErrors()) {
	        redirect.addFlashAttribute("error", "Dades incorrectes");
	        return "redirect:/private/calendari";
	    }

	    String coa = authentication.getName();
	    TecnicDto tecnic = citaPreviaPrivateClient.getTecnic(coa, locale);
	    if (tecnic == null) {
	        return "redirect:/private/login";
	    }

	    String subaplCoa = StringUtils.substringAfter(tecnic.getPrf(), "_");

	    // Obtener agenda y tipo de cita
	    AgendaDto agenda = citaPreviaPublicClient.getAgenda(form.getAgendaCon(), locale);
	    if (agenda == null || !subaplCoa.equals(agenda.getHorari().getSubapl().getCoa())) {
	        redirect.addFlashAttribute("error", "Agenda no vàlida");
	        return "redirect:/private/calendari";
	    }

	    TipusCitaDto tipusCita = agenda.getHorari().getTipusCita();

	    // Crear cita
	    CitaDto cita = new CitaDto();
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
	    cita.setAgenda(agenda);
	    cita.setTipusCita(tipusCita);

	    // Guardar TODO
	    CitaDto insertCita = citaPreviaPublicClient.saveCita(cita, locale);
	    if (insertCita == null || Utils.isEmpty(insertCita.getCon())) {
	        redirect.addFlashAttribute("error", "Error al guardar la cita");
	        return "redirect:/private/calendari";
	    }

	    return "redirect:/private/cita/confirmacio?con=" + insertCita.getCon() + "&accion=creada";
	}

	@GetMapping("/cita/confirmacio")
	public String confirmacioCita(
	        @RequestParam Long con,
	        @RequestParam String accion,
	        Model model,
	        Authentication authentication,
	        Locale locale) throws Exception {

	    String coa = authentication.getName();
	    TecnicDto tecnic = citaPreviaPrivateClient.getTecnic(coa, locale);
	    if (tecnic == null) {
	        return "redirect:/private/login";
	    }

	    String subaplCoa = StringUtils.substringAfter(tecnic.getPrf(), "_");

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
	    model.addAttribute("accion", accion); // "creada", "actualitzada", "cancelada"

	    return "private/confirmacio-private";
	}
	
	@PostMapping("/cita/actualitzar")
	public String actualitzarCita(
	        @ModelAttribute CitaFormDto form,
	        BindingResult result,
	        RedirectAttributes redirect,
	        Authentication authentication,
	        Locale locale) throws Exception {

	    if (result.hasErrors()) {
	        redirect.addFlashAttribute("error", "Dades incorrectes");
	        return "redirect:/private/calendari";
	    }

	    String coa = authentication.getName();
	    TecnicDto tecnic = citaPreviaPrivateClient.getTecnic(coa, locale);
	    if (tecnic == null) return "redirect:/private/login";

	    String subaplCoa = StringUtils.substringAfter(tecnic.getPrf(), "_");

	    CitaDto cita = citaPreviaPublicClient.getCita(form.getCitaCon(), locale);
	    if (cita == null || !subaplCoa.equals(cita.getAgenda().getHorari().getSubapl().getCoa())) {
	        redirect.addFlashAttribute("error", "Cita no vàlida");
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

	    CitaDto updateCita = citaPreviaPrivateClient.updateCita(form.getCitaCon(), cita, locale);
	    if (updateCita == null || Utils.isEmpty(updateCita.getCon()) || updateCita.hasErrors()) {
	        redirect.addFlashAttribute("error", "Error al actualitzar");
	    } else {
	        return "redirect:/private/cita/confirmacio?con=" + updateCita.getCon() + "&accion=actualitzada";
	    }
	    return "redirect:/private/calendari";
	}

	@PostMapping("/cita/cancelar")
	public String cancelarCita(
	        @RequestParam Long citaCon,
	        Authentication authentication,
	        RedirectAttributes redirect,
	        Locale locale) throws Exception {

	    String coa = authentication.getName();
	    TecnicDto tecnic = citaPreviaPrivateClient.getTecnic(coa, locale);
	    if (tecnic == null) return "redirect:/private/login";

	    String subaplCoa = StringUtils.substringAfter(tecnic.getPrf(), "_");

	    CitaDto cita = citaPreviaPublicClient.getCita(citaCon, locale);
	    if (cita == null || !subaplCoa.equals(cita.getAgenda().getHorari().getSubapl().getCoa())) {
	        redirect.addFlashAttribute("error", "Cita no vàlida");
	        return "redirect:/private/calendari";
	    }

	    ErrorDto error = citaPreviaPrivateClient.deleteCita(citaCon, locale);
	    if (error == null) {
	        return "redirect:/private/cita/confirmacio?con=" + citaCon + "&accion=cancelada";
	    } else {
	        redirect.addFlashAttribute("error", "Error al cancel·lar");
	        return "redirect:/private/calendari";
	    }
	}
	
	private Map<LocalDate, List<Map<String, Object>>> generarEvents(List<AgendaDto> agendes, CitaPreviaPublicClient client, Locale locale) {
	    Map<LocalDate, List<Map<String, Object>>> grouped = new TreeMap<>();

	    for (AgendaDto agenda : agendes) {
	        LocalDate dataInici = agenda.getDatini();
	        LocalDate dataFi = agenda.getDatfin();

	        for (LocalDate data = dataInici; !data.isAfter(dataFi); data = data.plusDays(1)) {
	            int diaSetmana = data.getDayOfWeek().getValue();

	            List<SetmanaTipusDto> franges = client.getSetmanesTipusByHorari(agenda.getHorari().getCon(), locale);

	            List<Map<String, Object>> dayEvents = new ArrayList<>();

	            for (SetmanaTipusDto franja : franges) {
	                if (franja.getDiasetCon() == diaSetmana) {
	                    LocalDateTime inici = LocalDateTime.of(data, franja.getHorini());
	                    LocalDateTime fi = LocalDateTime.of(data, franja.getHorfin());
	                    inici = inici.plusSeconds(1);
	                    fi = fi.plusSeconds(1);
	                    

	                    // Verificar si ya hay cita (solapamiento)
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
	                    }
	                    if (ocupada) {
	                    	event.put("citaCon", existeixCita.getCon());
	                    	
	                    	// *** INICI DE LA MODIFICACIÓ: AFEGIR DADES DE LA CITA EXISTENT ***
	                        event.put("nom", existeixCita.getNom());
	                        event.put("llis", existeixCita.getLlis());
	                        event.put("numdoc", existeixCita.getNumdoc());
	                        event.put("nomcar", existeixCita.getNomcar());
	                        // Assegura't que `tel` és un String per al frontend
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
	                        // *** FINAL DE LA MODIFICACIÓ ***
	                    }
	                    dayEvents.add(event);
	                }
	            }

	            if (!dayEvents.isEmpty()) {
	                grouped.put(data, dayEvents);
	            }
	        }
	    }

	    return grouped;
	}
	
	/**
     * Mètode per determinar si l'usuari autenticat té un rol d'administrador.
     * CAL ADAPTAR A LA VOSTRA LÒGICA DE SPRING SECURITY (ROLS/GRUPS).
     */
    private boolean isAdministrador(Authentication authentication) {
        if (authentication == null || authentication.getAuthorities() == null) {
            return false;
        }
        
        //String prefixePerfil = StringUtils.substringBefore(tecnic.getPrf(), "_");

        // Comprova si l'usuari té el rol "ADMINISTRADOR" o "TECNIC" (Exemple)
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String prefixePerfil = StringUtils.substringBefore(authority.getAuthority(), "_");
            if (prefixePerfil.equals(Perfil.ADMINISTRADOR.getValor())) {
                return true;
            }
        }
        return false;
    }
    
    
 // Mètode de Consulta (GET)
    @GetMapping("/gestio/agendes")
    public String gestioAgendes(Model model, Authentication authentication, RedirectAttributes redirect, Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.gestioAgendes startTime={}", startTime);
    	try {
	        // 1. Obtenir el tècnic connectat (assumint que el 'name' és el 'coa')
	        String tecCoa = authentication.getName();
	        String subaplCoa = null;
	        // Obtenir subaplicacio del perfil de l'usuari
	        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
	            for (GrantedAuthority authority : authentication.getAuthorities()) {
	                subaplCoa = StringUtils.substringAfter(authority.getAuthority(), "_");
	                break;
	            }
	        }
	        
	        
	        // 2. Obtenir llistes de lookups
	        List<UbicacioDto> ubicacions = citaPreviaPrivateClient.getUbicacions(locale);
	        List<HorariDto> horaris = citaPreviaPrivateClient.getHorarisBySubaplicacio(subaplCoa, locale);
	
	        // 3. Obtenir llistat d'agendes del tècnic connectat
	        List<AgendaDto> agendes = citaPreviaPrivateClient.getAgendasByTecnic(tecCoa, locale);
	        
	        // 4. Afegir a la vista
	        model.addAttribute("agendes", agendes);
	        model.addAttribute("ubicacions", ubicacions);
	        model.addAttribute("horaris", horaris);
	        model.addAttribute("agendaForm", new AgendaFormDto()); // Formulari buit per a l'alta
	        // 5. Determinar si l'usuari és administrador (es manté la lògica prèvia)
	        model.addAttribute("isAdministrador", this.isAdministrador(authentication));
    	}  catch (Exception e) {
            LOG.error("### Error gestio agenda {}", e);
            redirect.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_AGENDES, null, locale));
            return "redirect:/private/calendari";
        } finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.gestioAgendes totalTime={}", totalTime);
		}
        
        return "private/gestio-agendes"; 
    }
    
    
    // Mètode de Creació/Actualització (POST)
    @PostMapping("/gestio/agendes/save")
    public String saveUpdateAgenda(@ModelAttribute("agendaForm") @Valid AgendaFormDto form,
                             BindingResult result,
                             RedirectAttributes redirect,
                             Authentication authentication, 
                             Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.saveAgenda startTime={}", startTime);
		try {
			if (result.hasErrors()) {
	            redirect.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SAVE_AGENDES, null, locale));
	            return "redirect:/private/gestio/agendes"; 
	        }
     
            // 1. Crear el DTO per enviar al backend
            AgendaDto agendaToSave = new AgendaDto();
            agendaToSave.setCon(form.getCon());
            agendaToSave.setDatini(form.getDatini());
            agendaToSave.setDatfin(form.getDatfin());
            
            // 2. Lookup de DTOs per CON
            UbicacioDto centre = new UbicacioDto(); 
            centre.setCon(form.getCentreCon());
            HorariDto horari = new HorariDto(); 
            horari.setCon(form.getHorariCon());
            
            // 3. Tècnic connectat
            TecnicDto tecnic = new TecnicDto(); 
            tecnic.setCoa(authentication.getName());
            
            agendaToSave.setCentre(centre);
            agendaToSave.setHorari(horari);
            agendaToSave.setTecnic(tecnic);
            
            // 4. Guardar
            //AgendaDto savedAgenda = citaPreviaPrivateClient.saveAgenda(agendaSaved, locale);
            AgendaDto savedAgenda = new AgendaDto();
            if (form.getCon() != null) {
            	 savedAgenda = citaPreviaPrivateClient.updateAgenda(form.getCon(), agendaToSave, locale);
            } else {
            	savedAgenda = citaPreviaPrivateClient.saveAgenda(agendaToSave, locale);
            }

            if (savedAgenda.hasErrors()) {
                // Maneig d'errors del backend
                redirect.addFlashAttribute("error", savedAgenda.getErrors().get(0).getDem());
            } else {
                redirect.addFlashAttribute("success", form.getCon() == null ? bundle.getMessage(Constants.SUCCESS_FRONT_SAVE_AGENDES, null, locale) : bundle.getMessage(Constants.SUCCESS_FRONT_UPDATE_AGENDES, null, locale));
            }
    	}  catch (Exception e) {
            LOG.error("### Error save/update agenda {}", e);
            redirect.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_AGENDES, null, locale));
        } finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.saveAgenda totalTime={}", totalTime);
		}
        
        
        return "redirect:/private/gestio/agendes";
    }
    
    // Mètode d'Eliminació (POST)
    @PostMapping("/gestio/agendes/delete")
    public String deleteAgenda(@RequestParam("con") Long con, 
                               RedirectAttributes redirect, 
                               Locale locale) {
    	
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.deleteAgenda startTime={}, ageCon={}", startTime, con);
        try {
            ErrorDto error = citaPreviaPrivateClient.deleteAgenda(con, locale);
            if (error != null) {
                // Maneig d'errors del backend
                redirect.addFlashAttribute("error", error.getDem());
            } else {
            	redirect.addFlashAttribute("success", bundle.getMessage(Constants.SUCCESS_FRONT_DELETE_AGENDES, null, locale));
            }
            
        } catch (Exception e) {
            LOG.error("### Error delete agenda {}", e);
            redirect.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_AGENDES, null, locale));
        } finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.deleteAgenda totalTime={}, ageCon={}", totalTime, con);
		}
        return "redirect:/private/gestio/agendes";
    }
    
    @GetMapping("/gestio/horaris")
    public String gestioHoraris(Model model, Authentication authentication, RedirectAttributes redirect, Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.gestioHoraris startTime={}", startTime);
	    try {	      
	    	// 1. Obtenir el tècnic connectat (assumint que el 'name' és el 'coa')
		    String subaplCoa = null;
		    // Obtenir subaplicacio del perfil de l'usuari
		    if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
		        for (GrantedAuthority authority : authentication.getAuthorities()) {
		            subaplCoa = StringUtils.substringAfter(authority.getAuthority(), "_");
		            break;
		         }
		     }
	        
	        // 1. Obtener listado de Horarios
	        List<HorariDto> horaris = citaPreviaPrivateClient.getHorarisBySubaplicacio(subaplCoa, locale);
	        model.addAttribute("horaris", horaris);
	        
	        // 2. Obtener Lookups: Tipos de Cita
	        // Solo se necesitan los Tipos de Cita asociados a esta subaplicación
	        List<TipusCitaDto> tipusCites = citaPreviaPrivateClient.getTipusCitesBySubaplicacio(subaplCoa, locale);
	        model.addAttribute("tipusCites", tipusCites);
	        
	        // 3. Inicializar el DTO para el formulario modal
	        if (!model.containsAttribute("horariForm")) {
	            HorariFormDto form = new HorariFormDto();
	            model.addAttribute("horariForm", form);
	        }
	    }  catch (Exception e) {
	        LOG.error("### Error gestio horari {}", e);
	        redirect.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_AGENDES, null, locale));
	        return "redirect:/private/calendari";
	    } finally {
	    	long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.gestioHoraris totalTime={}", totalTime);
		}
        return "private/gestio-horaris";
    }

    @PostMapping("/gestio/horaris/save")
    public String saveUpdateHorari(@ModelAttribute("horariForm") @Valid HorariFormDto form,
                             BindingResult result,
                             Authentication authentication,
                             RedirectAttributes redirect,
                             Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.saveUpdateHorari startTime={}", startTime);
    	try {
        
    		if (result.hasErrors()) {
            redirect.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SAVE_HORARIS, null, locale));
            return "redirect:/private/gestio/agendes"; 
    		}

            // Mapear DTO del formulario a DTO de la API
            HorariDto horariToSave = new HorariDto();
            horariToSave.setCon(form.getCon());
            horariToSave.setDec(form.getDec());
            horariToSave.setDem(form.getDem());
            
            // Crear objetos DTO de Lookup
            TipusCitaDto tipusCita = new TipusCitaDto();
            tipusCita.setCon(form.getTipusCitaCon());
            horariToSave.setTipusCita(tipusCita);
            
            // Asignar Subaplicació
		    String subaplCoa = null;
		    // Obtenir subaplicacio del perfil de l'usuari
		    if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
		        for (GrantedAuthority authority : authentication.getAuthorities()) {
		            subaplCoa = StringUtils.substringAfter(authority.getAuthority(), "_");
		            break;
		         }
		     }
            SubaplicacioDto subapl = new SubaplicacioDto();
            subapl.setCoa(subaplCoa);
            horariToSave.setSubapl(subapl);
            
            // 4. Guardar
            HorariDto savedAgenda = new HorariDto();
            if (form.getCon() != null) {
            	 savedAgenda = citaPreviaPrivateClient.updateHorari(form.getCon(), horariToSave, locale);
            } else {
            	savedAgenda = citaPreviaPrivateClient.saveHorari(horariToSave, locale);
            }

            if (savedAgenda.hasErrors()) {
                // Maneig d'errors del backend
                redirect.addFlashAttribute("error", savedAgenda.getErrors().get(0).getDem());
            } else {
                redirect.addFlashAttribute("success", form.getCon() == null ? bundle.getMessage(Constants.SUCCESS_FRONT_SAVE_HORARIS, null, locale) : bundle.getMessage(Constants.SUCCESS_FRONT_UPDATE_HORARIS, null, locale));
            }
            
    	}  catch (Exception e) {
            LOG.error("### Error save/update horari {}", e);
            redirect.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_HORARIS, null, locale));
        } finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.saveUpdateHorari totalTime={}", totalTime);
		}
        
        
        return "redirect:/private/gestio/horaris";
    }

    @PostMapping("/gestio/horaris/delete")
    public String deleteHorari(@RequestParam("con") Long horCon,
                               RedirectAttributes redirect,
                               Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PrivateController.deleteHorari startTime={}, horCon={}", startTime, horCon);
        try {
            ErrorDto error = citaPreviaPrivateClient.deleteHorari(horCon, locale);
            if (error != null) {
                // Maneig d'errors del backend
                redirect.addFlashAttribute("error", error.getDem());
            } else {
            	redirect.addFlashAttribute("success", bundle.getMessage(Constants.SUCCESS_FRONT_DELETE_HORARIS, null, locale));
            }
            
        } catch (Exception e) {
            LOG.error("### Error delete horari {}", e);
            redirect.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_HORARIS, null, locale));
        } finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.deleteHorari totalTime={}, ageCon={}", totalTime, horCon);
		}
        
        return "redirect:/private/gestio/horaris";
    }
    
    
    public Map<Long, String> getDiesSetmana() {
        // Utilitzem TreeMap per mantenir l'ordre per clau (1, 2, 3...)
        Map<Long, String> diesSetmana = new TreeMap<>();
        
        // Els IDs (1 a 7) han de coincidir amb l'estructura de la teva base de dades/DTO
        diesSetmana.put(1L, "Dilluns");
        diesSetmana.put(2L, "Dimarts");
        diesSetmana.put(3L, "Dimecres");
        diesSetmana.put(4L, "Dijous");
        diesSetmana.put(5L, "Divendres");
        diesSetmana.put(6L, "Dissabte");
        diesSetmana.put(7L, "Diumenge");
        
        return diesSetmana;
    }
    
    @GetMapping("/gestio/horaris/{horCon}/setmanes-tipus")
    public String gestioSetmanesTipus(@PathVariable("horCon") Long horCon, 
                                        Model model, 
                                        RedirectAttributes redirect, 
                                        Locale locale) {
        long startTime=System.currentTimeMillis();
        LOG.info("### Inici PrivateController.gestioSetmanesTipus startTime={}, horCon={}", startTime, horCon);

        try {
            // 1. Carregar dades de l'Horari (necessari per a la capçalera)
            HorariDto horariDto = citaPreviaPrivateClient.getHorari(horCon, locale);
            if (horariDto == null || horariDto.getCon() == null) {
                redirect.addFlashAttribute("error", bundle.getMessage("error.horari.no.trobat", null, locale));
                return "redirect:/private/gestio/horaris";
            }
            
            // 2. Afegir dades al model
            model.addAttribute("horariDto", horariDto);
            model.addAttribute("horariCon", horCon); // Per simplicitat al JS i form
            model.addAttribute("diesSetmana", getDiesSetmana()); // Mètode que retorna Map<Integer, String> dels dies
            
            // 3. Afegir HorariFormDto per al modal d'afegir franja (important per a la validació)
            model.addAttribute("setmanaTipusForm", new SetmanaTipusFormDto()); // <-- Haureu de crear aquest DTO
            
            // Retorna el nom de la nova plantilla
            return "private/gestio-setmanes-tipus";
            
        } catch (Exception e) {
            LOG.error("### Error gestioSetmanesTipus horCon={}", horCon, e);
            redirect.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_HORARIS, null, locale));
            return "redirect:/private/gestio/horaris";
        } finally {
            long totalTime = (System.currentTimeMillis() - startTime);
            LOG.info("### Final PrivateController.gestioSetmanesTipus totalTime={}", totalTime);
        }
    }
    
 // 1. Endpoint per OBTENIR TOTA LA LLISTA (GET)
    @GetMapping("/horaris/{horCon}/setmanes-tipus") // <--- Patró de URL COMPLET
    @ResponseBody
    public List<SetmanaTipusDto> getSetmanesTipus(@PathVariable("horCon") Long horCon, Locale locale) {
        long startTime = System.currentTimeMillis();
        LOG.info("### Inici PrivateController.getSetmanesTipus startTime={}, horCon={}", startTime, horCon);

        try {
            // CRÍTIC: Cridar al client de backend per obtenir la llista
            List<SetmanaTipusDto> llista = citaPreviaPublicClient.getSetmanesTipusByHorari(horCon, locale);
            return llista;

        } catch (Exception e) {
            LOG.error("### Error getSetmanesTipus horCon={}", horCon, e);
            // Retornar una llista buida o gestionar l'excepció segons la política d'errors de l'API
            return Collections.emptyList();
        } finally {
            long totalTime = (System.currentTimeMillis() - startTime);
            LOG.info("### Final PrivateController.getSetmanesTipus totalTime={}", totalTime);
        }
    }

    @PostMapping("/horaris/{horCon}/setmanes-tipus/add")
    @ResponseBody
    public SetmanaTipusDto addSetmanaTipusToHorari(@PathVariable Long horCon,
    											   @RequestBody SetmanaTipusFormDto form,
    										        Locale locale) {
        long startTime = System.currentTimeMillis();
        LOG.info("### Inici PrivateController.addSetmanaTipus startTime={}, horCon={}, form={}", startTime, horCon, form.toString());

        SetmanaTipusDto settipSave = new SetmanaTipusDto();
        
        try {
            // 1. Assignar l'Horari CON al DTO del formulari (obligatori per a la crida al backend)
        	HorariDto horari = new HorariDto();
        	horari.setCon(horCon);
        	settipSave.setHorari(horari);
        	settipSave.setDiasetCon(form.getDiasetCon());
        	settipSave.setHorini(form.getHorini());
        	settipSave.setHorfin(form.getHorfin());
        	
            // 2. Cridar al client de backend per afegir la nova SetmanaTipus
            // El client s'encarregarà de la validació (hores, superposicions, etc.)
            settipSave = citaPreviaPrivateClient.addSetmanaTipusToHorari(settipSave, locale);

            if (settipSave.hasErrors()) {
                // Maneig d'errors del backend
            	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, settipSave.getErrors().get(0).getDem());
            } else {
            	return settipSave;            
            }
        } catch (ResponseStatusException e) {
            throw e; 
        }  catch (Exception e) {
            LOG.error("### Error save setmana tipus {}", e);
            String errorMessage = bundle.getMessage(Constants.ERROR_FRONT_GESTIO_SETMANES_TIPUS, null, locale);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        } finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PrivateController.saveUpdateHorari totalTime={}", totalTime);
		}

    }

 // Mètode Actualitzat
    @PostMapping("/horaris/{horCon}/setmanes-tipus/delete")
    @ResponseBody
    public void deleteSetmanaTipusOfHorari(@PathVariable Long horCon,
                                             @RequestBody SetmanaTipusDeleteFormDto form, 
                                             Locale locale) {
        long startTime = System.currentTimeMillis();
        LOG.info("### Inici PrivateController.deleteSetmanaTipusOfHorari startTime={}, horCon={}, form={}", startTime, horCon, form.toString());

        try {
            // Implementar la crida amb els 3 camps de la clau.
            // Assumim que citaPreviaPrivateClient.deleteSetmanaTipus existeix i accepta aquests paràmetres.
        	SetmanaTipusDto settipDelete = new SetmanaTipusDto();
        	HorariDto horari = new HorariDto();
        	horari.setCon(horCon);
        	settipDelete.setHorari(horari);
        	settipDelete.setDiasetCon(form.getDiasetCon());
        	settipDelete.setHorini(form.getHorini());
        	settipDelete.setHorfin(form.getHorfin());
            ErrorDto resultatDelete = citaPreviaPrivateClient.deleteSetmanaTipusOfHorari(horCon, settipDelete, locale);
            // Si no hi ha excepció, retorna un 200 OK / 204 No Content (amb 'void')
            if (resultatDelete != null && !Utils.isEmpty(resultatDelete.getDem())) {
            	String errorMessage = resultatDelete.getDem(); 
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
            }
        } catch (Exception e) {
            LOG.error("### Error deleteSetmanaTipusOfHorari horCon={}", horCon, e);            
            // Llençar una ResponseStatusException amb el missatge d'error per al client.
            // Aquest missatge serà capturat pel JS.
            String errorMessage = bundle.getMessage("error.eliminar.franja", null, locale);           
            // Si la teva capa de negoci ja retorna un missatge personalitzat, pots capturar-lo i usar-lo aquí.
            // Per defecte, usem un error genèric si no podem obtenir un missatge específic de la lògica de negoci.
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, errorMessage);
        } finally {
            long totalTime = (System.currentTimeMillis() - startTime);
            LOG.info("### Final PrivateController.deleteSetmanaTipusOfHorari totalTime={}, horCon={}, form={}", totalTime, horCon, form.toString());
        }
    }
    
    
    /**
     * Pàgina de gestió de tècnics.
     */
    @GetMapping("/gestio/tecnics")
    public String gestioTecnics(Model model, @ModelAttribute("tecnicForm") TecnicFormDto tecnicForm, Authentication authentication, Locale locale) {
        long startTime = System.currentTimeMillis();
        LOG.info("### Inici PrivateController.gestioTecnics");
        String subaplCoa = null;
        // Obtenir subaplicacio del perfil de l'usuari
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                subaplCoa = StringUtils.substringAfter(authority.getAuthority(), "_");
                break;
            }
        }

        try {
            if (Utils.isEmpty(subaplCoa)) {
                LOG.error("### Error gestioTecnics: No s'ha pogut obtenir la subaplicació (subaplCoa) de l'usuari autenticat.");
                model.addAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TECNICS, null, locale));
                return "private/gestio-tecnics";
            }

            // 2. Carregar llista de Tècnics
            List<TecnicDto> tecnicsList = citaPreviaPrivateClient.getTecnicsBySubaplicacio(subaplCoa, locale);

            // CRITICAL FIX: El nom de l'atribut a la vista HTML és 'tecnics'
            model.addAttribute("tecnics", tecnicsList);

            // 3. Afegir un DTO buit per al formulari modal si no n'hi ha un per errors de validació
            // Ja que l'hem injectat a la signatura (@ModelAttribute("tecnicForm") TecnicDto tecnicForm),
            // ja està disponible amb el nom 'tecnicForm', per això eliminem la comprovació.
            // Si hi ha errors de validació d'una crida POST, Spring l'haurà afegit automàticament.

        } catch (Exception e) {
            LOG.error("### Error gestioTecnics per a subaplCoa={}", subaplCoa, e);
            model.addAttribute("tecnicFormError", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TECNICS, null, locale));
        } finally {
            long totalTime = (System.currentTimeMillis() - startTime);
            LOG.info("### Final PrivateController.gestioTecnics totalTime={}", totalTime);
        }
        return "private/gestio-tecnics";
    }
    
    /**
     * Endpoint per crear o actualitzar un Tècnic.
     */
    @PostMapping("/gestio/tecnics/save")
    public String saveUpdateTecnic(@Valid @ModelAttribute("tecnicForm") TecnicFormDto tecnicForm,
    		Authentication authentication,
            BindingResult result,
            RedirectAttributes redirect,
            Locale locale) {

        long startTime = System.currentTimeMillis();
        LOG.info("### Inici PrivateController.saveTecnic startTime={}, tecnicForm={}", startTime, tecnicForm.toString());

        String subaplCoa = null;
        // Obtenir subaplicacio del perfil de l'usuari
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                subaplCoa = StringUtils.substringAfter(authority.getAuthority(), "_");
                break;
            }
        }
        
        try {
            // 1. Validació del formulari
            if (result.hasErrors()) {
                // Afegir BindingResult i DTO amb les dades introduïdes al FlashMap per a la redirecció
                redirect.addFlashAttribute("org.springframework.validation.BindingResult.tecnicForm", result);
                redirect.addFlashAttribute("tecnicForm", tecnicForm);
                // Redirigir al GET de gestio/tecnics per tornar a carregar la vista amb els errors
                return "redirect:/private/gestio/tecnics";
            }

            if (Utils.isEmpty(subaplCoa)) {
                LOG.error("### Error saveTecnic: No s'ha pogut obtenir la subaplicació (subaplCoa) de l'usuari autenticat.");
                redirect.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TECNICS, null, locale));
                return "redirect:/private/gestio/tecnics";
            }

            // 2. Mapeig DTO per a l'API
            // Només fem servir TecnicDto per enviar dades a l'API.
            TecnicDto tecnicToSave = new TecnicDto();
            // Si coa no és null, és una edició, altrament és una creació
            tecnicToSave.setCoa(tecnicForm.getCoa());
            tecnicToSave.setPass(tecnicForm.getPass());
            tecnicToSave.setNom(tecnicForm.getNom());
            tecnicToSave.setLl1(tecnicForm.getLl1());
            tecnicToSave.setLl2(tecnicForm.getLl2());
            tecnicToSave.setPrf(tecnicForm.getPrf() + "_" + subaplCoa);

	            // 4. Guardar
	            TecnicDto savedTecnic = new TecnicDto();
	            if (!Utils.isEmpty(tecnicForm.getOriginalCoa())) {
	            	savedTecnic = citaPreviaPrivateClient.updateTecnic(tecnicForm.getCoa(), tecnicToSave, locale);
	            } else {
	            	savedTecnic = citaPreviaPrivateClient.saveTecnic(tecnicToSave, locale);
	            }
	
	            if (savedTecnic.hasErrors()) {
	                // Maneig d'errors del backend
	                redirect.addFlashAttribute("error", savedTecnic.getErrors().get(0).getDem());
	                redirect.addFlashAttribute("tecnicForm", tecnicForm);
	            } else {
	                redirect.addFlashAttribute("success", tecnicForm.getCoa() == null ? bundle.getMessage(Constants.SUCCESS_FRONT_SAVE_TECNICS, null, locale) : bundle.getMessage(Constants.SUCCESS_FRONT_UPDATE_TECNICS, null, locale));
	            }
            
        } catch (Exception e) {
            LOG.error("### Error saveTecnic tecnicForm={}", tecnicForm.toString(), e);
            redirect.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TECNICS, null, locale));
            // Torna a afegir el DTO per mantenir les dades al formulari en cas d'error inesperat
            redirect.addFlashAttribute("tecnicForm", tecnicForm);
        } finally {
            long totalTime = (System.currentTimeMillis() - startTime);
            LOG.info("### Final PrivateController.saveTecnic totalTime={}, tecnicForm={}", totalTime, tecnicForm.toString());
        }

        return "redirect:/private/gestio/tecnics";
    }
    
    /**
     * Elimina un tècnic.
     */
    @PostMapping("/gestio/tecnics/delete")
    public String deleteTecnic(@RequestParam("coa") String coa,
                               RedirectAttributes redirect,
                               Authentication authentication,
                               Locale locale) {

        long startTime = System.currentTimeMillis();
        LOG.info("### Inici PrivateController.deleteTecnic con={}", coa);

        String subaplCoa = null;

        // Obtenir la subaplicació del perfil de l'usuari (igual que als altres mètodes)
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                subaplCoa = StringUtils.substringAfter(authority.getAuthority(), "_");
                break;
            }
        }

        try {
            if (Utils.isEmpty(subaplCoa)) {
                LOG.error("### Error deleteTecnic: No s'ha pogut obtenir la subaplicació de l'usuari autenticat.");
                redirect.addFlashAttribute("tecnicFormError", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TECNICS, null, locale));
                return "redirect:/private/gestio/tecnics";
            }

            // Crida al client per esborrar el tècnic
            // Assumint que tens un mètode deleteTecnic al teu CitaPreviaPrivateClient
            ErrorDto error = citaPreviaPrivateClient.deleteTecnic(coa, locale);

            if (error != null && !Utils.isEmpty(error.getDem())) {
                redirect.addFlashAttribute("tecnicFormError", error.getDem());
            } else {
                redirect.addFlashAttribute("successMessage", bundle.getMessage("tecnic.esborrat.ok", null, locale));
            }

        } catch (Exception e) {
            LOG.error("### Error deleteTecnic con={}", coa, e);
            redirect.addFlashAttribute("tecnicFormError", bundle.getMessage("error.esborrar.tecnic", null, locale));
        } finally {
            long totalTime = (System.currentTimeMillis() - startTime);
            LOG.info("### Final PrivateController.deleteTecnic totalTime={}", totalTime);
        }

        return "redirect:/private/gestio/tecnics";
    }
}