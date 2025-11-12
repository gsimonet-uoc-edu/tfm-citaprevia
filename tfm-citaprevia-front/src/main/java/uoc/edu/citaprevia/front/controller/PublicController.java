package uoc.edu.citaprevia.front.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
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
import uoc.edu.citaprevia.dto.SeleccioTipusCitaDto;
import uoc.edu.citaprevia.dto.SetmanaTipusDto;
import uoc.edu.citaprevia.dto.SubaplicacioDto;
import uoc.edu.citaprevia.dto.TipusCitaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.front.dto.CampConfigDto;
import uoc.edu.citaprevia.front.dto.CitaFormDto;
import uoc.edu.citaprevia.front.service.CitaPreviaPublicClient;
import uoc.edu.citaprevia.front.service.MetacamapService;
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
	private MetacamapService metacamapService;
	
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
        if (tipusCita == null || !subaplCoa.equals(tipusCita.getSubapl().getCoa()) || Utils.isEmpty(tipusCita.getCon())) {
            addError(model, 9998L, "tipus_cita_no_valid", locale);
            return welcome(request, model, subaplCoa, locale);
        }

        List<AgendaDto> agendes = citaPreviaPublicClient.getAgendasBySubaplicacioAndTipusCita(subaplCoa, tipcitCon, locale);
        if (agendes.isEmpty()) {
        	model.addAttribute("noAgendas", true);
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
        	        e -> ((LocalDateTime) e.get("start")).toLocalDate(),
        	        TreeMap::new, // ← ASCENDENTE (por defecto)
        	        Collectors.toList()
        	    ));

        model.addAttribute("frangesHorariesGrouped", grouped);	
	    model.addAttribute("frangesHoraries", events); // para compatibilidad
	    
	    List<CampConfigDto> campos = metacamapService.getCampos(subaplCoa, locale);
	    model.addAttribute("camposCita", campos);
        

        return "calendari";
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
	                    inici = inici.plusSeconds(1);
	                    fi = fi.plusSeconds(1);
	                    
	                    // Verificar si ya hay cita (solapamiento)
	                    boolean ocupada = false;
	                    		
	                    CitaDto existeixCita = citaPreviaPublicClient.existeixCitaAgenda(agenda.getCon(), inici.minusSeconds(1), fi.minusSeconds(1), agenda.getHorari().getTipusCita().getCon(), locale);

	                    if (existeixCita != null && !Utils.isEmpty(existeixCita.getCon())) {
	                    	ocupada = true;
	                    }
	                    
	                    // Crear evento FullCalendar
	                    Map<String, Object> event = new HashMap<>();
	                    event.put("title", franja.getHorini() + " - " + franja.getHorfin());
	                    event.put("start", inici); // ISO 8601
	                    event.put("end", fi);
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
	
	@PostMapping("/{subaplCoa}/reserva")
	public String reserva(
	        @PathVariable String subaplCoa,
	        @ModelAttribute CitaFormDto form,
	        BindingResult result,
	        Model model,
	        RedirectAttributes redirect,
	        Locale locale) throws Exception {

	    // === VALIDACIÓN ===
	    if (result.hasErrors()) {
	        model.addAttribute("camposCita", metacamapService.getCampos(subaplCoa, locale));
	        model.addAttribute("tipusCita", citaPreviaPublicClient.getTipusCita(form.getTipcitCon(), locale));
	        model.addAttribute("subaplCoa", subaplCoa);
	        // Necesitas volver a generar frangesHorariesGrouped si quieres mostrar el calendario
	        // O redirigir con errores
	        redirect.addFlashAttribute("errors", result.getAllErrors());
	        return "redirect:/public/" + subaplCoa + "/seleccio";
	    }

	    // === CREAR CITA ===
	    CitaDto cita = new CitaDto();
	    cita.setDathorini(form.getDataHoraInici().minusSeconds(1));
	    cita.setDathorfin(form.getDataHoraFin().minusSeconds(1)); // o duración desde TipusCita

	    // === CAMPOS ESTÁTICOS ===
	    cita.setNom(form.getNom());
	    cita.setLlis(form.getLlis());
	    cita.setNumdoc(form.getNumdoc());
	    cita.setEma(form.getEma());
	    cita.setTel(Long.valueOf(form.getTel()));    
	    cita.setNomcar(form.getNomcar());	       
	    cita.setObs(form.getObs());

	    // Camps dinàmics
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

	    // === RELACIONES ===
	    cita.setAgenda(citaPreviaPublicClient.getAgenda(form.getAgendaCon(), locale));
	    cita.setTipusCita(citaPreviaPublicClient.getTipusCita(form.getTipcitCon(), locale));

	    // === GUARDAR EN BBDD ===
	    CitaDto insertCita = citaPreviaPublicClient.saveCita(cita, locale);

	    // === REDIRECCIÓN CON ÉXITO ===
	    redirect.addFlashAttribute("success", "Cita reservada correctamente");
	    return "redirect:/public/" + subaplCoa + "/confirmacio?con=" + insertCita.getCon();
	}
	
	@GetMapping("/{subaplCoa}/confirmacio")
	public String confirmacio(@PathVariable String subaplCoa,
	                          @RequestParam Long con,
	                          Model model, Locale locale) throws Exception {

	    CitaDto cita = citaPreviaPublicClient.getCita(con, locale);
	    if (cita == null || Utils.isEmpty(cita.getCon())) {
	        addError(model, 9999L, "cita_no_trobada", locale);
	        return "redirect:/public/" + subaplCoa;
	    }

	    model.addAttribute("cita", cita);
	    model.addAttribute("subaplCoa", subaplCoa);
	    model.addAttribute("tipusCita", citaPreviaPublicClient.getTipusCita(cita.getTipusCita().getCon(), locale));

	    return "confirmacio";
	}
	
	@PostMapping("/{subaplCoa}/cancelar")
	public String cancelarCita(@PathVariable String subaplCoa,
	                           @RequestParam Long con,
	                           @RequestParam String numdoc,
	                           RedirectAttributes redirect,
	                           Locale locale) {

	    try {
	        CitaDto cita = citaPreviaPublicClient.getCita(con, locale);
	        // Comprovar que la cita pertany a la subaplicació i persona que vol cancelarla
	        if (cita == null || !StringUtils.upperCase(cita.getNumdoc()).equals(StringUtils.upperCase(numdoc)) || !subaplCoa.equals(cita.getAgenda().getHorari().getSubapl().getCoa())) {
	            redirect.addFlashAttribute("error", true);
	            return "redirect:/public/" + subaplCoa + "?error";
	        }

	        ErrorDto eliminada = citaPreviaPublicClient.deleteCitaPersona(con, numdoc, locale);
	        
	        if (eliminada == null) {
	            redirect.addFlashAttribute("success", true);
	        } else {
	            redirect.addFlashAttribute("error", true);
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	        redirect.addFlashAttribute("error", true);
	    }

	    return "redirect:/public/" + subaplCoa + (redirect.getFlashAttributes().containsKey("success") ? "?success" : "?error");
	}
}
