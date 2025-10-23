package uoc.edu.citaprevia.api.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter @NoArgsConstructor
public class SetmanaTipusId implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="HORCIT_CON")
	private Horari horari;
	 
	@Column(name = "DIASET_CON")
	private Long diasetCon;
	
	private LocalDateTime horini;
	
	private LocalDateTime horfin;

}
