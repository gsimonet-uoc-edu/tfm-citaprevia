package uoc.edu.citaprevia.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.AgendaDao;
import uoc.edu.citaprevia.api.dao.UbicacioDao;
import uoc.edu.citaprevia.api.model.Agenda;
import uoc.edu.citaprevia.api.model.Ubicacio;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.UbicacioDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@Service("ubicacioService")
public class UbicacioServiceImpl implements UbicacioService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UbicacioServiceImpl.class);

	@Autowired
	private UbicacioDao ubicacioDao;
	
	@Autowired
	private AgendaDao agendaDao;
	
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
			LOG.error("### Error UbicacioServiceImpl.getAllUbicacions: " , bundle.getMessage(Constants.ERROR_API_FIND_UBICACIONS, null, locale));
			e.printStackTrace();			
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final UbicacioServiceImpl.getAllUbicacions totalTime={}, resposta.size={}", totalTime, resposta.size());
		}
		return resposta;
	}
	
	@Override
	public List<UbicacioDto> getUbicacionsByUbicacio(String subaplCoa, Locale locale) {
		List<UbicacioDto> resposta = new ArrayList<>();
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici UbicacioServiceImpl.getUbicacionsByUbicacio startTime={}, subaplCoa={}", startTime, subaplCoa);
		try {
			List<Ubicacio> llista = ubicacioDao.findUbicacionsBySubaplicacio(subaplCoa);
			llista.forEach(item->resposta.add(Converter.toDto(item)));
		} catch (Exception e) {
			LOG.error("### Error UbicacioServiceImpl.getUbicacionsByUbicacio: " , bundle.getMessage(Constants.ERROR_API_FIND_UBICACIONS, null, locale));
			e.printStackTrace();			
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final UbicacioServiceImpl.getUbicacionsByUbicacio totalTime={}, subaplCoa={}, resposta.size={}", totalTime, subaplCoa, resposta.size());
		}
		return resposta;
	}
	
	@Override
	public UbicacioDto saveUbicacio (UbicacioDto ubicacio, Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici UbicacioServiceImpl.saveUbicacio startTime={}, ubicacio={}", startTime, ubicacio.toString());
		UbicacioDto dto = new UbicacioDto();
		try {
			if (dto != null && Utils.isEmpty(dto.getCon())) {
				dto = Converter.toDto(ubicacioDao.saveUbicacio(Converter.toDao(ubicacio)));
			} else {
				dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_UBICACIONS, null, locale)));
				LOG.error("### Error UbicacioServiceImpl.saveUbicacio={} " , dto.getErrors().get(0).toString());
			}
		} catch (Exception e) {
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_UBICACIONS, null, locale)));
			LOG.error("### Error UbicacioServiceImpl.saveUbicacio:", e);
			LOG.error("### Error UbicacioServiceImpl.saveUbicacio={} " , bundle.getMessage(Constants.ERROR_API_CRUD_UBICACIONS, null, locale));
						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final UbicacioServiceImpl.saveUbicacio totalTime={}, ubicacio={}", totalTime, dto.toString());
		}
		return dto;				
	}
	
	@Override
	public UbicacioDto updateUbicacio (UbicacioDto ubicacio, Locale locale) {
		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici UbicacioServiceImpl.updateUbicacio startTime={}, ubicacio={}", startTime, ubicacio.toString());
		UbicacioDto dto = new UbicacioDto();
		try {
			if (ubicacio != null && !Utils.isEmpty(ubicacio.getCon())) {
				dto = Converter.toDto(ubicacioDao.updateUbicacio(Converter.toDao(ubicacio)));
			} else {
				dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_UBICACIONS, null, locale)));
				LOG.error("### Error UbicacioServiceImpl.updateUbicacio={} " , dto.getErrors().get(0).toString());
			}
		} catch (Exception e) {
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_UBICACIONS, null, locale)));
			LOG.error("### Error UbicacioServiceImpl.updateUbicacio:" , e);
			LOG.error("### Error UbicacioServiceImpl.updateUbicacio={} " , bundle.getMessage(Constants.ERROR_API_CRUD_UBICACIONS, null, locale));						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final UbicacioServiceImpl.updateUbicacio totalTime={}, ubicacio={}", totalTime, dto.toString());
		}
		return dto;				
	}
	
	@Override
	public ErrorDto deleteUbicacio(Long con, Locale locale) {		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici UbicacioServiceImpl.deleteUbicacio startTime={}, ubiCon={}", startTime, con);
		ErrorDto dto = null;
		try {
			
			Ubicacio dao = ubicacioDao.findUbicacioByCon(con);
			
			if (dao == null) {
				return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_CRUD_UBICACIONS, null, locale));
			} else {
				// Validacions per esborrar ubicació
				List<Agenda> teAgendes = agendaDao.findAgendesByUbicacio(con);
				// Comprovar que la ubicació no te agendes.
				if (teAgendes != null && !teAgendes.isEmpty() && teAgendes.size() > 0) {
					return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_DELETE_AGENDES_UBICACIONS, null, locale));
				} else {
					ubicacioDao.deleteUbicacio(dao);
				}			
			}
		
		} catch (Exception e) {
			LOG.error("### UbicacioServiceImpl.deleteUbicacio:" , e);
			LOG.error("### UbicacioServiceImpl.deleteUbicacio={} ", bundle.getMessage(Constants.ERROR_API_CRUD_UBICACIONS, null, locale));		
			return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_CRUD_UBICACIONS, null, locale));

		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final UbicacioServiceImpl.deleteUbicacio totalTime={}, ubiCon={}", totalTime, con);
		}
		return dto;	
		
	}

}
