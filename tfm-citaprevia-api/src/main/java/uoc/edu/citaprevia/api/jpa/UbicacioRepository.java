package uoc.edu.citaprevia.api.jpa;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import uoc.edu.citaprevia.api.model.Ubicacio;

public interface UbicacioRepository extends PagingAndSortingRepository<Ubicacio, Long> {
	
	List<Ubicacio> findAll();

}
