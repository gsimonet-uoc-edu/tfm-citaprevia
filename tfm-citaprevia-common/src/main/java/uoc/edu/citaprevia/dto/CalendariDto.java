package uoc.edu.citaprevia.dto;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.dto.generic.ErrorsDto;

@Getter @Setter @NoArgsConstructor @ToString(callSuper = true) @EqualsAndHashCode
public class CalendariDto extends ErrorsDto{
    private int any;
    private int mes; // 1-12
    private List<DiaCalendariDto> dies;
}
