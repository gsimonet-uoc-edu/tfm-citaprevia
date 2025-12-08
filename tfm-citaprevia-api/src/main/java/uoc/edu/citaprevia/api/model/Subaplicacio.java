package uoc.edu.citaprevia.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SUBAPLICACIO")
@Getter @Setter @NoArgsConstructor @ToString
/**
 * Una subaplicació indica l'organisme o empresa que té implantat el sistema de Cita Prèvia online.
 */
public class Subaplicacio {
	
	@Id
	@Column(length = 3)
	/**
	 * Codi alfanumèric
	 */
	private String coa;	
	
	@Column(length = 50)
	/**
	 * Descripció curta
	 */
	private String dec;
	
	@Column(length = 100)
	/**
	 * Descripció llarga
	 */
	private String dem;

}
