package uoc.edu.citaprevia.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.HorariDao;
import uoc.edu.citaprevia.api.model.Horari;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.HorariDto;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@Service("horariService")
public class HorariServiceImpl implements HorariService{
	
	private static final Logger LOG = LoggerFactory.getLogger(HorariServiceImpl.class);

	@Autowired
	private HorariDao horariDao;
	
	@Autowired
	protected MessageSource bundle;
	
	@Override
	public List<HorariDto> getHorarisBySubaplicacio(String subaplCoa, Locale locale) {
		List<HorariDto> resposta = new ArrayList<>();
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici HorariServiceImpl.getHorarisBySubaplicacio startTime={}", startTime);
		try {
			if (!Utils.isEmpty(subaplCoa)) {
				List<Horari> llista = horariDao.findHorarisBySubaplicacio(subaplCoa);
				llista.forEach(item->resposta.add(Converter.toDto(item)));
			} else {
				LOG.error("### Error HorariServiceImpl.getHorarisBySubaplicacio: " , bundle.getMessage(Constants.ERROR_FIND_HORARIS, null, locale));
			}
			
		} catch (Exception e) {
			LOG.error("### Error HorariServiceImpl.getHorarisBySubaplicacio: " , bundle.getMessage(Constants.ERROR_FIND_HORARIS, null, locale));
			e.printStackTrace();			
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final HorariServiceImpl.getHorarisBySubaplicacio totalTime={}, resposta.size={}", totalTime, resposta.size());
		}
		return resposta;
	}

}
