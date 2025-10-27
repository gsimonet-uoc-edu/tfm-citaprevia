package uoc.edu.citaprevia.front.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class FaltaSubaplAsignadaUsuariException extends BadCredentialsException {
	private static final long serialVersionUID = 1L;

	public FaltaSubaplAsignadaUsuariException(String msg) {
		super(msg);
		
	}

}
