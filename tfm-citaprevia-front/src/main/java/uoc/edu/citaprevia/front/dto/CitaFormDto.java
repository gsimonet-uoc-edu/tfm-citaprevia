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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataHoraInici;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataHoraFin;
    @NotNull private Long agendaCon;

    @NotBlank private String nom;
    @NotBlank private String llis;
    @NotBlank private String numdoc;
    @NotBlank private String nomcar;
    @NotBlank @Pattern(regexp = "[0-9]{9}") private String tel; // String!
    @NotBlank @Email private String ema;
    private String obs;

    private Map<String, String> lit = new HashMap<>();

    public void setLit(String name, String value) {
        if (name.matches("lit[0-9]{1,2}[a-z]{1,2}")) {
            lit.put(name, value);
        }
    }
}