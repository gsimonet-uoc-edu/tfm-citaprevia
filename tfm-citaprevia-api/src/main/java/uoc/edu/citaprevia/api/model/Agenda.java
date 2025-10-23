package uoc.edu.citaprevia.api.model;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.querydsl.core.annotations.QueryInit;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.model.SiNo;

@Entity
@Table(name = "AGENDA")
@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class Agenda {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
	// mirar  http://www.querydsl.com/static/querydsl/4.1.3/reference/html_single/#d0e2260
	private Horari horari;
	@Enumerated(EnumType.STRING)
	private SiNo gespri;
	private String usucre;
	private String usumod;
	private Date datcre;
	private Date datmod;
	private Long seqmod;

}
