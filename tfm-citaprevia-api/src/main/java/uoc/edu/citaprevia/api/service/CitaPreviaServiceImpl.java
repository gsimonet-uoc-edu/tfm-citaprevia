package uoc.edu.citaprevia.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.CitaPreviaDao;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.util.Utils;


@Service("citaPreviaService")
public class CitaPreviaServiceImpl implements CitaPreviaService{
	
	@Autowired
	private CitaPreviaDao citaPreviaDao;
	
	@Override
	public CitaDto getCita (Long con) {
		
		CitaDto dto = new CitaDto();
		
		if (!Utils.isEmpty(con)) {
			dto = Converter.toDto(citaPreviaDao.findCitaByCon(con));
		}
		return dto; 
	}

}
