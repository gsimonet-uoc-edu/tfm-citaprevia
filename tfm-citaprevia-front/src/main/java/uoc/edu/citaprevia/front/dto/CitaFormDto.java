package uoc.edu.citaprevia.front.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CitaFormDto {
    @NotNull private Long tipcitCon;
    @NotNull 
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataHoraInici;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataHoraFin;
    @NotNull private Long agendaCon;

    @NotBlank private String nom;
    @NotBlank private String llis;
    @NotBlank private String numdoc;
    @NotBlank private String nomcar;
    @NotBlank @Pattern(regexp = "[0-9]{9}") private String tel; // String!
    @NotBlank @Email private String ema;
    private String obs;

    private String lit1;
    private String lit2;
    private String lit3;
    private String lit4;
    private String lit5;
    private String lit6;
    private String lit7;
    private String lit8;
    private String lit9;
    private String lit10;
    
}