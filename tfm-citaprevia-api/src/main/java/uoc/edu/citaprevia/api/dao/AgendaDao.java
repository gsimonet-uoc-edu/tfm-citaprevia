package uoc.edu.citaprevia.api.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uoc.edu.citaprevia.api.jpa.AgendaRepository;
import uoc.edu.citaprevia.api.model.Agenda;
import uoc.edu.citaprevia.api.model.Cita;

@Component
public class AgendaDao {
	
	@Autowired
	private AgendaRepository agendaRepository;
	
	
	public List<Agenda> findAgendesByTipusCitaAndSubaplicacio (Long tipCitCon, String subaplCoa) {
		return agendaRepository.findByHorariTipusCitaConAndHorariSubaplCoa(tipCitCon, subaplCoa);
	}
	
	public List<Agenda> findByHorariTipusCitaConAndHorariSubaplCoaAndDatiniLessThanEqualAndDatfinGreaterThanEqual (Long tipCitCon, String subaplCoa, LocalDate ultimDia, LocalDate primerDia) {
		return agendaRepository.findByHorariTipusCitaConAndHorariSubaplCoaAndDatiniLessThanEqualAndDatfinGreaterThanEqual(tipCitCon, subaplCoa, ultimDia, primerDia);
	}
	
	public Agenda findAgendaByCon (Long con) {
		return agendaRepository.findByCon(con);
	}

    public List<Agenda> findAgendesBySubaplicacio(String subaplCoa) {
        return agendaRepository.findByHorariSubaplCoa(subaplCoa);
    }
    
    public List<Agenda> findAgendesByTecnicCoa(String tecCoa) {
        return agendaRepository.findByTecnicCoa(tecCoa);
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
