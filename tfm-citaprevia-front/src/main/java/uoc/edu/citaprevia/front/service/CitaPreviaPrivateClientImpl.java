package uoc.edu.citaprevia.front.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.dto.HorariDto;
import uoc.edu.citaprevia.dto.SetmanaTipusDto;
import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.dto.TipusCitaDto;
import uoc.edu.citaprevia.dto.UbicacioDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.front.util.RestTemplateResponseErrorHandler;

@Service("citprePrivateService")
public class CitaPreviaPrivateClientImpl implements CitaPreviaPrivateClient{

	@Value("${citaprevia.service.api.host}")
	private String citaPreviaApi;
	
	private String getBaseApiUrl() { return citaPreviaApi; }
	
	private static final String PARAM_SUBAPL_COA = "subaplCoa";
	private static final String PARAM_TIPCIT_CON = "tipCitCon";
	private static final String PARAM_UBI_CON = "ubiCon";
	private static final String PARAM_AGE_CON = "ageCon";
	private static final String PARAM_HOR_CON = "horCon";
	private static final String PARAM_TEC_COA = "tecCoa";
	private static final String PARAM_LOCALE = "lang";
	private static final String PARAM_CIT_CON = "citCon";
	
	
	private RestTemplate restTemplate = new RestTemplate();
	public CitaPreviaPrivateClientImpl (RestTemplateBuilder restTemplateBuilder) {
	    this.restTemplate = restTemplateBuilder
		    .errorHandler(new RestTemplateResponseErrorHandler())
		    .build();
	}
	
