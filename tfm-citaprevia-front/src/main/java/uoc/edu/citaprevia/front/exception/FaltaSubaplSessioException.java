package uoc.edu.citaprevia.front.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class FaltaSubaplSessioException extends BadCredentialsException {
	private static final long serialVersionUID = 1L;

	public FaltaSubaplSessioException(String msg) {
		super(msg);
		
	}
}