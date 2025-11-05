package uoc.edu.citaprevia.api.dao;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uoc.edu.citaprevia.api.jpa.CitaRepository;
import uoc.edu.citaprevia.api.model.Cita;

@Component
public class CitaDao {
	
	@Autowired
	private CitaRepository citaRepository;
	
	public Cita findCitaByCon (Long con) {
		return citaRepository.findByCon(con);
	}
	
	public Cita saveCita(Cita entity) {
		return (entity != null ? citaRepository.save(entity) : null);
	}
	
	public Cita updateCita(Cita entity) {	
		Cita dao = this.findCitaByCon(entity.getCon());
		return (dao != null) ? citaRepository.save(entity) : null;
	}
	
	public void deleteCita (Cita entity) {
		citaRepository.delete(entity);
	}
	
	public boolean existsByAgendaConAndDathorini(Long con, LocalDateTime current) {
		return citaRepository.existsByAgendaConAndDathorini(con, current);
	}
	
	public boolean existeixCita(Long agendaCon, LocalDateTime fi, LocalDateTime inici) {
		return citaRepository.existsByAgendaConAndDathoriniLessThanEqualAndDathorfinGreaterThanEqual(agendaCon, fi, inici);
	}
	
	public Cita existeixCitaAgenda (Long ageCon, LocalDateTime inici, LocalDateTime fi, Long tipcitCon) {
		return citaRepository.findByAgendaConAndDathoriniAndDathorfinAndTipcitCon(ageCon, inici, fi, tipcitCon);
	}

}
