package uoc.edu.citaprevia.api.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import uoc.edu.citaprevia.api.model.Cita;

public interface CitaRepository extends PagingAndSortingRepository<Cita, Long> {
    Cita findByCon(Long con);
}
