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
    private Long agendaCon;

    // ESTÁTICOS
    private String nom;
    private String lli;
    private String nomcar;
    private Long tel;
    private String ema;
    private String obs;

    // DINÁMICOS: lit1er → lit10e
    private Map<String, String> lit = new HashMap<>();

    //@JsonAnySetter
    public void setLit(String name, String value) {
        if (name.matches("lit[1-9][0-9]?[a-z]")) {
            lit.put(name, value);
        }
    }
}
