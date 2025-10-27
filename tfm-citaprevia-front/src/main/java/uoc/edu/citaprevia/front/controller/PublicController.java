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
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@Value("${imi.citaprevia.api.host}")
	private String urlCitapreviaApi;
	@Value("${imi.citaprevia.apl}")
	private String application;
	@Value("${hCaptcha.sitekey}")
	
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
	
	private String msg(String key, Locale locale) {
		try {
			return bundle.getMessage(key, null, locale);
		} catch(NoSuchMessageException e) {
			return "{" + key + "}";
		}
	}
	private String msg(String key, Locale locale, String defMsg) {
		try {
			return bundle.getMessage(key, null, locale);
		} catch(NoSuchMessageException e) {
			return defMsg;
		}
	}
	
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
		SeleccioTipusCitaDto llistaTipusCites = citaPreviaPublicClient.getAllTipusCites(application, subaplCoa, locale);
		if(llistaTipusCites == null || Utils.size(llistaTipusCites.getTipusCites()) == 0) {
			List<ErrorDto> errors = new ArrayList<>();
			errors.add(new ErrorDto(9999L, "E", this.msg("error_no_public", locale)));
			model.addAttribute("errors", errors);
		}
		model.addAttribute("llistaTipusCites", llistaTipusCites);
		List<OficinaCitaPreviaDto> oficines = citaPreviaPublicClient.getOficinesHabilitatesCitaPrevia(
			application, subaplCoa, locale);
		boolean senseOficines = true;
		if(oficines != null && !oficines.isEmpty()) {
			for(OficinaCitaPreviaDto oficina : oficines) {
				if(oficina.isHbl()) { senseOficines = false; break; }
			}
		}
		model.addAttribute("senseOficines", senseOficines);
		return "index";
	}

}
