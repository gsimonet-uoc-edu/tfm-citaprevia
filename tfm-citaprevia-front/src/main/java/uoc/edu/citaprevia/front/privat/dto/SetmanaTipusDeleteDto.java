package uoc.edu.citaprevia.front.privat.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class SetmanaTipusDeleteDto {

    private Long diasetCon;
    
    // Assegura't que el format coincideixi amb el del JS (HH:mm)
    @JsonFormat(pattern = "HH:mm") 
    private LocalTime horini;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime horfin;
}
