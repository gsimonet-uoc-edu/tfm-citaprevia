package uoc.edu.citaprevia.api.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.querydsl.core.annotations.QueryInit;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "SETMANA_TIPUS")
@Getter @Setter @NoArgsConstructor @ToString
/**
 * Una Setmana Tipus és una franja horària que indica el dia de la setmana i la hora inici i final de la cita
 */
public class SetmanaTipus {
	
	@Id
	@Embedded
	@QueryInit("horari.subapl.id")
	private SetmanaTipusId id;
}
