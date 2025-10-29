package uoc.edu.citaprevia.api.jpa;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import uoc.edu.citaprevia.api.model.Agenda;

public interface AgendaRepository extends PagingAndSortingRepository<Agenda, Long>{
	
	List<Agenda> findByHorariTipusCitaConAndHorariSubaplCoa (Long tipCitCon, String subaplCoa);
	List<Agenda> findByHorariTipusCitaConAndHorariSubaplCoaAndDatiniLessThanEqualAndDatfinGreaterThanEqual (Long tipCitCon, String subaplCoa, LocalDate ultimDia, LocalDate primerDia);

}
