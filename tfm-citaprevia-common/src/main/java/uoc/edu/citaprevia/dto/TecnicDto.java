package uoc.edu.citaprevia.dto;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.dto.generic.ErrorsDto;
import uoc.edu.citaprevia.model.Perfil;
import uoc.edu.citaprevia.model.SiNo;

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class TecnicDto extends ErrorsDto{
	
    private String coa;
    private String pass;
	private String nom;
	private String ll1;
	private String ll2;
	private SiNo notval;
	private Perfil prf;

}
