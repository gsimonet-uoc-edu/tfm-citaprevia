package uoc.edu.citaprevia.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uoc.edu.citaprevia.api.jpa.SetmanaTipusRepository;
import uoc.edu.citaprevia.api.model.SetmanaTipus;
import uoc.edu.citaprevia.api.model.SetmanaTipusId;

@Component
public class SetmanaTipusDao {
	
	@Autowired
	private SetmanaTipusRepository setmanaTipusRepository;
	
	public List<SetmanaTipus> findByIdHorariConAndIdDiasetCon (Long horCon, Long diasetCon){
		return setmanaTipusRepository.findByIdHorariConAndIdDiasetCon(horCon, diasetCon);
	}

	public List<SetmanaTipus> findSetmanaTipusByHorari (Long horCon){
		return setmanaTipusRepository.findByIdHorariCon(horCon);
	}

	public SetmanaTipus saveSetmanaTipus(SetmanaTipus entity) {
		return (entity != null ? setmanaTipusRepository.save(entity) : null);
	}
	
	public SetmanaTipus findSetmanaTipusById (SetmanaTipusId id) {
		SetmanaTipus dao = null;
		if (id != null && id.getHorari() != null) {
			dao = setmanaTipusRepository.findByIdHorariConAndIdDiasetConAndIdHoriniAndIdHorfin(id.getHorari().getCon(), id.getDiasetCon(), id.getHorini(), id.getHorfin());
		}
		return dao;
	}
	
	public SetmanaTipus updateHorari(SetmanaTipus entity) {	
		SetmanaTipus dao = this.findSetmanaTipusById(entity.getId());
		return (dao != null) ? setmanaTipusRepository.save(entity) : null;
	}
	
	public void deleteSetmanaTipus(SetmanaTipus entity) {
		setmanaTipusRepository.delete(entity);
	}
	
}
