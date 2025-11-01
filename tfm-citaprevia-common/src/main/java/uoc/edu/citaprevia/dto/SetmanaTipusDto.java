package uoc.edu.citaprevia.dto;

import java.time.LocalTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.dto.generic.ErrorsDto;

@Getter @Setter @NoArgsConstructor @ToString
public class SetmanaTipusDto extends ErrorsDto{
	
	private HorariDto horari;
	 
	private Long diasetCon;
	
	private LocalTime horini;
	
	private LocalTime horfin;
	
	private TipusCitaDto tipcit;

}
