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
    @NotNull
    private String pass;   
    @NotNull
	private String nom;   
	private String ll1;   
	private String ll2;   
	@NotNull
	private String prf;
}