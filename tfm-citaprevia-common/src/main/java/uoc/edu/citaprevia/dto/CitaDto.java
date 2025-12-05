package uoc.edu.citaprevia.dto;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uoc.edu.citaprevia.dto.generic.ErrorsDto;

@Getter @Setter @NoArgsConstructor @ToString(callSuper = true) @EqualsAndHashCode
public class CitaDto extends ErrorsDto{
	
	private Long con;
	private LocalDateTime dathorini;
	private LocalDateTime dathorfin;
	private String obs;
	private String nom;
	private String llis;
	private String nomcar;
	private Long tel;
	private String ema;
	private String numdoc;
	// Camps dinàmics
    private String lit1;
    private String lit2;
    private String lit3;
    private String lit4;
    private String lit5;
    private String lit6;
    private String lit7;
    private String lit8;
    private String lit9;
    private String lit10;
	private AgendaDto agenda;
	private TipusCitaDto tipusCita;
}
