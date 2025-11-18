package uoc.edu.citaprevia.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uoc.edu.citaprevia.api.jpa.TecnicRepository;
import uoc.edu.citaprevia.api.model.Horari;
import uoc.edu.citaprevia.api.model.Tecnic;
import uoc.edu.citaprevia.api.model.TipusCita;

@Component
public class TecnicDao {
	
	@Autowired
	private TecnicRepository tecnicRepository;
	
	public Tecnic findTecnicByCoa (String coa) {
		return tecnicRepository.findByCoa(coa);
	}
	
	public List<Tecnic> findTecnicsBySubaplicacio(String subaplCoa) {	
		return tecnicRepository.findByPrfEndingWith(subaplCoa);

	}
	
	public Tecnic saveTecnic(Tecnic entity) {
		return (entity != null ? tecnicRepository.save(entity) : null);
	}
	
	public Tecnic updateTecnic(Tecnic entity) {	
		Tecnic dao = this.findTecnicByCoa(entity.getCoa());
		return (dao != null) ? tecnicRepository.save(entity) : null;
	}
	
	public void deleteTecnic(Tecnic entity) {
		tecnicRepository.delete(entity);
	}

}
