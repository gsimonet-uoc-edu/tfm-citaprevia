package uoc.edu.citaprevia.front.service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import uoc.edu.citaprevia.dto.SubaplicacioDto;

@Service("subaplicacioClientImpl")
public class SubaplicacioClientImpl implements SubaplicacioClient{
	
	private static final String SUBAPL_COA = "subaplCoa";
	
	@Value("${citaprevia.service.api.host}")
	private String citapreviaApi;

	private RestTemplate restTemplate = new RestTemplate();
	private String getBaseApiUrl() { return citapreviaApi; }
	
	@Override
	public SubaplicacioDto getSubaplicacio(String subaplCoa, Locale locale) {
		Map<String, Object> params = new HashMap<>();
		params.put(SUBAPL_COA, subaplCoa);
		params.put("lang", locale);
		String url = getBaseApiUrl() + "/subaplicacions/{subaplCoa}?lang={lang}";
		ResponseEntity<SubaplicacioDto> response = restTemplate.exchange(url, HttpMethod.GET, null, SubaplicacioDto.class, params);
		return response.getBody() == null ? new SubaplicacioDto() : response.getBody();
	}

}
