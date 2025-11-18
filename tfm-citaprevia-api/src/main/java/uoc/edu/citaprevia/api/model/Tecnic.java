package uoc.edu.citaprevia.api.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TECNIC")
@Getter @Setter @NoArgsConstructor @ToString
public class Tecnic {
	
	@Id
    private String coa;
	private String pass;
	private String nom;
	private String ll1;
	private String ll2;
    private String prf;

}
