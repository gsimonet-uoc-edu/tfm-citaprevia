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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.dto.SetmanaTipusDto;
import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.dto.TipusCitaDto;
import uoc.edu.citaprevia.front.service.CitaPreviaPrivateClient;
import uoc.edu.citaprevia.front.service.CitaPreviaPublicClient;
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

		return "/private/calendari-private";
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
	                    event.put("classNames", ocupada ? "hora-ocupada" : "hora-lliure");
	                    event.put("lliure", !ocupada);
	                    event.put("tecnic", agenda.getTecnic().getNom());
	                    event.put("agendaCon", agenda.getCon());
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