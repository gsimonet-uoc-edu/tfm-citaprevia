package uoc.edu.citaprevia.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.TecnicDao;
import uoc.edu.citaprevia.api.model.Tecnic;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@Service("tecnicService")
public class TecnicServiceImpl implements TecnicService{
	
	@Autowired
	private TecnicDao tecnicDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(TecnicServiceImpl.class);
	
	@Autowired
	protected MessageSource bundle;
	
	
	@Override
	public TecnicDto getTecnicByCoa (String coa) {
		TecnicDto dto = new TecnicDto();
		if (!Utils.isEmpty(coa)) {
			dto = Converter.toDto(tecnicDao.findTecnicByCoa(coa));
		}
		return dto;
	}
	
	@Override
	public List<TecnicDto> getTecnicsSubaplicacio(String subaplCoa, Locale locale) {
		List<TecnicDto> resposta = new ArrayList<>();
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici TecnicServiceImpl.getTecnicsSubaplicacio startTime={}, subaplCoa={}", startTime, subaplCoa);
		try {
			List<Tecnic> llista = tecnicDao.findTecnicsBySubaplicacio(subaplCoa);
			llista.forEach(item->resposta.add(Converter.toDto(item)));
		} catch (Exception e) {
			LOG.error("### Error TecnicServiceImpl.getTecnicsSubaplicacio" , e);
			LOG.error("### Error TecnicServiceImpl.getTecnicsSubaplicacio={} " , bundle.getMessage(Constants.ERROR_API_FIND_UBICACIONS, null, locale));		
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final UbicacioServiceImpl.getAllUbicacions totalTime={}, subaplCoa={}, resposta.size={}", totalTime, subaplCoa, resposta.size());
		}
		return resposta;
	}

}