	@Override
	public TecnicDto getTecnic (String tecCoa, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_TEC_COA, tecCoa);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/tecnics/{tecCoa}?lang={lang}";
		return restTemplate.getForObject(url, TecnicDto.class, params);
	}
	
	@Override
	public TecnicDto saveTecnic(TecnicDto tecnic, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/tecnics?lang={lang}";
	    ResponseEntity<TecnicDto> response = restTemplate.postForEntity(url, tecnic, TecnicDto.class, params
	        );
		return response.getBody() == null ? new TecnicDto() : response.getBody();
	}
	
	@Override
	public TecnicDto updateTecnic(String tecCoa, TecnicDto dto, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_TEC_COA, tecCoa);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/tecnics/{tecCoa}?lang={lang}";	
	    ResponseEntity<TecnicDto> response = restTemplate.exchange(url, 
		        HttpMethod.PUT, 
		        new HttpEntity<>(dto),               
		        TecnicDto.class,     
		        params             
		    );
		return response.getBody() == null ? new TecnicDto() : response.getBody();
	}
	
	@Override
	public ErrorDto deleteTecnic(String tecCoa, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_TEC_COA, tecCoa);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/tecnics/{tecCoa}?lang={lang}";
	    ResponseEntity<ErrorDto> response = restTemplate.exchange(url, 
	        HttpMethod.DELETE, 
	        null,               
	        ErrorDto.class,     
	        params             
	    );

	    return response.getBody();
	}
	
	@Override
	public List<TecnicDto> getTecnicsBySubaplicacio(String subaplCoa, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_SUBAPL_COA, subaplCoa);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/tecnics/subaplicacions/{subaplCoa}?lang={lang}";	
		TecnicDto[] list = restTemplate.getForObject(url, TecnicDto[].class, params);
		return list == null ? new ArrayList<>() : Arrays.asList(list);
	}
	
	@Override
	public List<AgendaDto> getAgendasBySubaplicacio(String subaplCoa, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_SUBAPL_COA, subaplCoa);		
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/agendes/subaplicacions/{subaplCoa}?lang={lang}";	
		AgendaDto[] list = restTemplate.getForObject(url, AgendaDto[].class, params);
		return list == null ? new ArrayList<>() : Arrays.asList(list);
	}
	
	@Override
	public List<AgendaDto> getAgendasByTecnic(String tecCoa, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_TEC_COA, tecCoa);		
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/tecnics/{tecCoa}/agendes?lang={lang}";	
		AgendaDto[] list = restTemplate.getForObject(url, AgendaDto[].class, params);
		return list == null ? new ArrayList<>() : Arrays.asList(list);
	}
	
	@Override
	public AgendaDto saveAgenda(AgendaDto agenda, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/agendes?lang={lang}";
	    ResponseEntity<AgendaDto> response = restTemplate.postForEntity(url, agenda, AgendaDto.class, params
	        );
		return response.getBody() == null ? new AgendaDto() : response.getBody();
	}
	
	@Override
	public AgendaDto updateAgenda(Long ageCon, AgendaDto dto, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_AGE_CON, ageCon);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/agendes/{ageCon}?lang={lang}";	
	    ResponseEntity<AgendaDto> response = restTemplate.exchange(url, 
		        HttpMethod.PUT, 
		        new HttpEntity<>(dto),               
		        AgendaDto.class,     
		        params             
		    );
		return response.getBody() == null ? new AgendaDto() : response.getBody();
	}
	
	@Override
	public ErrorDto deleteAgenda(Long ageCon, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_AGE_CON, ageCon);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/agendes/{ageCon}?lang={lang}";
	    ResponseEntity<ErrorDto> response = restTemplate.exchange(url, 
	        HttpMethod.DELETE, 
	        null,               
	        ErrorDto.class,     
	        params             
	    );

	    return response.getBody();
	}
	
	
	@Override
	public CitaDto updateCita(Long con, CitaDto dto, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_CIT_CON, con);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/cites/{citCon}/?lang={lang}";	
	    ResponseEntity<CitaDto> response = restTemplate.exchange(url, 
		        HttpMethod.PUT, 
		        new HttpEntity<>(dto),               
		        CitaDto.class,     
		        params             
		    );
		return response.getBody() == null ? new CitaDto() : response.getBody();
	}
	
	@Override
	public ErrorDto deleteCita(Long citCon, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_CIT_CON, citCon);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/cites/{citCon}?lang={lang}";
	    ResponseEntity<ErrorDto> response = restTemplate.exchange(url, 
	        HttpMethod.DELETE, 
	        null,               
	        ErrorDto.class,     
	        params             
	    );

	    return response.getBody();
	}
	
	@Override
	public List<UbicacioDto> getUbicacionsBySubaplicacio(String subaplCoa, Locale locale) {	
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_SUBAPL_COA, subaplCoa);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/ubicacions/subaplicacions/{subaplCoa}?lang={lang}";	
		UbicacioDto[] list = restTemplate.getForObject(url, UbicacioDto[].class, params);
		return list == null ? new ArrayList<>() : Arrays.asList(list);
	}
	
	@Override
	public List<HorariDto> getHorarisBySubaplicacio(String subaplCoa, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_SUBAPL_COA, subaplCoa);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/horaris/subaplicacions/{subaplCoa}?lang={lang}";	
		HorariDto[] list = restTemplate.getForObject(url, HorariDto[].class, params);
		return list == null ? new ArrayList<>() : Arrays.asList(list);
	}
	
	@Override
	public HorariDto getHorari (Long horCon, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_HOR_CON, horCon);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/horaris/{horCon}?lang={lang}";
		return restTemplate.getForObject(url, HorariDto.class, params);
	}
	
	
	@Override
	public HorariDto saveHorari(HorariDto horari, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/horaris?lang={lang}";
	    ResponseEntity<HorariDto> response = restTemplate.postForEntity(url, horari, HorariDto.class, params
	        );
		return response.getBody() == null ? new HorariDto() : response.getBody();
	}
	
	@Override
	public HorariDto updateHorari(Long horCon, HorariDto dto, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_HOR_CON, horCon);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/horaris/{horCon}?lang={lang}";	
	    ResponseEntity<HorariDto> response = restTemplate.exchange(url, 
		        HttpMethod.PUT, 
		        new HttpEntity<>(dto),               
		        HorariDto.class,     
		        params             
		    );
		return response.getBody() == null ? new HorariDto() : response.getBody();
	}
	
	@Override
	public ErrorDto deleteHorari(Long horCon, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_HOR_CON, horCon);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/horaris/{horCon}?lang={lang}";
	    ResponseEntity<ErrorDto> response = restTemplate.exchange(url, 
	        HttpMethod.DELETE, 
	        null,               
	        ErrorDto.class,     
	        params             
	    );

	    return response.getBody();
	}
	
	@Override
	public List<TipusCitaDto> getTipusCitesBySubaplicacio(String subaplCoa, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_SUBAPL_COA, subaplCoa);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/tipus-cites/subaplicacions/{subaplCoa}?lang={lang}";	
		TipusCitaDto[] list = restTemplate.getForObject(url, TipusCitaDto[].class, params);
		return list == null ? new ArrayList<>() : Arrays.asList(list);
	}
	
	@Override
	public SetmanaTipusDto addSetmanaTipusToHorari(SetmanaTipusDto setmanaTipus, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/setmanes-tipus?lang={lang}";
	    ResponseEntity<SetmanaTipusDto> response = restTemplate.postForEntity(url, setmanaTipus, SetmanaTipusDto.class, params
	        );
		return response.getBody() == null ? new SetmanaTipusDto() : response.getBody();
	}
	
	@Override
	public ErrorDto deleteSetmanaTipusOfHorari(Long horCon, SetmanaTipusDto settip, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_HOR_CON, horCon);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/setmanes-tipus/horaris/{horCon}?lang={lang}";
	    ResponseEntity<ErrorDto> response = restTemplate.exchange(url, 
	        HttpMethod.DELETE, 
	        new HttpEntity<>(settip),               
	        ErrorDto.class,     
	        params             
	    );

	    return response.getBody();
	}
	
	@Override
	public TipusCitaDto saveTipusCita(TipusCitaDto tipusCita, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/tipus-cites?lang={lang}";
	    ResponseEntity<TipusCitaDto> response = restTemplate.postForEntity(url, tipusCita, TipusCitaDto.class, params
	        );
		return response.getBody() == null ? new TipusCitaDto() : response.getBody();
	}
	
	@Override
	public TipusCitaDto updateTipusCita(Long tipCitCon, TipusCitaDto dto, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_TIPCIT_CON, tipCitCon);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/tipus-cites/{tipCitCon}?lang={lang}";	
	    ResponseEntity<TipusCitaDto> response = restTemplate.exchange(url, 
		        HttpMethod.PUT, 
		        new HttpEntity<>(dto),               
		        TipusCitaDto.class,     
		        params             
		    );
		return response.getBody() == null ? new TipusCitaDto() : response.getBody();
	}
	
	@Override
	public ErrorDto deleteTipusCita(Long tipCitCon, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_TIPCIT_CON, tipCitCon);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/tipus-cites/{tipCitCon}?lang={lang}";
	    ResponseEntity<ErrorDto> response = restTemplate.exchange(url, 
	        HttpMethod.DELETE, 
	        null,               
	        ErrorDto.class,     
	        params             
	    );
	    return response.getBody();
	}
	
	
	@Override
	public UbicacioDto saveUbicacio(UbicacioDto ubicacio, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/ubicacions?lang={lang}";
	    ResponseEntity<UbicacioDto> response = restTemplate.postForEntity(url, ubicacio, UbicacioDto.class, params
	        );
		return response.getBody() == null ? new UbicacioDto() : response.getBody();
	}
	
	@Override
	public UbicacioDto updateUbicacio(Long ubiCon, UbicacioDto dto, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_UBI_CON, ubiCon);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/ubicacions/{ubiCon}?lang={lang}";	
	    ResponseEntity<UbicacioDto> response = restTemplate.exchange(url, 
		        HttpMethod.PUT, 
		        new HttpEntity<>(dto),               
		        UbicacioDto.class,     
		        params             
		    );
		return response.getBody() == null ? new UbicacioDto() : response.getBody();
	}
	
	@Override
	public ErrorDto deleteUbicacio(Long ubiCon, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(PARAM_UBI_CON, ubiCon);
		params.put(PARAM_LOCALE, locale);
		String url = getBaseApiUrl() + "/ubicacions/{ubiCon}?lang={lang}";
	    ResponseEntity<ErrorDto> response = restTemplate.exchange(url, 
	        HttpMethod.DELETE, 
	        null,               
	        ErrorDto.class,     
	        params             
	    );
	    return response.getBody();
	}
	
}
