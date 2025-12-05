package uoc.edu.citaprevia.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TECNIC")
@Getter @Setter @NoArgsConstructor @ToString
/**
 * Un tècnic és la persona que atén a l'interessa durant el transcurs la cita
 */
public class Tecnic {
	
	@Id
    private String coa;
	private String pass;
	private String nom;
	private String ll1;
	private String ll2;
    private String prf;

}
