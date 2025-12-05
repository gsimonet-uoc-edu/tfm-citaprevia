package uoc.edu.citaprevia.front.config;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;
import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.front.service.CitaPreviaPrivateClient;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;


/**
 * Codi típic que defineix com és comporta la seguretat (autenticació, autorització, gestió de sessions...)
 **/
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


	@Autowired
	private CitaPreviaPrivateClient citaPreviaPrivateClient;
	
	@Autowired
	private MessageSource bundle;
	
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

	@Bean
	public UserDetailsService userDetailsService() {
	    return username -> {
	    	
	    	if (username == null || username.trim().isEmpty()) {
                throw new UsernameNotFoundException(bundle.getMessage("login.error.usuari.null", null, null)); 
            }
	    	
	        TecnicDto tecnic = citaPreviaPrivateClient.getTecnic(StringUtils.upperCase(username).trim(), null);
	        
	        if (tecnic == null || Utils.isEmpty(tecnic.getCoa())) {
                throw new UsernameNotFoundException(bundle.getMessage("login.error.tecnic", null, null)); 
            }

	        return User.withUsername(tecnic.getCoa())
	            .password(tecnic.getPass())
	            .authorities(tecnic.getPrf())
	            .build();
	    };
	}

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return rawPassword.toString().equals(encodedPassword);
            }
        };
    }
}
