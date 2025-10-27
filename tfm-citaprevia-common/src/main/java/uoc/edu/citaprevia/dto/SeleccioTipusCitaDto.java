package uoc.edu.citaprevia.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.dto.generic.ErrorsDto;

@Getter @Setter @NoArgsConstructor @ToString
public class SeleccioTipusCitaDto extends ErrorsDto {
	List<TipusCitaDto> tipusCites;

	
}
