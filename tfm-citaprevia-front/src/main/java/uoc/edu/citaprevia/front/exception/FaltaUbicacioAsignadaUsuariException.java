package uoc.edu.citaprevia.front.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class FaltaUbicacioAsignadaUsuariException extends BadCredentialsException {
	private static final long serialVersionUID = 1L;

	public FaltaUbicacioAsignadaUsuariException(String msg) {
		super(msg);
		
	}

}
