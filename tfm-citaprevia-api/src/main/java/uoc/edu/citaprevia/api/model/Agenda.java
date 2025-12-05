package uoc.edu.citaprevia.api.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.querydsl.core.annotations.QueryInit;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "AGENDA")
@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
/**
 * Una agenda es un rang de dates on es poden planificar cites
 */
public class Agenda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agenda_seq")
	@SequenceGenerator(name = "agenda_seq", sequenceName = "AGENDA_SEQ", allocationSize = 1)
	private Long con;
	private LocalDate datini;
	private LocalDate datfin;
	@ManyToOne
	@JoinColumn(name = "UBI_CON")
	private Ubicacio centre;
	@ManyToOne
	@JoinColumn(name = "TEC_COA")
	private Tecnic tecnic;
	@ManyToOne
	@JoinColumn(name = "HORCIT_CON")
	@QueryInit({"subapl.id","tipusCita"})
	private Horari horari;

}
