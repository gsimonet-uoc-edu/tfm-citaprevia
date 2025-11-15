package uoc.edu.citaprevia.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.TipusCitaDao;
import uoc.edu.citaprevia.api.model.TipusCita;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.TipusCitaDto;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@Service("tipusCitaService")
public class TipusCitaServiceImpl implements TipusCitaService{
	
	@Autowired
	private TipusCitaDao tipusCitaDao;
	
	@Autowired
	protected MessageSource bundle;
	
	private static final Logger LOG = LoggerFactory.getLogger(TipusCitaServiceImpl.class);
	
	@Override
	public List<TipusCitaDto> getAllTipusCitaBySubaplCoa(String subaplCoa, Locale locale) {
		List<TipusCitaDto> resposta = new ArrayList<>();
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici TipusCitaServiceImpl.getAllTipusCitaBySubaplCoa startTime={}", startTime);
		try {
			if (!Utils.isEmpty(subaplCoa)) {
				List<TipusCita> llista = tipusCitaDao.getAllTipusCitaBySubapl(subaplCoa);			
				llista.forEach(item->resposta.add(Converter.toDto(item)));
			}	
		} catch (Exception e) {
			LOG.error("### Error TipusCitaServiceImpl.getAllTipusCitaBySubaplCoa: " , bundle.getMessage(Constants.ERROR_API_FIND_HORARIS, null, locale));
			e.printStackTrace();			
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final TipusCitaServiceImpl.getAllTipusCitaBySubaplCoa totalTime={}, resposta.size={}", totalTime, resposta.size());
		}
		return resposta;
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
