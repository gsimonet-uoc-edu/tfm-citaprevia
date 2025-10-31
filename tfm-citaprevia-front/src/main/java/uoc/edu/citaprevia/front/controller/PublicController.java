package uoc.edu.citaprevia.front.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

import uoc.edu.citaprevia.dto.CalendariDto;
import uoc.edu.citaprevia.dto.SeleccioTipusCitaDto;
import uoc.edu.citaprevia.dto.SubaplicacioDto;
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

	@GetMapping("/{subaplCoa}/calendari")
    public String mostrarCalendari(
            @PathVariable String subaplCoa,
            @RequestParam Long tipcitCon,
            Model model,
            Locale locale) {

        model.addAttribute("subaplCoa", subaplCoa);
        model.addAttribute("tipcitCon", tipcitCon);

        // Cargar calendario desde el backend
        CalendariDto calendari = citaPreviaPublicClient.getCalendariCites(subaplCoa, tipcitCon, locale);
        model.addAttribute("calendari", calendari);

        return "calendari"; // → calendari.html
    }
    
    @PostMapping("/{subaplCoa}/seleccio")
    public String procesarSeleccio(
            @PathVariable String subaplCoa,
            @RequestParam("tipcitCon") Long tipcitCon) {

        if (tipcitCon == null) {
            return "redirect:/public/" + subaplCoa; // vuelve si no hay selección
        }

        // REDIRECCIÓN AL CALENDARIO
        return "redirect:/public/" + subaplCoa + "/calendari?tipcitCon=" + tipcitCon;
    }


	@PostMapping("/{subaplCoa}/lang")
	public String changeLanguage(@PathVariable String subaplCoa,
	                            @RequestParam String lang,
	                            HttpServletRequest request) {

	    Locale locale = "ca".equals(lang) ? new Locale("ca") : new Locale("es");
	    
	    // Usa la constante oficial de Spring
	    request.getSession().setAttribute(
	        org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,
	        locale
	    );

	    return "redirect:/public/" + subaplCoa;
	}

}
