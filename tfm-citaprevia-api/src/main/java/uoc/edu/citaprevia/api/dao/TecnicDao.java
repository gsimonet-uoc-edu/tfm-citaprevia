package uoc.edu.citaprevia.api.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uoc.edu.citaprevia.api.jpa.TecnicRepository;
import uoc.edu.citaprevia.api.model.Tecnic;

@Component
public class TecnicDao {
	
	@Autowired
	private TecnicRepository tecnicRepository;
	
	public Tecnic findTecnicByCoa (String coa) {
		return tecnicRepository.findByCoa(coa);
	}

}
