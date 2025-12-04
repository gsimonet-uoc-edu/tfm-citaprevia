package uoc.edu.citaprevia.front.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CampConfigDto {
    private String name;  // Ex: lit1, lit2, lit3....
    private String label; // Ex: Nom, DNI, Telèfon...
    private String type;  // Ex: text, email, tel, textarea, date...
    private String validation; // Ex: required
}
