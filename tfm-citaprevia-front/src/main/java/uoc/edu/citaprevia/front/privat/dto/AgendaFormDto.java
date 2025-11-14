package uoc.edu.citaprevia.front.privat.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import uoc.edu.citaprevia.model.SiNo;

@Getter @Setter
public class AgendaFormDto {
	
private Long con; // Per edició
    
    @NotNull(message = "{validation.required}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datini;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datfin;
    
    @NotNull(message = "{validation.required}")
    private Long centreCon; // Con de UbicacioDto (Lookup)
    
    @NotNull(message = "{validation.required}")
    private Long horariCon; // Con de HorariDto (Lookup)
    
}
