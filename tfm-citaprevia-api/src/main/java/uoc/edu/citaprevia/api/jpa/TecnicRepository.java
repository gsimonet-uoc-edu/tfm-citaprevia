package uoc.edu.citaprevia.api.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import uoc.edu.citaprevia.api.model.Tecnic;

public interface TecnicRepository extends PagingAndSortingRepository<Tecnic, String> {

	Tecnic findByCoa (String coa);
}
