package uoc.edu.citaprevia.dto.generic;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class ErrorDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long con;
	private String tip;
	private String dem;
	private Object[] args;

	public ErrorDto(Long con, String tip, String dem, Object[] args) {
		super();
		this.con = con;
		this.tip = tip;
		this.dem = dem;
		this.args = args;
	}
	
	public ErrorDto(Long con, String tip, String dem) {
		super();
		this.con = con;
		this.tip = tip;
		this.dem = dem;
	}

	public ErrorDto(Long con, String dem) {
		super();
		this.con = con;
		this.tip = "E";
		this.dem = dem;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

}