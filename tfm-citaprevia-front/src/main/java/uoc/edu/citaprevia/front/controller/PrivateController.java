package uoc.edu.citaprevia.front.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.qos.logback.classic.pattern.Util;
import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.dto.SetmanaTipusDto;
import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.dto.TipusCitaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.front.dto.CampConfigDto;
import uoc.edu.citaprevia.front.dto.CitaFormDto;
import uoc.edu.citaprevia.front.service.CitaPreviaPrivateClient;
import uoc.edu.citaprevia.front.service.CitaPreviaPublicClient;
import uoc.edu.citaprevia.front.service.MetacamapService;
import uoc.edu.citaprevia.model.Perfil;
import uoc.edu.citaprevia.util.Utils;

@Controller
@RequestMapping("/private")
public class PrivateController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PrivateController.class);
	
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
		String coa = authentication.getName();
		TecnicDto tecnic = citaPreviaPrivateClient.getTecnic(coa, locale);

		if (tecnic == null) {
			return "redirect:/private/login";
		}

        List<AgendaDto> agendes;
        String subaplCoa = StringUtils.substringAfter(tecnic.getPrf(), "_"); // Extreim el codi de la subaplicació a partir sufixe del perfil.
        String prefixePerfil = StringUtils.substringBefore(tecnic.getPrf(), "_");
        
        if (Perfil.ADMINISTRADOR.getValor().equals(prefixePerfil)) {
            agendes = citaPreviaPrivateClient.getAgendasBySubaplicacio(subaplCoa, locale);
        } else {
            agendes = citaPreviaPrivateClient.getAgendasByTecnic(tecnic.getCoa(), locale);    
        }

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
	    cita.setLit1(form.getLit1()); cita.setLit2(form.getLit2());
	    cita.setLit3(form.getLit3()); cita.setLit4(form.getLit4());
	    cita.setLit5(form.getLit5()); cita.setLit6(form.getLit6());
	    cita.setLit7(form.getLit7()); cita.setLit8(form.getLit8());
	    cita.setLit9(form.getLit9()); cita.setLit10(form.getLit10());

	    CitaDto updated = null; //citaPreviaPrivateClient.updateCita(cita, locale); TODO
	    if (updated == null) {
	        redirect.addFlashAttribute("error", "Error al actualitzar");
	    } else {
	        return "redirect:/private/cita/confirmacio?con=" + updated.getCon() + "&accion=actualitzada";
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
}