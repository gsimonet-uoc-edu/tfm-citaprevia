package uoc.edu.citaprevia.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SUBAPLICACIO")
@Getter @Setter @NoArgsConstructor @ToString
/**
 * Una subaplicació indica l'organisme o empresa que té implantat el sistema de Cita Prèvia online.
 */
public class Subaplicacio {
	
	@Id
	@Size(max=3)
	private String coa;
	
	@NotNull
	@Size(max=100)
	private String dec;
	
	@Size(max=240)
	private String dem;

}
