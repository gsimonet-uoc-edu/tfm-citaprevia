package uoc.edu.citaprevia.front.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.CalendariDto;
import uoc.edu.citaprevia.dto.SeleccioTipusCitaDto;
import uoc.edu.citaprevia.dto.SetmanaTipusDto;
import uoc.edu.citaprevia.dto.TipusCitaDto;
import uoc.edu.citaprevia.front.util.RestTemplateResponseErrorHandler;

@Service("citprePublicService")
public class CitaPreviaPublicClientImpl  implements CitaPreviaPublicClient{
	
	@Value("${citaprevia.service.api.host}")
	private String citaPreviaApi;
	
	private String getBaseApiUrl() { return citaPreviaApi; }
	
	private static final String PARAM_SUBAPL_COA = "subaplCoa";
	private static final String PARAM_TIPCIT_CON = "tipCitCon";
	private static final String PARAM_HOR_CON = "horCon";
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

	@Override
	public List<AgendaDto> getAgendasBySubaplicacioAndTipusCita(String subaplCoa, Long tipCitCon, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_SUBAPL_COA, subaplCoa);
		params.put(PARAM_TIPCIT_CON, tipCitCon);		
		params.put("lang", locale);
		String url = getBaseApiUrl() + "/agendes/tipus-cites/{tipCitCon}/subaplicacions/{subaplCoa}?lang={lang}";	
		AgendaDto[] list = restTemplate.getForObject(url, AgendaDto[].class, params);
		return list == null ? new ArrayList<>() : Arrays.asList(list);
	}
	
	@Override
	public List<SetmanaTipusDto> getSetmanesTipusByHorari(Long horCon, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_HOR_CON, horCon);	
		params.put("lang", locale);
		String url = getBaseApiUrl() + "/setmanes-tipus/horaris/{horCon}?lang={lang}";	
		SetmanaTipusDto[] list = restTemplate.getForObject(url, SetmanaTipusDto[].class, params);
		return list == null ? new ArrayList<>() : Arrays.asList(list);
	}

	@Override
	public CalendariDto getCalendariCites(String subaplCoa, Long tipCitCon, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_SUBAPL_COA, subaplCoa);
		params.put(PARAM_TIPCIT_CON, tipCitCon);
		params.put("lang", locale);
		String url = getBaseApiUrl() + "/public/calendaris/subaplicacions/{subaplCoa}/tipus-cites/{tipCitCon}?lang={lang}";
		return restTemplate.getForObject(url, CalendariDto.class, params);
	}
	
	@Override
	public TipusCitaDto getTipusCita (Long tipCitCon, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_TIPCIT_CON, tipCitCon);
		params.put("lang", locale);
		String url = getBaseApiUrl() + "/tipus-cites/{tipCitCon}?lang={lang}";
		return restTemplate.getForObject(url, TipusCitaDto.class, params);
	}

}
