package uoc.edu.citaprevia.api.model;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter @NoArgsConstructor
public class SetmanaTipusId implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="HORCIT_CON")
	/**
	 * Horari a la qual pertany la franja horària
	 */
	private Horari horari;
	 
	@Column(name = "DIASET_CON")
	/**
	 * Codi numèric del dia de la setmana (cita)
	 */
	private Long diasetCon;
	
	@Column(name = "horini", columnDefinition = "TIME", nullable = false)
	@JsonFormat(pattern = "HH:mm")
	/**
	 * Hora inici de la franja horària (cita)
	 */
    private LocalTime horini;

    @Column(name = "horfin", columnDefinition = "TIME", nullable = false)
    @JsonFormat(pattern = "HH:mm")
    /**
     * Hora fila de la franja horària (cita)
     */
    private LocalTime horfin;

}
