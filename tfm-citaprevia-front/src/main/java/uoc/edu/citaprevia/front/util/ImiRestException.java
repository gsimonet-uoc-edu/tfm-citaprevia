package uoc.edu.citaprevia.front.util;

import uoc.edu.citaprevia.dto.generic.ErrorDto;

public class ImiRestException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private ErrorDto error;
	private int httpStatus;
	
	public ImiRestException() { }
	public ImiRestException(ErrorDto error, int status) {
		this.error = error;
		this.httpStatus = status;
	}
	
	public void setError(ErrorDto error) {
		this.error = error;
	}
	public ErrorDto getError() { return error; }
	public int getHttpStatus() { return httpStatus; }
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}

	@Override
	public String toString() {
		return new StringBuilder("ImiRestException [ ")
			.append("httpStatus=").append(httpStatus)
			.append(", error=").append(error)
			.append(" ]").toString();
	}
}