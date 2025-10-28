package uoc.edu.citaprevia.dto;

import java.time.LocalDate;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.dto.generic.ErrorsDto;
import uoc.edu.citaprevia.model.SiNo;

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class AgendaDto extends ErrorsDto{
	
	private Long con;
	private LocalDate datini;
	private LocalDate datfin;
	private UbicacioDto centre;
	private TecnicDto tecnic;
	private HorariDto horari;
	private SiNo gespri;
	private String usucre;
	private String usumod;
	private Date datcre;
	private Date datmod;
	private Long seqmod;

}
