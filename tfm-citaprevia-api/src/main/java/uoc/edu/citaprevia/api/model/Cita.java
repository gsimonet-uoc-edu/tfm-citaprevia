package uoc.edu.citaprevia.api.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
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
	/*@ManyToOne
	@JoinColumn(name = "TIPCIT_CON")
	@NotNull
	private GenTipcit tipcit;
	@ManyToOne
	@JoinColumn(name = "AGE_CON")
	@NotNull
	private GenAgenda agenda;
	private Long cap;
	@Enumerated(EnumType.STRING)
	private SiNo  dip;
	@Column(name="IDI_CON")
	private Long idiCon;
	@Column(name="WEBUSUMOD_COA")
	private String webusumodCoa;
	@Column(name="UBIMOD_CON")
	private Long ubimodCon;
	
	@Length(max = 250)
	private String lit1er;
	@Length(max = 250)
	private String lit2on;
	@Length(max = 250)
	private String lit3er;
	@Length(max = 250)
	private String lit4rt;
	@Length(max = 250)
	private String lit05e;
	@Length(max = 250)
	private String lit06e;
	@Length(max = 250)
	private String lit07e;
	@Length(max = 250)
	private String lit08e;
	@Length(max = 250)
	private String lit09e;
	@Length(max = 250)
	private String lit10e;	
		
	@NotNull
	@Enumerated(EnumType.STRING)
	private SiNo  asi;
	private Date datblo;
	
	
	@Length(max = 70)
	private String nomcar;
	@Min(0)
	@Max(999999999)
	private Long tel;
	@Length(max = 240)
	private String ema;
	@Length(max = 20000)
	private String urlreu;

	@Column(name="UBICRE_CON")
	private Long ubicreCon;
	@Column(name="WEBUSUCRE_COA")
	private String webusucreCoa;
	
	private Long seqmod;
	
	@ManyToOne
	@JoinColumn(name = "MBC_CON")	
	private GenMotblocit motiuBloqueig;*/
}
