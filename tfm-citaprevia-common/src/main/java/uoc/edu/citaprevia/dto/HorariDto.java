package uoc.edu.citaprevia.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class HorariDto {
	
	private Long con;
	private String dec;
	private String dem;
	private SubaplicacioDto subapl;
	private TipusCitaDto tipusCita;

}
