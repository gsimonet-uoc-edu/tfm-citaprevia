package uoc.edu.citaprevia.api.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.querydsl.core.annotations.QueryInit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "SETMANA_TIPUS")
@Getter @Setter @NoArgsConstructor @ToString
public class SetmanaTipus {
	
	@Id
	@Embedded
	@QueryInit("horari.subapl.id")
	private SetmanaTipusId id;

	@ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="TIPCIT_CON")
	private TipusCita tipcit;

}
