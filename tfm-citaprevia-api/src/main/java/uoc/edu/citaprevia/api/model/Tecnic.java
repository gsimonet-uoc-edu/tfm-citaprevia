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
	/** 
	 * Codi alfanumèric (login)
	 */
    private String coa;
	/**
	 * Contrasenya Login
	 */
	private String pass;
	/**
	 * Nom del tècnic
	 */
	private String nom;
	/**
	 * Primer llinatge del tècnic
	 */
	private String ll1;
	/**
	 * Segon llinatge del tècnic
	 */
	private String ll2;
	/**
	 * Perfil (rol) del tècnic
	 */
    private String prf;

}
