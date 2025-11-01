package uoc.edu.citaprevia.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.TipusCitaDao;
import uoc.edu.citaprevia.api.model.TipusCita;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.TipusCitaDto;
import uoc.edu.citaprevia.util.Utils;

@Service("tipusCitaService")
public class TipusCitaServiceImpl implements TipusCitaService{
	
	@Autowired
	private TipusCitaDao tipusCitaDao;
	
	@Override
	public List<TipusCitaDto> getAllTipusCitaBySubaplCoa(String subaplCoa, Locale locale) {
		List<TipusCitaDto> dtos = new ArrayList<>();
		
		if (!Utils.isEmpty(subaplCoa)) {
			List<TipusCita> llista = tipusCitaDao.getAllTipusCitaBySubapl(subaplCoa);			
			llista.forEach(item->dtos.add(Converter.toDto(item)));
		}	
		return dtos;
	}
	
	@Override
	public TipusCitaDto getTipusCitaByCon (Long con, Locale locale) {
		TipusCitaDto dto = new TipusCitaDto();
		if (!Utils.isEmpty(con)) {
			dto = Converter.toDto(tipusCitaDao.findTipusCitaByCon(con));
		}
		return dto;
	}
}
