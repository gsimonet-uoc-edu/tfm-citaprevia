package uoc.edu.citaprevia.api.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
import uoc.edu.citaprevia.model.SiNo;

@Entity
@Table(name = "HORARI")
@Getter @Setter @NoArgsConstructor @ToString
public class Horari {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "horari_seq")
	@SequenceGenerator(name = "horari_seq", sequenceName = "HORARI_SEQ", allocationSize = 1)
	private Long con;
	private String dec;
	private String dem;
	@ManyToOne(fetch=FetchType.LAZY) 
	@JoinColumn(name="SUBAPL_COA")
	private Subaplicacio subapl;
	@ManyToOne(fetch=FetchType.LAZY) 
	@JoinColumn(name = "TIPCIT_CON")
	private TipusCita tipusCita;

}
