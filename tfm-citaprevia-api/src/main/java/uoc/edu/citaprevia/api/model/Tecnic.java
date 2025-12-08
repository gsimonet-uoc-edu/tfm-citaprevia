package uoc.edu.citaprevia.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
	@Column(length = 50)
	/** 
	 * Codi alfanumèric (login)
	 */
    private String coa;
	
	@Column(length = 50)
	/**
	 * Contrasenya Login
	 */
	private String pass;
	
	@Column(length = 50)
	/**
	 * Nom del tècnic
	 */
	private String nom;
	
	@Column(length = 50)
	/**
	 * Primer llinatge del tècnic
	 */
	private String ll1;
	
	@Column(length = 50)
	/**
	 * Segon llinatge del tècnic
	 */
	private String ll2;
	
	@Column(length = 20)
	/**
	 * Perfil (rol) del tècnic
	 */
    private String prf;

}
