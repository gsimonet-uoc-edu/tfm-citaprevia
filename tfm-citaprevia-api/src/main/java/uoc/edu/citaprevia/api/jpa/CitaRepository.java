package uoc.edu.citaprevia.api.jpa;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import uoc.edu.citaprevia.api.model.Cita;

public interface CitaRepository extends PagingAndSortingRepository<Cita, Long> {
	
    Cita findByCon(Long con);

    Cita findByAgendaConAndDathoriniAndDathorfinAndTipcitCon(Long ageCon, LocalDateTime inici, LocalDateTime fi, Long tipcitCon);
       
	boolean existsByAgendaConAndDathorini(Long con, LocalDateTime current);
	
	boolean existsByAgendaConAndDathoriniLessThanEqualAndDathorfinGreaterThanEqual(
	        Long agendaCon, LocalDateTime fi, LocalDateTime inici);
	
	List<Cita> findByAgendaCon(Long ageCon);
}
