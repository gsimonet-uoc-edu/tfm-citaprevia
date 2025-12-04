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
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;


@Controller
@RequestMapping("/public")
public class PublicController {
	
	private static final Logger LOG = LoggerFactory.getLogger(PublicController.class);
	
	@Value("${citaprevia.service.api.host}")
	private String urlCitapreviaApi;

	@Autowired
	private CitaPreviaPublicClient citaPreviaPublicClient;
	
	@Autowired
	private MetacamapService metacamapService;
	
	@Autowired
	private MessageSource bundle;
		
	@GetMapping("/{subaplCoa}")
	public String index(@PathVariable String subaplCoa,
						Model model,
						Locale locale) throws Exception {
		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PublicController.index startTime={}, subaplCoa={}", startTime, subaplCoa);
		try {
			
			SubaplicacioDto subAplicacioSessio = citaPreviaPublicClient.getSubaplicacio(subaplCoa, locale);
			
	        if (subAplicacioSessio == null || Utils.isEmpty(subAplicacioSessio.getCoa())) {
	        	model.addAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_SUBAPLICACIO_NO_TROBADA, null, locale));
	        	return "index";
	        }
	        
			
			// Obtenir tots els tipus de cites de la subaplicació
			SeleccioTipusCitaDto llistaTipusCites =   citaPreviaPublicClient.getAllTipusCites(subaplCoa, locale);
			if(llistaTipusCites == null || Utils.size(llistaTipusCites.getTipusCites()) == 0) {				
				model.addAttribute("alert", bundle.getMessage("no_tipus_disponibles", null, locale));
	        	return "index";
			}
			
