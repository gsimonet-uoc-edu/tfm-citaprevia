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

}
