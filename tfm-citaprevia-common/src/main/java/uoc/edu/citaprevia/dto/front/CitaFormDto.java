package uoc.edu.citaprevia.dto.front;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CitaFormDto {
    private Long tipcitCon;
    private LocalDateTime dataHoraInici;
    private LocalDateTime dataHoraFin;
    private Long agendaCon;

    // ESTÁTICOS
    private String nom;
    private String llis;
    private String numdoc;
    private String nomcar;
    private Long tel;
    private String ema;
    private String obs;

    // DINÁMICOS: lit1er → lit10e
    private Map<String, String> lit = new HashMap<>();

    //@JsonAnySetter
    public void setLit(String name, String value) {
        if (name.matches("lit[0-9]{1,2}[a-z]{1,2}")) {
            lit.put(name, value);
        }
    }
}
