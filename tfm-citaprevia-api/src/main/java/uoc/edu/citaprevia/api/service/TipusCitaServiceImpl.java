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

@Service("tipusCitaService")
public class TipusCitaServiceImpl implements TipusCitaService{
	
	@Autowired
	private TipusCitaDao tipusCitaDao;
	
	@Override
	public List<TipusCitaDto> getAllTipusCitaBySubaplCoa(String subaplCoa, Locale locale) {
		List<TipusCita> llista = tipusCitaDao.getAllTipusCitaBySubapl(subaplCoa);
		List<TipusCitaDto> dto = new ArrayList<>();
		llista.forEach(item->dto.add(Converter.toDto(item)));
		return dto;
	}

}
