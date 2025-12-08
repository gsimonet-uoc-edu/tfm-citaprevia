package uoc.edu.citaprevia.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import uoc.edu.citaprevia.model.ModalitatTipusCita;

@Entity
@Table(name = "TIPUS_CITA")
@Getter @Setter @NoArgsConstructor @ToString
/**
 * Un tipus de cita és la tipologia d'una cita
 */
public class TipusCita {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipus_cita_seq")
	@SequenceGenerator(name = "tipus_cita_seq", sequenceName = "TIPUS_CITA_SEQ", allocationSize = 1)
	/**
	 * Codi numèric
	 */
	private Long con;
	
	@Column(length = 50)
	/**
	 * Descripció curta
	 */
	private String dec;
	
	@Column(length = 100)
	/**
	 * Descripció mitjana
	 */
	private String dem;
	
	@Enumerated(EnumType.STRING)
	/**
	 * Modalitat en que es realitzarà la cita
	 */
	private ModalitatTipusCita tipcitmod;
	
	@ManyToOne 
	@JoinColumn(name="SUBAPL_COA")
	/**
	 * Subaplicació a la qual pertany el tipus de cita
	 */
	private Subaplicacio subaplicacio;
	
}
