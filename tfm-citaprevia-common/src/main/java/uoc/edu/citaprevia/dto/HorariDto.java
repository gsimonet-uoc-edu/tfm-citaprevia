package uoc.edu.citaprevia.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.dto.generic.ErrorsDto;

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class HorariDto extends ErrorsDto{
	
	private Long con;
	private String dec;
	private String dem;
	private SubaplicacioDto subapl;
	private TipusCitaDto tipusCita;

}
