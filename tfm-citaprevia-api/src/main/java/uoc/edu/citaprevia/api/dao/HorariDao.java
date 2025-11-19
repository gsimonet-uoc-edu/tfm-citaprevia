package uoc.edu.citaprevia.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uoc.edu.citaprevia.api.jpa.HorariRepository;
import uoc.edu.citaprevia.api.model.Horari;

@Component
public class HorariDao {
	
	@Autowired
	private HorariRepository horariRepository;
	
	public List<Horari> findHorarisBySubaplicacio (String subaplCoa) {
		return horariRepository.findBySubaplCoa(subaplCoa);
	}
	
	public List<Horari> findHorarisByTipusCita (Long tipcitCon) {
		return horariRepository.findByTipusCitaCon(tipcitCon);
	}
	
	public Horari findHorariByCon (Long con) {
		return horariRepository.findByCon(con);
	}
	
	public Horari saveHorari(Horari entity) {
		return (entity != null ? horariRepository.save(entity) : null);
	}
	
	public Horari updateHorari(Horari entity) {	
		Horari dao = this.findHorariByCon(entity.getCon());
		return (dao != null) ? horariRepository.save(entity) : null;
	}
	
	public void deleteHorari(Horari entity) {
		horariRepository.delete(entity);
	}
}
