package uoc.edu.citaprevia.api.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "UBICACIO")
@Getter @Setter @NoArgsConstructor @ToString
public class Ubicacio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ubicacio_seq")
	@SequenceGenerator(name = "ubicacio_seq", sequenceName = "UBICACIO_SEQ", allocationSize = 1)
	private Long con;	
	private String nom;
	private String obs;

}
