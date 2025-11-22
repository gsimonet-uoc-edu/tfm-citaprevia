package uoc.edu.citaprevia.api.model;

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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.model.ModalitatTipusCita;

@Entity
@Table(name = "TIPUS_CITA")
@Getter @Setter @NoArgsConstructor @ToString
public class TipusCita {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipus_cita_seq")
	@SequenceGenerator(name = "tipus_cita_seq", sequenceName = "TIPUS_CITA_SEQ", allocationSize = 1)
	private Long con;
	private String dec;
	private String dem;
	@Enumerated(EnumType.STRING)
	private ModalitatTipusCita tipcitmod;
	@ManyToOne 
	@JoinColumn(name="SUBAPL_COA")
	private Subaplicacio subaplicacio;
	
}
