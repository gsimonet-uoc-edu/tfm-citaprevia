package uoc.edu.citaprevia.api.jpa;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import uoc.edu.citaprevia.api.model.Agenda;

public interface AgendaRepository extends PagingAndSortingRepository<Agenda, Long>{
	
	List<Agenda> findByHorariTipusCitaConAndHorariSubaplCoa (Long tipCitCon, String subaplCoa);
	Agenda findByCon(Long con);
	List<Agenda> findByHorariSubaplCoa(String subaplCoa);
	List<Agenda> findByTecnicCoa(String tecCoa);
	List<Agenda> findByCentreCon(Long ubiCon);
}
