package uoc.edu.citaprevia.api.jpa;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import uoc.edu.citaprevia.api.model.TipusCita;

public interface TipusCitaRepository extends PagingAndSortingRepository<TipusCita, Long>{
	
	List<TipusCita> findBySubaplicacioCoa(String coa);


}
