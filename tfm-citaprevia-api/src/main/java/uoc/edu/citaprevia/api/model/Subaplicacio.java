package uoc.edu.citaprevia.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "SUBAPLICACIO")
public class Subaplicacio {
	
	@Id
	@Size(max=3)
	private String coa;
	
	@NotNull
	@Size(max=15)
	private String dec;
	
	@Size(max=240)
	private String dem;

}
