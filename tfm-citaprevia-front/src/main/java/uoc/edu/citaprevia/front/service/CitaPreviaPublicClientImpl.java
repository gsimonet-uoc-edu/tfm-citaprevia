package uoc.edu.citaprevia.front.service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import uoc.edu.citaprevia.dto.SeleccioTipusCitaDto;
import uoc.edu.citaprevia.front.util.RestTemplateResponseErrorHandler;

@Service("citprePublicService")
public class CitaPreviaPublicClientImpl  implements CitaPreviaPublicClient{
	
	@Value("${imi.citaprevia.api.host}")
	private String citaPreviaApi;
	
	private String getBaseApiUrl() { return citaPreviaApi; }
	
	private static final String PARAM_APL_COA = "aplCoa";
	private static final String PARAM_SUBAPL_COA = "subaplCoa";
	private static final String PARAM_LOCALE = "locale";
	private static final String PARAM_UBI_CON = "ubiCon";
	
	
	private RestTemplate restTemplate = new RestTemplate();
	public CitaPreviaPublicClientImpl (RestTemplateBuilder restTemplateBuilder) {
	    this.restTemplate = restTemplateBuilder
		    .errorHandler(new RestTemplateResponseErrorHandler())
		    .build();
	}
	
	@Override
	public SeleccioTipusCitaDto getAllTipusCites(String subaplCoa, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_SUBAPL_COA, subaplCoa);
		params.put("lang", locale);
		String url = getBaseApiUrl() + "/public/subaplicacions/{subaplCoa}/selecciotipuscites?lang={lang}";
		return restTemplate.getForObject(url, SeleccioTipusCitaDto.class, params);
	}

}
