package uoc.edu.citaprevia.api.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import uoc.edu.citaprevia.api.model.Cita;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    Cita findByCon(Long con);
}
