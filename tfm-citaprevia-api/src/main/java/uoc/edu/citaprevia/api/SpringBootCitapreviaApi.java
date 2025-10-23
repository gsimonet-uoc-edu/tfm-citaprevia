package uoc.edu.citaprevia.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@SpringBootApplication
public class SpringBootCitapreviaApi extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootCitapreviaApi.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<SpringBootCitapreviaApi> applicationClass = SpringBootCitapreviaApi.class;

	@Bean
	public OpenAPI customOpenAPI() {
	    return new OpenAPI()
	    	.components(
	    		new Components()
	    			.addSecuritySchemes("api-key",
    					new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name("X-API-KEY"))
	    	)
	    	.info(new Info().title("TFM-CITAPREVIA API").version("100"));
	}
}
