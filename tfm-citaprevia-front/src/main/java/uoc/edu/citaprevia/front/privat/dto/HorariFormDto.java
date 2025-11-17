package uoc.edu.citaprevia.front.privat.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class HorariFormDto {
    
    private Long con; // Identificador para edición
    
    @NotBlank
    @Size(max = 50)
    private String dec; // Descripción Corta (dec)
    
    @Size(max = 255)
    private String dem; // Descripción Extendida (dem)
    
    @NotNull
    private Long tipusCitaCon; // Conexión con TipusCita (lookup)
}