package uoc.edu.citaprevia.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.AgendaDao;
import uoc.edu.citaprevia.api.model.Agenda;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.util.Utils;

@Service("agendaService")
public class AgendaServiceImpl implements AgendaService{
	
	@Autowired
	private AgendaDao agendaDao;
	
	@Override
	public List<AgendaDto> getAgendesByTipusCitaAndSubaplicacio (Long tipCitCon, String subaplCoa, Locale locale) {
		
		List<AgendaDto> dtos = new ArrayList<AgendaDto>();
		
		if (!Utils.isEmpty(tipCitCon) && !Utils.isEmpty(subaplCoa)) {
			List<Agenda> daos = agendaDao.findAgendesByTipusCitaAndSubaplicacio(tipCitCon, subaplCoa);	
			daos.forEach(item->dtos.add(Converter.toDto(item)));
		}
		
		return dtos;
	}

}
