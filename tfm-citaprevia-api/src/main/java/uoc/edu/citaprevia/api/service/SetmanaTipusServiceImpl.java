package uoc.edu.citaprevia.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.SetmanaTipusDao;
import uoc.edu.citaprevia.api.model.SetmanaTipus;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.SetmanaTipusDto;
import uoc.edu.citaprevia.util.Utils;

@Service("setmanaTipusService")
public class SetmanaTipusServiceImpl implements SetmanaTipusService{
	
	@Autowired
	private SetmanaTipusDao setmanaTipusDao;
	
	@Override
	public List<SetmanaTipusDto> getSetmanaTipusByHorari(Long horCon, Locale locale) {
		List<SetmanaTipusDto> dtos = new ArrayList<>();
		
		if (!Utils.isEmpty(horCon)) {
			List<SetmanaTipus> llista = setmanaTipusDao.findSetmanaTipusByHorari(horCon);
			llista.forEach(item->dtos.add(Converter.toDto(item)));
		}	
		return dtos;
	}

}
