package uoc.edu.citaprevia.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.model.SiNo;

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class HorariDto {
	
	private Long con;
	private String dec;
	private String dem;
	private SiNo notval;
	private SubaplicacioDto subapl;
	private TipusCitaDto tipusCita;
	//@OneToMany(fetch=FetchType.LAZY, mappedBy = "id.horari")
	//private List<SetmanaTipus> setmanesTipus;
	

}
