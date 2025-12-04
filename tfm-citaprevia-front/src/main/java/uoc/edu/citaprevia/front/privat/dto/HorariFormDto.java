package uoc.edu.citaprevia.front.privat.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class HorariFormDto {
    
    private Long con;
    
    @NotBlank
    @Size(max = 50)
    private String dec;
    
    @Size(max = 255)
    private String dem;
    
    @NotNull
    private Long tipusCitaCon;
}