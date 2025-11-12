package uoc.edu.citaprevia.front.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.SetmanaTipusDto;
import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.front.service.CitaPreviaPrivateClient;
import uoc.edu.citaprevia.front.service.CitaPreviaPublicClient;
import uoc.edu.citaprevia.model.Perfil;

@Controller
@RequestMapping("/private")
public class PrivateController {


    @Autowired
    private CitaPreviaPublicClient citaPreviaPublicClient; // Reutiliza si es necesario
    
    @Autowired
    private CitaPreviaPrivateClient citaPreviaPrivateClient;
    
    @ModelAttribute("private")
    public boolean isPrivate() {
        return true; // Para diferenciar en el template si es necesario
    }

    @GetMapping("/login")
    public String login() {
        return "private/login";
    }

    @GetMapping("/calendari")
    public String calendari(Authentication auth, Model model, Locale locale) {
    	String coa = auth.getName(); 
        TecnicDto tecnic = citaPreviaPrivateClient.getTecnic(coa, locale);
        if (tecnic == null) {
            return "redirect:/private/login";
        }

        List<AgendaDto> agendes;
        if (tecnic.getPrf() == Perfil.ADMINISTRADOR) {
            agendes = citaPreviaPrivateClient.getAgendasBySubaplicacio(tecnic.getSubaplicacio().getCoa()); // Asumiendo Tecnic tiene subaplicacio
        } else {
            agendes = agendaService.findByTecnicCoa(coa);
        }

        model.addAttribute("subaplCoa", tecnic.getSubaplicacio().getCoa());
        model.addAttribute("tipusCita", null); // Adaptar si es necesario, o dejar para el template
        model.addAttribute("frangesHorariesGrouped", generarFrangesHorariesGrouped(agendes)); // Reutiliza la lógica

        model.addAttribute("dataInici", LocalDate.now());
        model.addAttribute("dataFi", LocalDate.now().plusDays(30));

        return "calendario"; // USA EL MISMO TEMPLATE
    }

    // MÉTODO GENERAR FRANGES (COPIADO DE PUBLIC CONTROLLER)
    private Map<LocalDate, List<Map<String, Object>>> generarFrangesHorariesGrouped(List<AgendaDto> agendes) {
        Map<LocalDate, List<Map<String, Object>>> grouped = new TreeMap<>();

        for (AgendaDto agenda : agendes) {
            LocalDate dataInici = agenda.getDatini();
            LocalDate dataFi = agenda.getDatfin();

            for (LocalDate data = dataInici; !data.isAfter(dataFi); data = data.plusDays(1)) {
                int diaSetmana = data.getDayOfWeek().getValue();

                List<SetmanaTipusDto> franges = citaPreviaPublicClient.getSetmanesTipusByHorari(agenda.getHorari().getCon(), null);

                List<Map<String, Object>> dayEvents = new ArrayList<>();

                for (SetmanaTipusDto franja : franges) {
                    if (franja.getDiasetCon() == diaSetmana) {
                        LocalDateTime inici = LocalDateTime.of(data, franja.getHorini());
                        LocalDateTime fi = LocalDateTime.of(data, franja.getHorfin());

                        boolean ocupada = citaPreviaPublicClient.existeixCita(inici, fi, agenda.getCon());

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

    // ... otros métodos ...
}