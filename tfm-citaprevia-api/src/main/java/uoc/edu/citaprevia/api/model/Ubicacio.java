package uoc.edu.citaprevia.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "UBICACIO")
@Getter @Setter @NoArgsConstructor @ToString
/**
 * Una ubicació és el lloc físic on s'atendrà la cita
 */
public class Ubicacio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ubicacio_seq")
	@SequenceGenerator(name = "ubicacio_seq", sequenceName = "UBICACIO_SEQ", allocationSize = 1)
	/**
	 * Codi numèrica
	 */
	private Long con;
	
	@Column(length = 50)
	/**
	 * Nom del centre (ubicació)
	 */
	private String nom;
	
	@Column(length = 100)
	/**
	 * Nom del carrer
	 */
	private String nomcar;
	
	@Column(length = 300)
	/**
	 * Observacions
	 */
	private String obs;
	@ManyToOne(fetch=FetchType.LAZY) 
	@JoinColumn(name="SUBAPL_COA")
	/**
	 * Subaplicació assignada al centre
	 */
	private Subaplicacio subapl;

}
