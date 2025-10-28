package uoc.edu.citaprevia.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uoc.edu.citaprevia.api.jpa.AgendaRepository;
import uoc.edu.citaprevia.api.model.Agenda;

@Component
public class AgendaDao {
	
	@Autowired
	private AgendaRepository agendaRepository;
	
	
	public List<Agenda> findAgendesByTipusCitaAndSubaplicacio (Long tipCitCon, String subaplCoa) {
		return agendaRepository.findByHorariTipusCitaConAndHorariSubaplCoa(tipCitCon, subaplCoa);
	}

}
