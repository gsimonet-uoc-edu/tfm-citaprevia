package uoc.edu.citaprevia.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uoc.edu.citaprevia.api.jpa.TipusCitaRepository;
import uoc.edu.citaprevia.api.model.TipusCita;

@Component
public class TipusCitaDao {
	
	@Autowired
	private TipusCitaRepository tipusCitaRepository;
	
	public List<TipusCita> getAllTipusCitaBySubapl(String coa) {	
		return tipusCitaRepository.findBySubaplicacioCoa(coa);

	}

}
