package uoc.edu.citaprevia.front.privat.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class AgendaFormDto {
	
	private Long con; 
    
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datini;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate datfin;
    
    @NotNull
    private Long centreCon;
    
    @NotNull
    private Long horariCon; 
    
}
