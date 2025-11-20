package uoc.edu.citaprevia.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.dto.generic.ErrorsDto;
import uoc.edu.citaprevia.model.ModalitatTipusCita;

@Getter @Setter @NoArgsConstructor @ToString
public class TipusCitaDto extends ErrorsDto{

	public TipusCitaDto(String dec, String dem) {
	}
	private Long con;
	private String dec;
	private String dem;
	private ModalitatTipusCita tipcitmod;
	private SubaplicacioDto subapl;
	
}
