package uoc.edu.citaprevia.front.privat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.dto.generic.ErrorsDto;

@Getter @Setter @NoArgsConstructor @ToString
public class TipusCitaFormDto extends ErrorsDto{

	private Long con;
	private String dec;
	private String dem;
	private String tipcitmod;
	private String subaplCoa;
	
}
