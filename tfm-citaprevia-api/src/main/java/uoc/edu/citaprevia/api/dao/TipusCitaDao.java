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
	public TipusCita findTipusCitaByCon(Long con) {
		return tipusCitaRepository.findByCon(con);
	}
	
	public TipusCita saveTipusCita(TipusCita entity) {
		return (entity != null ? tipusCitaRepository.save(entity) : null);
	}
	
	public TipusCita updateTipusCita(TipusCita entity) {	
		TipusCita dao = this.findTipusCitaByCon(entity.getCon());
		return (dao != null) ? tipusCitaRepository.save(entity) : null;
	}
	
	public void deleteTipusCita (TipusCita entity) {
		tipusCitaRepository.delete(entity);
	}

}
