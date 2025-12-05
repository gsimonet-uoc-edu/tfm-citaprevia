package uoc.edu.citaprevia.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.dto.generic.ErrorsDto;

@Getter @Setter @NoArgsConstructor @ToString(callSuper = true)
public class UbicacioDto extends ErrorsDto {
	
	private Long con;	
	private String nom;
	private String nomcar;
	private String obs;
	private SubaplicacioDto subapl;


}
