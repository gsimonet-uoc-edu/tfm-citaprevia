package uoc.edu.citaprevia.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uoc.edu.citaprevia.api.jpa.UbicacioRepository;
import uoc.edu.citaprevia.api.model.Ubicacio;

@Component
public class UbicacioDao {
	
	@Autowired
	private UbicacioRepository ubicacioRepository;
	
	public Ubicacio findUbicacioByCon (Long con) {
		return ubicacioRepository.findByCon(con);
	}
	
	public List<Ubicacio> findUbicacions () {
		return ubicacioRepository.findAll();
	}
	
	public List<Ubicacio> findUbicacionsBySubaplicacio (String subaplCoa) {
		return ubicacioRepository.findBySubaplCoa(subaplCoa);
	}
	
	public Ubicacio saveUbicacio(Ubicacio entity) {
		return (entity != null ? ubicacioRepository.save(entity) : null);
	}
	
	public Ubicacio updateUbicacio(Ubicacio entity) {	
		Ubicacio dao = this.findUbicacioByCon(entity.getCon());
		return (dao != null) ? ubicacioRepository.save(entity) : null;
	}
	
	public void deleteUbicacio (Ubicacio entity) {
		ubicacioRepository.delete(entity);
	}

}
