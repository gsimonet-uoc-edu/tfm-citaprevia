package uoc.edu.citaprevia.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.dto.generic.ErrorsDto;

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class AgendaDto extends ErrorsDto{
	
	private Long con;
	@NotNull
	private LocalDate datini;
	@NotNull
	private LocalDate datfin;
	@NotNull
	private UbicacioDto centre;
	@NotNull
	private TecnicDto tecnic;
	@NotNull
	private HorariDto horari;

}
