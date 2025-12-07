package uoc.edu.citaprevia.api.dao;

import java.time.LocalDate;
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
	
	public List<Agenda> findAgendesObertesByTipusCitaAndSubaplicacio (Long tipCitCon, String subaplCoa) {
		return agendaRepository.findByHorariTipusCitaConAndHorariSubaplCoaAndDatfinGreaterThanEqual(tipCitCon, subaplCoa, LocalDate.now());
	}
	
	
	public Agenda findAgendaByCon (Long con) {
		return agendaRepository.findByCon(con);
	}

    public List<Agenda> findAgendesBySubaplicacio(String subaplCoa) {
        return agendaRepository.findByHorariSubaplCoa(subaplCoa);
    }
    

    public List<Agenda> findAgendesByHorari(Long horCon) {
        return agendaRepository.findByHorariCon(horCon);
    }
    
    public List<Agenda> findAgendesByTecnicCoa(String tecCoa) {
        return agendaRepository.findByTecnicCoa(tecCoa);
    }
    
	public List<Agenda> findAgendesByUbicacio (Long ubiCon) {
		return agendaRepository.findByCentreCon(ubiCon);
	}
    
	public Agenda saveAgenda(Agenda entity) {
		return (entity != null ? agendaRepository.save(entity) : null);
	}
	
	public Agenda updateAgenda(Agenda entity) {	
		Agenda dao = this.findAgendaByCon(entity.getCon());
		return (dao != null) ? agendaRepository.save(entity) : null;
	}
	
	public void deleteAgenda (Agenda entity) {
		agendaRepository.delete(entity);
	}
    
}
