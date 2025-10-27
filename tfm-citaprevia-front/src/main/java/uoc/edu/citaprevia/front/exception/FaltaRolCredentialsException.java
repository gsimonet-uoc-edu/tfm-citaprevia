package uoc.edu.citaprevia.front.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class FaltaRolCredentialsException extends BadCredentialsException {
	private static final long serialVersionUID = 1L;

	public FaltaRolCredentialsException(String msg) {
		super(msg);
		
	}

}
