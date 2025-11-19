package uoc.edu.citaprevia.front.privat.dto;


import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class TecnicFormDto {

    // Camp clau / Codi de l'Operari
    private String coa; 
    
    private String originalCoa;
    
    // Contrasenya
    @NotNull (message = "{error.tecnic.pass.obligatori}")
    private String pass;
    
    // Nom
    @NotNull(message = "{error.tecnic.nom.obligatori}")
	private String nom;
    
    // Lliure 1
	private String ll1;
    
    // Lliure 2
	private String ll2;
    
    // Perfil (Ex: 'ADMIN', 'BASIC', etc.)
	@NotNull(message = "{error.tecnic.prf.obligatori}")
	private String prf;
}