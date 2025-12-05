package uoc.edu.citaprevia.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.dto.generic.ErrorsDto;

@Getter @Setter @NoArgsConstructor @ToString(callSuper = true)
public class FranjaDto extends ErrorsDto{
    private String horaInici; // Ex: "09:00"
    private String horaFi;    // Ex: "09:30"
    private Long agendaCon;
    private boolean lliure;
}
