package uoc.edu.citaprevia.api.service;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.SubaplicacioDao;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.SubaplicacioDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@Service("subaplicacioPreviaService")
public class SubaplicacioServiceImpl implements SubaplicacioService{
	
	@Autowired
	private SubaplicacioDao subaplicacioDao;
	
	@Autowired
	private MessageSource bundle;
	
	private static final Logger LOG = LoggerFactory.getLogger(SubaplicacioServiceImpl.class);

	
	@Override
	public SubaplicacioDto getSubaplicacioByCoa (String coa, Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici SubaplicacioServiceImpl.getSubaplicacioByCoa startTime={}, coa={}", startTime, coa);	
		SubaplicacioDto dto = new SubaplicacioDto();		
		try {

			if (!Utils.isEmpty(coa)) {
				dto = Converter.toDto(subaplicacioDao.findSubaplicacioByCoa(coa));
			}
			
		} catch (Exception e) {
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_SUBAPLICACIONS_TIPUS, null, locale)));
			LOG.error("### Error AgendaServiceImpl.getSubaplicacioByCoa:", e);
			LOG.error("### Error AgendaServiceImpl.getSubaplicacioByCoa={} " , bundle.getMessage(Constants.ERROR_API_CRUD_SUBAPLICACIONS_TIPUS, null, locale));
						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final AgendaServiceImpl.getSubaplicacioByCoa totalTime={}, coa={}, subaplicacio={}", totalTime, coa, dto.toString());
		}
		
		return dto;
	}
	
	

}
