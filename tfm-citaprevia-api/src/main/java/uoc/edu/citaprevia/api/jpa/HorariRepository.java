package uoc.edu.citaprevia.api.jpa;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import uoc.edu.citaprevia.api.model.Horari;

public interface HorariRepository extends PagingAndSortingRepository<Horari, Long>{
	
	List<Horari> findBySubaplCoa(String subapl);
	Horari findByCon(Long con);
	List<Horari> findByTipusCitaCon (Long tipcitCon);
}
