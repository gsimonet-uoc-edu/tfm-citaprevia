package uoc.edu.citaprevia.front.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.SeleccioTipusCitaDto;
import uoc.edu.citaprevia.dto.SetmanaTipusDto;
import uoc.edu.citaprevia.dto.SubaplicacioDto;
import uoc.edu.citaprevia.dto.TipusCitaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.front.service.CitaPreviaPublicClient;
import uoc.edu.citaprevia.front.service.SubaplicacioClient;
import uoc.edu.citaprevia.util.Utils;


@Controller
@RequestMapping("/public")
public class PublicController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PublicController.class);
	
	@Value("${citaprevia.service.api.host}")
	private String urlCitapreviaApi;

	@Autowired
	private CitaPreviaPublicClient citaPreviaPublicClient;
	
	@Autowired
	private SubaplicacioClient subaplicacioClient;
	
	@Autowired
	private MessageSource bundle;
	
	/** 
	 * Seteo atributos comunes en Model: subaplCoa, subAplicacio, etc 
	 * @throws ImiException 
	*/
	private void setInModelCommonAttributes(HttpServletRequest request, Model model, Locale locale, String subaplCoa)
	throws Exception {
		
		
		//final SubaplicacioDto subAplicacioSessio = SubaplicacioUtil.getSubaplicacioAndSetSessionPublic(
		//	request, bundle, application, subaplCoa, locale);
		SubaplicacioDto subAplicacioSessio = subaplicacioClient.getSubaplicacio(subaplCoa, locale);
		model.addAttribute("subaplCoa", subAplicacioSessio.getCoa());
		model.addAttribute("subAplicacio", subAplicacioSessio);
	}
	
	@GetMapping("/{subaplCoa}") 
	public String welcome(HttpServletRequest request, Model model, @PathVariable String subaplCoa,
	Locale locale) throws Exception {
		setInModelCommonAttributes(request, model, locale, subaplCoa);
		SeleccioTipusCitaDto llistaTipusCites =   citaPreviaPublicClient.getAllTipusCites(subaplCoa, locale);
		if(llistaTipusCites == null || Utils.size(llistaTipusCites.getTipusCites()) == 0) {
			List<ErrorDto> errors = new ArrayList<>();
			errors.add(new ErrorDto(9999L, "E", bundle.getMessage("no_tipus_disponibles", null, locale)));
			model.addAttribute("errors", errors);
		}
		model.addAttribute("llistaTipusCites", llistaTipusCites);
		return "index";
	}

	private void addError(Model model, Long codi, String msgKey, Locale locale) {
	    // Obtiene la lista de errores existente (o crea una nueva)
	    List<ErrorDto> errors = (List<ErrorDto>) model.getAttribute("errors");
	    if (errors == null) {
	        errors = new ArrayList<>();
	    }

	    // Añade el nuevo error
	    errors.add(new ErrorDto(codi, "E", bundle.getMessage(msgKey, null, locale)));

	    // Vuelve a guardarlo en el modelo
	    model.addAttribute("errors", errors);
	}
	
	@PostMapping("/{subaplCoa}/seleccio")
    public String seleccio(@PathVariable String subaplCoa,
                           @RequestParam Long tipcitCon,
                           Model model, Locale locale, HttpServletRequest request) throws Exception {

        setInModelCommonAttributes(request, model, locale, subaplCoa);

        TipusCitaDto tipusCita = citaPreviaPublicClient.getTipusCita(tipcitCon, locale);
        if (tipusCita == null || !subaplCoa.equals(tipusCita.getSubapl().getCoa())) {
            addError(model, 9998L, "tipus_cita_no_valid", locale);
            return welcome(request, model, subaplCoa, locale);
        }

        List<AgendaDto> agendes = citaPreviaPublicClient.getAgendasBySubaplicacioAndTipusCita(subaplCoa, tipcitCon, locale);
        if (agendes.isEmpty()) {
            addError(model, 9997L, "no_agenda_disponible", locale);
            model.addAttribute("llistaTipusCites", new SeleccioTipusCitaDto(List.of(tipusCita)));
            return "index";
        }

        // Generar eventos
        List<Map<String, Object>> events = generarEvents(agendes, citaPreviaPublicClient, locale);
        model.addAttribute("frangesHoraries", events);
        model.addAttribute("tipusCita", tipusCita);
        model.addAttribute("dataInici", LocalDate.now());
        model.addAttribute("dataFi", LocalDate.now().plusDays(30));
        

	     // AGRUPAR POR DÍA
	     Map<LocalDate, List<Map<String, Object>>> grouped = events.stream()
	         .collect(Collectors.groupingBy(
	             e -> ((LocalDateTime) e.get("start")).toLocalDate()
	         ));
	
	     model.addAttribute("frangesHoraries", events); // para compatibilidad
	     model.addAttribute("frangesHorariesGrouped", grouped);
        

        return "calendario";
    }
	
	private List<Map<String, Object>> generarEvents(List<AgendaDto> agendes, CitaPreviaPublicClient client, Locale locale) {
	    List<Map<String, Object>> events = new ArrayList<>();

	    for (AgendaDto agenda : agendes) {
	        LocalDate dataInici = agenda.getDatini();
	        LocalDate dataFi = agenda.getDatfin();

	        // RECORRER CADA DÍA del rango (inclusive)
	        for (LocalDate data = dataInici; !data.isAfter(dataFi); data = data.plusDays(1)) {
	            int diaSetmana = data.getDayOfWeek().getValue(); // 1=lunes, 2=martes, ..., 7=domingo

	            // Obtener franjas horarias del horario asociado
	            List<SetmanaTipusDto> franges = citaPreviaPublicClient.getSetmanesTipusByHorari(agenda.getHorari().getCon(), locale);

	            for (SetmanaTipusDto franja : franges) {
	                // Solo si coincide el día de la semana
	                if (franja.getDiasetCon() == diaSetmana) {

	                    // COMBINAR fecha del día + hora de la franja
	                    LocalDateTime inici = LocalDateTime.of(data, franja.getHorini());
	                    LocalDateTime fi = LocalDateTime.of(data, franja.getHorfin());

	                    // Verificar si ya hay cita (solapamiento)
	                    boolean ocupada = false;//client.existeixCita(inici, fi, agenda.getCon());

	                    // Crear evento FullCalendar
	                    Map<String, Object> event = new HashMap<>();
	                    event.put("title", franja.getHorini() + " - " + franja.getHorfin());
	                    event.put("start", inici); // ISO 8601
	                    event.put("classNames", ocupada ? "hora-ocupada" : "hora-lliure");
	                    event.put("lliure", !ocupada);
	                    event.put("tecnic", agenda.getTecnic().getNom());
	                    event.put("agendaCon", agenda.getCon());
	                    events.add(event);
	                }
	            }
	        }
	    }

	    return events;
	}
}
