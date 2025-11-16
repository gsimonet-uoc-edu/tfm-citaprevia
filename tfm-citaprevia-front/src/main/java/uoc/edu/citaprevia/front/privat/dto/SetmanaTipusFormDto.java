package uoc.edu.citaprevia.front.privat.dto;

import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
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
    
    /**
     * Camp utilitzat a la vista per marcar si aquesta franja de dia ha de ser eliminada/buidada.
     */
    private Boolean delete;
}
