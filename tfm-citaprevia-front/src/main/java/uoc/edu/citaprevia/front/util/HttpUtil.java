package uoc.edu.citaprevia.front.util;

import java.util.Map;

import org.springframework.http.HttpHeaders;

public class HttpUtil {
	private HttpUtil() { /* Constructor buit. */ }
	
	public static HttpHeaders createHeaders(String name, String value){
		HttpHeaders header = new HttpHeaders();
		header.set( name, value );
		return header;
	}

	public static HttpHeaders createHeaders(Map<String, String> headers) {
		HttpHeaders header = new HttpHeaders();
		header.setAll(headers);
		return header;
	}

}