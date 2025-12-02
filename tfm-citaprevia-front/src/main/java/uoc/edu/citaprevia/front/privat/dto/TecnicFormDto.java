package uoc.edu.citaprevia.front.privat.dto;


import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class TecnicFormDto {

    private String coa;    
    private String originalCoa;   
    @NotNull (message = "{error.tecnic.pass.obligatori}")
    private String pass;   
    @NotNull(message = "{error.tecnic.nom.obligatori}")
	private String nom;   
	private String ll1;   
	private String ll2;   
	@NotNull(message = "{error.tecnic.prf.obligatori}")
	private String prf;
}