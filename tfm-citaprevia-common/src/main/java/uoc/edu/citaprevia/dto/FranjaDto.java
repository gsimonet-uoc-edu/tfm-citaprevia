package uoc.edu.citaprevia.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uoc.edu.citaprevia.dto.generic.ErrorsDto;

@Getter @Setter @NoArgsConstructor
public class FranjaDto extends ErrorsDto{
    private String horaInici; // "09:00"
    private String horaFi;    // "09:30"
    private Long agendaCon;
    private boolean lliure;
}
