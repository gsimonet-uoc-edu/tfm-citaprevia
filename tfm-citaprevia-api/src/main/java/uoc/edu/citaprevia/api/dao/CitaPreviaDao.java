package uoc.edu.citaprevia.api.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uoc.edu.citaprevia.api.jpa.CitaRepository;
import uoc.edu.citaprevia.api.model.Cita;

@Component
public class CitaPreviaDao {
	
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

}
