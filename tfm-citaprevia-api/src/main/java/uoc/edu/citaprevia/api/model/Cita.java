package uoc.edu.citaprevia.api.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long con;
	@NotNull
	private LocalDateTime dathorini;
	@NotNull
	private LocalDateTime dathorfin;
	@Size(max=2000)
	private String obs;
	@ManyToOne
	@JoinColumn(name = "TIPCIT_CON")
	@NotNull
	private TipusCita tipcit;
	@ManyToOne
	@JoinColumn(name = "AGE_CON")
	@NotNull
	private Agenda agenda;
	//@Column(name="WEBUSUMOD_COA")
	//private String webusumodCoa;
	//@Column(name="UBIMOD_CON")
	//private Long ubimodCon;	
	@Size(max = 250)
	private String lit1er;
	@Size(max = 250)
	private String lit2on;
	@Size(max = 250)
	private String lit3er;
	@Size(max = 250)
	private String lit4rt;
	@Size(max = 250)
	private String lit05e;
	@Size(max = 250)
	private String lit06e;
	@Size(max = 250)
	private String lit07e;
	@Size(max = 250)
	private String lit08e;
	@Size(max = 250)
	private String lit09e;
	@Size(max = 250)
	private String lit10e;	
	@Size(max = 70)
	private String nomcar;
	@Min(0)
	@Max(999999999)
	private Long tel;
	@Size(max = 240)
	private String ema;
	/*@Length(max = 20000)
	private String urlreu;*/
/*
	@Column(name="UBICRE_CON")
	private Long ubicreCon;
	@Column(name="WEBUSUCRE_COA")
	private String webusucreCoa;	
	private Long seqmod;
*/
}
