package uoc.edu.citaprevia.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.UbicacioDao;
import uoc.edu.citaprevia.api.model.Ubicacio;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.UbicacioDto;
import uoc.edu.citaprevia.util.Constants;

@Service("ubicacioService")
public class UbicacioServiceImpl implements UbicacioService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UbicacioServiceImpl.class);

	@Autowired
	private UbicacioDao ubicacioDao;
	
	@Autowired
	protected MessageSource bundle;
	
	@Override
	public List<UbicacioDto> getAllUbicacions(Locale locale) {
		List<UbicacioDto> resposta = new ArrayList<>();
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici UbicacioServiceImpl.getAllUbicacions startTime={}", startTime);
		try {
			List<Ubicacio> llista = ubicacioDao.findUbicacions();	
			llista.forEach(item->resposta.add(Converter.toDto(item)));
		} catch (Exception e) {
			LOG.error("### Error UbicacioServiceImpl.getAllUbicacions: " , bundle.getMessage(Constants.ERROR_FIND_UBICACIONS, null, locale));
			e.printStackTrace();			
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final UbicacioServiceImpl.getAllUbicacions totalTime={}, resposta.size={}", totalTime, resposta.size());
		}
		return resposta;
	}

	
	// 			return new ImiErrorDto(9999L,bundle.getMessage(MercatsConstants.DELETE_COOPERATIVA_EXPEDIENT_EXISTENT, null, locale));

}
