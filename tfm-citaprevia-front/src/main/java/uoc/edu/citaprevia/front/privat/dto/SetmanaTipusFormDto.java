package uoc.edu.citaprevia.front.privat.dto;

import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class SetmanaTipusFormDto {

	/**
     * Identificador del dia de la setmana (1=Dilluns, 7=Diumenge).
     * Obligatori perquè aquest camp identifica la franja.
     */
    @NotNull(message = "{error.diasetcon.notnull}")
    private Long diasetCon; 

    /**
     * Hora d'inici de la franja.
     */
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horini;

    /**
     * Hora de fi de la franja.
     */
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horfin;
    
}
