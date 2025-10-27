package uoc.edu.citaprevia.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.model.ModalitatTipusCita;
import uoc.edu.citaprevia.model.SiNo;

@Getter @Setter @NoArgsConstructor @ToString
public class TipusCitaDto {

	private Long con;
	private String dec;
	private String dem;
	private SiNo notval;
	private Long cap;
	private SiNo gespri;
	private ModalitatTipusCita tipcitmod;
	private SubaplicacioDto subapl;
	
}
