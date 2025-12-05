package uoc.edu.citaprevia.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.dto.generic.ErrorsDto;

@Getter @Setter @NoArgsConstructor @ToString(callSuper = true)
public class SeleccioTipusCitaDto extends ErrorsDto {
	
	private List<TipusCitaDto> tipusCites = new ArrayList<>();
    public SeleccioTipusCitaDto(List<TipusCitaDto> tipusCites) {
        this.tipusCites = tipusCites;
    }
	
}
