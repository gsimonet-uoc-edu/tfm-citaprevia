package uoc.edu.citaprevia.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uoc.edu.citaprevia.api.jpa.SetmanaTipusRepository;
import uoc.edu.citaprevia.api.model.SetmanaTipus;

@Component
public class SetmanaTipusDao {
	
	@Autowired
	private SetmanaTipusRepository setmanaTipusRepository;
	
	public List<SetmanaTipus> findByIdHorariConAndIdDiasetCon (Long horCon, Long diasetCon){
		return setmanaTipusRepository.findByIdHorariConAndIdDiasetCon(horCon, diasetCon);
	}

}
