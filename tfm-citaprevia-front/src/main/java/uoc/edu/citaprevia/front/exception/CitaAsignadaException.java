package uoc.edu.citaprevia.front.exception;

import org.springframework.security.authentication.LockedException;

public class CitaAsignadaException extends LockedException {
	
	private static final long serialVersionUID = 3464159221752889258L;

	public CitaAsignadaException(String msg) {
		super(msg);
		
	}

}
