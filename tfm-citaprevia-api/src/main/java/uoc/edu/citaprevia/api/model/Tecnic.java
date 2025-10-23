package uoc.edu.citaprevia.api.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.model.SiNo;

@Entity
@Table(name = "TECNIC")
@Getter @Setter @NoArgsConstructor @ToString
public class Tecnic {
	
	@Id
	@Size(max=3)
    private String coa;
	private String nom;
	private String ll1;
	private String ll2;
	@Enumerated(EnumType.STRING)
	private SiNo notval;

}
