package uoc.edu.citaprevia.api.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import uoc.edu.citaprevia.api.model.Subaplicacio;

public interface SubaplicacioRepository extends PagingAndSortingRepository<Subaplicacio, String> {
	
	Subaplicacio findByCoa(String coa);

}
