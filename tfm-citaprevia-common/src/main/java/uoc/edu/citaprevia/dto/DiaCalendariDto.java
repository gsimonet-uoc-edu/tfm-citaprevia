package uoc.edu.citaprevia.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.dto.generic.ErrorsDto;

@Getter @Setter @NoArgsConstructor  @ToString
public class DiaCalendariDto extends ErrorsDto{
    private int dia; // 1-31
    private boolean disponible;
    private List<FranjaDto> franges; // solo si disponible
}
