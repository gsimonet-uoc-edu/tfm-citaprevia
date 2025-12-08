package uoc.edu.citaprevia.api.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CITA")
@Getter @Setter @NoArgsConstructor @ToString
/**
 * Una cita és la unitat bàsica amb un rang d'hores que conté les dades del interessat
 */
public class Cita {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cita_seq")
	@SequenceGenerator(name = "cita_seq", sequenceName = "CITA_SEQ", allocationSize = 1)
	/**
	 * Codi numèric
	 */
	private Long con;
	/**
	 * Data i hora inici de la cita
	 */
	private LocalDateTime dathorini;
	/**
	 * Data i hora final de la cita
	 */
	private LocalDateTime dathorfin;
	@Size(max=2000)
	/**
	 * Observacions
	 */
	private String obs;
	/**
	 * Nom de l'interessat
	 */
	private String nom;
	/**
	 * Llinatges de l'interessat
	 */
	private String llis;
	/**
	 * Número de document de l'interessa
	 */
	private String numdoc;
	@ManyToOne
	@JoinColumn(name = "TIPCIT_CON")
	@NotNull
	/**
	 * Tipus de cita
	 */
	private TipusCita tipcit;
	@ManyToOne
	@JoinColumn(name = "AGE_CON")
	@NotNull
	/**
	 * Agenda a la qual pertany la cita
	 */
	private Agenda agenda;	
	@Size(max = 250)
	/**
	 * Camp dinàmic Literal 1
	 */
	private String lit1er;
	@Size(max = 250)
	/**
	 * Camp dinàmic Literal 2
	 */
	private String lit2on;
	@Size(max = 250)
	/**
	 * Camp dinàmic Literal 3
	 */
	private String lit3er;
	@Size(max = 250)
	/**
	 * Camp dinàmic Literal 4
	 */
	private String lit4rt;
	@Size(max = 250)
	/**
	 * Camp dinàmic Literal 5
	 */
	private String lit05e;
	@Size(max = 250)
	/**
	 * Camp dinàmic Literal 6
	 */
	private String lit06e;
	@Size(max = 250)
	/**
	 * Camp dinàmic Literal 7
	 */
	private String lit07e;
	@Size(max = 250)
	/**
	 * Camp dinàmic Literal 8
	 */
	private String lit08e;
	@Size(max = 250)
	/**
	 * Camp dinàmic Literal 9
	 */
	private String lit09e;
	@Size(max = 250)
	/**
	 * Camp dinàmic Literal 10
	 */
	private String lit10e;	
	@Size(max = 70)
	/**
	 * Nom del carrer de l'interessat
	 */
	private String nomcar;
	@Min(0)
	/**
	 * Número de telèfon de l'interessat
	 */
	private Long tel;
	@Size(max = 240)
	/*
	 * Correu electrònic de l'interessat
	 */
	private String ema;
}