			// Afegir al model el elements necessaris
			model.addAttribute("subaplCoa", subAplicacioSessio.getCoa());
			model.addAttribute("subAplicacio", subAplicacioSessio);
			model.addAttribute("llistaTipusCites", llistaTipusCites);
			
	    }  catch (Exception e) {
	        LOG.error("### PublicController.index: ", e);
	        model.addAttribute("error", bundle.getMessage(Constants.MSG_ERR_GET_CALENDARI, null, locale));
        	return "index";
	    } finally {
	    	long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PublicController.index totalTime={}, subaplCoa={}", totalTime, subaplCoa);
		}
		
		return "index";
	}
	
	@GetMapping("/{subaplCoa}/seleccio")
    public String seleccioTipusCita(@PathVariable String subaplCoa,
		                           @RequestParam Long tipcitCon,
		                           Model model,
		                           Locale locale) throws Exception {

		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PublicController.seleccioTipusCita startTime={}, subaplCoa={}, tipcitCon={}", startTime, subaplCoa, tipcitCon);
		try {

	        TipusCitaDto tipusCita = citaPreviaPublicClient.getTipusCita(tipcitCon, locale);
	
	        if (tipusCita == null || !subaplCoa.equals(tipusCita.getSubapl().getCoa()) || Utils.isEmpty(tipusCita.getCon())) {       	
	        	model.addAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_TIPUS_CITES, null, locale));
	        	return "index";
	        }
	
	        List<AgendaDto> agendes = citaPreviaPublicClient.getAgendesObertesBySubaplicacioAndTipusCita(subaplCoa, tipcitCon, locale);
	        
	        if (agendes == null || agendes.isEmpty()) {
	        	model.addAttribute("alert", bundle.getMessage("no_agendes_obertes", null, locale));
	        	model.addAttribute("noAgendas", true);
	            return "index";
	        }
	
	        // Generar eventos
	        List<Map<String, Object>> events = generarEvents(agendes, locale);
	        model.addAttribute("frangesHoraries", events);
	        model.addAttribute("tipusCita", tipusCita);
	        model.addAttribute("dataInici", LocalDate.now());
	        model.addAttribute("dataFi", LocalDate.now().plusDays(30));
	        
			SubaplicacioDto subAplicacioSessio = citaPreviaPublicClient.getSubaplicacio(subaplCoa, locale);
			model.addAttribute("subaplCoa", subAplicacioSessio.getCoa());
			model.addAttribute("subAplicacio", subAplicacioSessio);
			
	        Map<LocalDate, List<Map<String, Object>>> grouped = events.stream()
	        	    .collect(Collectors.groupingBy(
	        	        e -> ((LocalDateTime) e.get("start")).toLocalDate(),
	        	        TreeMap::new,
	        	        Collectors.toList()
	        	    ));
	
	        model.addAttribute("frangesHorariesGrouped", grouped);	
		    model.addAttribute("frangesHoraries", events);
		    
		    List<CampConfigDto> campos = metacamapService.getCampos(subaplCoa, locale);
		    model.addAttribute("camposCita", campos);
		    
	    }  catch (Exception e) {
	        LOG.error("###  PublicController.seleccioTipusCita: ", e);
	        model.addAttribute("error", bundle.getMessage(Constants.MSG_ERR_GET_CALENDARI, null, locale));
        	return "index";
	    } finally {
	    	long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Inici PublicController.seleccioTipusCita totalTime={}, subaplCoa={}, tipcitCon={}", totalTime, subaplCoa, tipcitCon);

		}     

        return "calendari";
    }
	
	private List<Map<String, Object>> generarEvents(List<AgendaDto> agendes, Locale locale) {
	    List<Map<String, Object>> events = new ArrayList<>();
	    
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PublicController.generarEvents startTime={}, agendesSize={}", startTime, agendes != null ? agendes.size() : 0);				
    	try {

    		// Recorrer cada agenda del tipus de cita
    		
		    for (AgendaDto agenda : agendes) {
		        LocalDate dataInici = agenda.getDatini();
		        LocalDate dataFi = agenda.getDatfin();
	
		        for (LocalDate data = dataInici; !data.isAfter(dataFi); data = data.plusDays(1)) {
		            int diaSetmana = data.getDayOfWeek().getValue();
	
		            // Recuperar les franges horàries del tipus d'horari seleccionat
		            List<SetmanaTipusDto> franges = citaPreviaPublicClient.getSetmanesTipusByHorari(agenda.getHorari().getCon(), locale);
		            
		            // Recorrer cada franja horàrira
		            for (SetmanaTipusDto franja : franges) {
		                if (franja.getDiasetCon() == diaSetmana) {
		                	
		                    LocalDateTime inici = LocalDateTime.of(data, franja.getHorini());
		                    LocalDateTime fi = LocalDateTime.of(data, franja.getHorfin());
		                    inici = inici.plusSeconds(1);
		                    fi = fi.plusSeconds(1);
		                    
		                    // Verificar si la cita esta ocupada
		                    boolean ocupada = false;		                    		
		                    CitaDto existeixCita = citaPreviaPublicClient.existeixCitaAgenda(agenda.getCon(), inici.minusSeconds(1), fi.minusSeconds(1), agenda.getHorari().getTipusCita().getCon(), locale);
	
		                    if (existeixCita != null && !Utils.isEmpty(existeixCita.getCon())) {
		                    	ocupada = true;
		                    }
		                    
		                    // Configurar Full Calendar
		                    Map<String, Object> event = new HashMap<>();
		                    event.put("title", franja.getHorini() + " - " + franja.getHorfin());
		                    event.put("start", inici);
		                    event.put("end", fi);
		                    event.put("classNames", ocupada ? "hora-ocupada" : "hora-lliure");
		                    event.put("lliure", !ocupada);
		                    event.put("tecnicNom", agenda.getTecnic().getNom());
		                    event.put("tecnicLl1", agenda.getTecnic().getLl1());
		                    event.put("centreNom", agenda.getCentre().getNom());
		                    event.put("agendaCon", agenda.getCon());
		                    events.add(event);
		                }
		            }
		        }
		    }
		    
	    }  catch (Exception e) {
	        LOG.error("### Error PublicController.generarEvents: ", e);
	    } finally {
	    	long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PublicController.generarEvents totalTime={}, agendesSize={}", totalTime, agendes != null ? agendes.size() : 0);
		}  

	    return events;
	}
	
	@PostMapping("/{subaplCoa}/reserva")
	public String reservaCitaPublica(
	        @PathVariable String subaplCoa,
	        @ModelAttribute CitaFormDto form,
	        BindingResult result,
	        RedirectAttributes redirectAttributes,
	        Locale locale) throws Exception {

		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PublicController.reservaCitaPublica startTime={}, citaForm={}", startTime, form.toString());
		
		CitaDto citaSaved = new CitaDto();
		
    	try {
    		
		    if (result.hasErrors()) {
		    	redirectAttributes.addFlashAttribute("error", Constants.ERROR_BINDINGS_FORM);
		        return "redirect:/public/" + subaplCoa + "/seleccio?tipcitCon=" + form.getTipcitCon();
		    }
	
		    // Crear la cita
		    CitaDto cita = new CitaDto();
		    cita.setDathorini(form.getDataHoraInici().minusSeconds(1));
		    cita.setDathorfin(form.getDataHoraFin().minusSeconds(1));
		    // Camps estàtics
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
	
	
		    cita.setAgenda(citaPreviaPublicClient.getAgenda(form.getAgendaCon(), locale));
		    cita.setTipusCita(citaPreviaPublicClient.getTipusCita(form.getTipcitCon(), locale));
	
		    // Guarda la cita a BBDD
		    citaSaved = citaPreviaPublicClient.saveCita(cita, locale);
			    
		    if (citaSaved.hasErrors()) {
		    	// Error de l'api
		    	redirectAttributes.addFlashAttribute("error", citaSaved.getErrors().get(0).getDem());
		        return "redirect:/public/" + subaplCoa + "/seleccio?tipcitCon=" + form.getTipcitCon();

		    }
		    		    
	    }  catch (Exception e) {
	        LOG.error("### Error PublicController.reservaCitaPublica: ", e);
        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.MSG_ERR_GET_CALENDARI, null, locale));
        	return "redirect:/public/" + subaplCoa + "/seleccio?tipcitCon=" + form.getTipcitCon();
	    } finally {
	    	long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PublicController.reservaCitaPublica totalTime={}, citaSaved={}", totalTime, citaSaved.toString());
		}
		    return "redirect:/public/" + subaplCoa + "/confirmacio?con=" + citaSaved.getCon() + "&tipcitCon=" + form.getTipcitCon();
	}
	
	@GetMapping("/{subaplCoa}/confirmacio")
	public String confirmacio(@PathVariable String subaplCoa,
	                          @RequestParam Long con,
	                          @RequestParam Long tipcitCon,
	                          Model model,
	                          RedirectAttributes redirectAttributes,
	                          Locale locale) throws Exception {

		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PublicController.confirmacio startTime={}, subaplCoa={}, citCon={}, tipcitCon={}", startTime, subaplCoa, con, tipcitCon);
		
		CitaDto cita  = new CitaDto();
		
    	try {
    		
		    cita = citaPreviaPublicClient.getCita(con, locale);
		    if (cita == null || Utils.isEmpty(cita.getCon())) {
	        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_CITES, null, locale));
	        	return "redirect:/public/" + subaplCoa + "/seleccio?tipcitCon=" + tipcitCon;
		    }
	
		    // Afegir al model
		    model.addAttribute("cita", cita);
		    model.addAttribute("subaplCoa", subaplCoa);
		    model.addAttribute("tipusCita", citaPreviaPublicClient.getTipusCita(cita.getTipusCita().getCon(), locale));
	    
	    }  catch (Exception e) {
	        LOG.error("### Error PublicController.confirmacio: ", e);
        	redirectAttributes.addFlashAttribute("error", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_CITES, null, locale));
        	return "redirect:/public/" + subaplCoa + "/seleccio?tipcitCon=" + tipcitCon;
	    } finally {
	    	long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PublicController.confirmacio totalTime={}, subaplCoa={}, citCon={}, tipcitCon={}", totalTime, subaplCoa, con, tipcitCon);
		}

	    return "confirmacio";
	}
	
	
	@PostMapping("/{subaplCoa}/cancelar")
	public String cancelarCita(@PathVariable String subaplCoa,
	                           @RequestParam Long con,
	                           @RequestParam String numdoc,
	                           RedirectAttributes redirectAttributes,
	                           Locale locale) {

		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PublicController.cancelarCita startTime={}, subaplCoa={}, citCon={}, numdoc={}", startTime, subaplCoa, con, numdoc);
		
		CitaDto cita  = new CitaDto();
		
    	try {
    		
	        cita = citaPreviaPublicClient.getCita(con, locale);
	        // Comprovar que la cita pertany a la subaplicació i persona que vol cancelarla
	        if (cita == null || Utils.isEmpty(cita.getCon()) || !StringUtils.upperCase(cita.getNumdoc()).equals(StringUtils.upperCase(numdoc)) || !subaplCoa.equals(cita.getAgenda().getHorari().getSubapl().getCoa())) {
	            redirectAttributes.addFlashAttribute("error_cancel", bundle.getMessage("cita_inexistent", null, locale));
	            
	            return "redirect:/public/" + subaplCoa;
	        }

	        ErrorDto eliminada = citaPreviaPublicClient.deleteCitaPersona(con, numdoc, locale);
	        
	        if (eliminada == null) {
	            redirectAttributes.addFlashAttribute("success_cancel", bundle.getMessage("cita_cancelada_correctament", null, locale));
	        } else {
	            redirectAttributes.addFlashAttribute("error_cancel", bundle.getMessage("cita_inexistent", null, locale));
	        }
	        
	    }  catch (Exception e) {
	        LOG.error("### Error PublicController.cancelarCita: ", e);
	        redirectAttributes.addFlashAttribute("error_cancel", bundle.getMessage(Constants.ERROR_FRONT_GESTIO_CITES, null, locale));
	    } finally {
	    	long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PublicController.ca totalTime={}, subaplCoa={}, citCon={}, numdoc={}", totalTime, subaplCoa, con, numdoc);
		}

    	return "redirect:/public/" + subaplCoa;
    	
	}
	
	
	/*
	@PostMapping("/{subaplCoa}/cancelar")
	public String cancelarCita(@PathVariable String subaplCoa,
	                           @RequestParam Long con,
	                           @RequestParam String numdoc,
	                           RedirectAttributes redirectAttributes,
	                           Locale locale) {

		long startTime=System.currentTimeMillis();
		LOG.info("### Inici PublicController.cancelarCita startTime={}, subaplCoa={}, citCon={}, numdoc={}", startTime, subaplCoa, con, numdoc);
		
		CitaDto cita  = new CitaDto();
		
    	try {
    		
	        cita = citaPreviaPublicClient.getCita(con, locale);
	        // Comprovar que la cita pertany a la subaplicació i persona que vol cancelarla
	        if (cita == null || Utils.isEmpty(cita.getCon()) || !StringUtils.upperCase(cita.getNumdoc()).equals(StringUtils.upperCase(numdoc)) || !subaplCoa.equals(cita.getAgenda().getHorari().getSubapl().getCoa())) {
	            redirectAttributes.addFlashAttribute("error_cancel", true);
	            return "redirect:/public/" + subaplCoa + (redirectAttributes.getFlashAttributes().containsKey("success_cancel") ? "?success_cancel" : "?error_cancel");
	        }

	        ErrorDto eliminada = citaPreviaPublicClient.deleteCitaPersona(con, numdoc, locale);
	        
	        if (eliminada == null) {
	            redirectAttributes.addFlashAttribute("success_cancel", true);
	        } else {
	            redirectAttributes.addFlashAttribute("error_cancel", true);
	        }
   	
	    }  catch (Exception e) {
	        LOG.error("### Error PublicController.cancelarCita: ", e);
	        redirectAttributes.addFlashAttribute("error_cancel", true);
	    } finally {
	    	long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final PublicController.ca totalTime={}, subaplCoa={}, citCon={}, numdoc={}", totalTime, subaplCoa, con, numdoc);
		}

	    return "redirect:/public/" + subaplCoa + (redirectAttributes.getFlashAttributes().containsKey("success_cancel") ? "?success_cancel" : "?error_cancel");
	}*/
}
