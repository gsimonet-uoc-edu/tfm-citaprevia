package uoc.edu.citaprevia.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.SubaplicacioDao;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.SubaplicacioDto;
import uoc.edu.citaprevia.util.Utils;

@Service("subaplicacioPreviaService")
public class SubaplicacioServiceImpl implements SubaplicacioService{
	
	@Autowired
	private SubaplicacioDao subaplicacioDao;
	
	@Override
	public SubaplicacioDto getSubaplicacioByCoa (String coa) {
		SubaplicacioDto dto = new SubaplicacioDto();
		if (!Utils.isEmpty(coa)) {
			dto = Converter.toDto(subaplicacioDao.findSubaplicacioByCoa(coa));
		}
		return dto;
	}

}
