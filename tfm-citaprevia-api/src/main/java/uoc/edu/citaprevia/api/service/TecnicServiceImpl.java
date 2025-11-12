package uoc.edu.citaprevia.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.TecnicDao;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.util.Utils;

@Service("tecnicService")
public class TecnicServiceImpl implements TecnicService{
	
	@Autowired
	private TecnicDao tecnicDao;
	
	@Override
	public TecnicDto getTecnicByCoa (String coa) {
		TecnicDto dto = new TecnicDto();
		if (!Utils.isEmpty(coa)) {
			dto = Converter.toDto(tecnicDao.findTecnicByCoa(coa));
		}
		return dto;
	}

}
