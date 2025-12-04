package uoc.edu.citaprevia.front.privat.dto;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class SetmanaTipusFormDto {

    private Long diasetCon; 

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horini;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horfin;
    
}
