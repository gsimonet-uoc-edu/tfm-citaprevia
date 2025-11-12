package uoc.edu.citaprevia.front.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;
//import uoc.edu.citaprevia.api.service.TecnicService;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    //private final TecnicService tecnicService; //TODO:

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(new AntPathRequestMatcher("/private/login")).permitAll()
	            .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
	            .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
	            .requestMatchers(new AntPathRequestMatcher("/private/**")).authenticated()
	            .anyRequest().permitAll()
	        )
	        .formLogin(form -> form
	            .loginPage("/private/login")
	            .loginProcessingUrl("/private/login")
	            .defaultSuccessUrl("/private/calendari", true)
	            .failureUrl("/private/login?error=true")
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutUrl("/private/logout")
	            .logoutSuccessUrl("/private/login")
	            .permitAll()
	        );

	    return http.build();
	}

	// TODO tecnicService
    /*@Bean
    public UserDetailsService userDetailsService() {
        return username -> tecnicService.findByCoa(username)
            .map(tecnic -> org.springframework.security.core.userdetails.User
                .withUsername(tecnic.getCoa())
                .password(tecnic.getPass())
                .authorities(tecnic.getPrf().name())
                .build()
            )
            .orElseThrow(() -> new UsernameNotFoundException("Tècnic no trobat: " + username));
    }*/

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
