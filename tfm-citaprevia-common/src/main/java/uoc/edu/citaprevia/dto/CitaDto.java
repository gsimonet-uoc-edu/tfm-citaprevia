package uoc.edu.citaprevia.dto;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.dto.generic.ErrorsDto;

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class CitaDto extends ErrorsDto{
	
	private Long con;
	private LocalDateTime dathorini;
	private LocalDateTime dathorfin;
	private String obs;
	private String nom;
	private String lli;
	private String nomcar;
	private Long tel;
	private String ema;
	private String numdoc;
	private String lit1er;
	private String lit2on;
	private String lit3er;
	private String lit4rt;
	private String lit05e;
	private String lit06e;
	private String lit07e;
	private String lit08e;
	private String lit09e;
	private String lit10e;
	private AgendaDto agenda;
	private TipusCitaDto tipusCita;
}
