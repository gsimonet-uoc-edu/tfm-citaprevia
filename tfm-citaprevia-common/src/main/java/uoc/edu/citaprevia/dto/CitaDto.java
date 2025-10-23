package uoc.edu.citaprevia.dto;

import java.time.LocalDateTime;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
public class CitaDto {
	
	private Long con;
	private LocalDateTime dathorini;
	private LocalDateTime dathorfin;
	private String obs;

}
