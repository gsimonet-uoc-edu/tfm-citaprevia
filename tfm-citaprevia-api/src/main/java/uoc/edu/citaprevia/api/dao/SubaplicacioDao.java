package uoc.edu.citaprevia.api.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uoc.edu.citaprevia.api.jpa.SubaplicacioRepository;
import uoc.edu.citaprevia.api.model.Subaplicacio;

@Component
public class SubaplicacioDao {
	
	@Autowired
	private SubaplicacioRepository subaplicacioRepository;
	
	public Subaplicacio findSubaplicacioByCoa (String coa) {
		return subaplicacioRepository.findByCoa(coa);
	}

}
