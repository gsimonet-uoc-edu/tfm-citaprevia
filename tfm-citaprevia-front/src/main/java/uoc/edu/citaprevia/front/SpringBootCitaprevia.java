package uoc.edu.citaprevia.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringBootCitaprevia extends SpringBootServletInitializer {
	private static Class<SpringBootCitaprevia> applicationClass = SpringBootCitaprevia.class;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCitaprevia.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}	
}
