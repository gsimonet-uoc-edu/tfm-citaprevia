package uoc.edu.citaprevia.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.CitaDao;
import uoc.edu.citaprevia.api.dao.HorariDao;
import uoc.edu.citaprevia.api.model.Agenda;
import uoc.edu.citaprevia.api.model.Cita;
import uoc.edu.citaprevia.api.model.Horari;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.HorariDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@Service("horariService")
public class HorariServiceImpl implements HorariService{
	
	private static final Logger LOG = LoggerFactory.getLogger(HorariServiceImpl.class);

	@Autowired
	private HorariDao horariDao;
	
	@Autowired
	private CitaDao citaDao;
	
	@Autowired
	protected MessageSource bundle;
	
	
	@Override
	public HorariDto getHorari (Long con, Locale locale) {
		HorariDto dto = new HorariDto();
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici HorariServiceImpl.getHorari startTime={}, horCon={}", startTime, con);
		try {
			if (!Utils.isEmpty(con)) {
				Horari dao = horariDao.findHorariByCon(con);
				dto = Converter.toDto(dao);
			}
		} catch (Exception e) {
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_HORARI, null, locale)));
			LOG.error("### Error HorariServiceImpl.getHorari:", e);
			LOG.error("### Error HorariServiceImpl.getHorari: " , bundle.getMessage(Constants.ERROR_API_CRUD_HORARI, null, locale));
					
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final HorariServiceImpl.getHorari totalTime={}, horari={}", totalTime, dto.toString());
		}
		return dto;
	}
	
	
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
				LOG.error("### Error HorariServiceImpl.getHorarisBySubaplicacio: " , bundle.getMessage(Constants.ERROR_API_FIND_HORARIS, null, locale));
			}
			
		} catch (Exception e) {
			LOG.error("### Error HorariServiceImpl.getHorarisBySubaplicacio: " , bundle.getMessage(Constants.ERROR_API_FIND_HORARIS, null, locale));
			e.printStackTrace();			
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final HorariServiceImpl.getHorarisBySubaplicacio totalTime={}, resposta.size={}", totalTime, resposta.size());
		}
		return resposta;
	}
	
	@Override
	public HorariDto saveHorari (HorariDto horari, Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici HorariServiceImpl.saveHorari startTime={}, horari={}", startTime, horari.toString());
		HorariDto dto = new HorariDto();
		try {
			if (dto != null && Utils.isEmpty(dto.getCon())) {
				dto = Converter.toDto(horariDao.saveHorari(Converter.toDao(horari)));
			} else {
				dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_HORARI, null, locale)));
				LOG.error("### Error HorariServiceImpl.saveHorari: " , dto.getErrors().get(0).toString());
			}
		} catch (Exception e) {
			LOG.error("### Error HorariServiceImpl.saveHorari:", e);
			LOG.error("### Error HorariServiceImpl.saveHorari: " , bundle.getMessage(Constants.ERROR_API_CRUD_HORARI, null, locale));
						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final HorariServiceImpl.saveHorari totalTime={}, horari={}", totalTime, horari.toString());
		}
		return dto;				
	}
	
	@Override
	public HorariDto updateHorari (HorariDto horari, Locale locale) {
		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici HorariServiceImpl.updateHorari startTime={}, horari={}", startTime, horari.toString());
		HorariDto dto = new HorariDto();
		try {
			if (horari != null && !Utils.isEmpty(horari.getCon())) {
				dto = Converter.toDto(horariDao.updateHorari(Converter.toDao(horari)));
			} else {
				dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_HORARI, null, locale)));
				LOG.error("### Error HorariServiceImpl.updateHorari: " , dto.getErrors().get(0).toString());
			}
		} catch (Exception e) {
			LOG.error("### Error HorariServiceImpl.updateHorari:" , e);
			LOG.error("### Error HorariServiceImpl.updateHorari: " , bundle.getMessage(Constants.ERROR_API_CRUD_HORARI, null, locale));
						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final HorariServiceImpl.updateHorari totalTime={}, horari={}", totalTime, horari.toString());
		}
		return dto;				
	}
	
	@Override
	public ErrorDto deleteHorari(Long con, Locale locale) {		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici HorariServiceImpl.deleteHorari startTime={}, horCon={}", startTime, con);
		ErrorDto dto = null;
		try {
			
			Horari dao = horariDao.findHorariByCon(con);
			
			if (dao == null) {
				return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_CRUD_HORARI, null, locale));
			} else {
				// Validacions per esborrar horari
				List<Cita> teCites = citaDao.findCitesByHorari(con);
				// Comprovar que l'agenda no te cites
				if (teCites != null && !teCites.isEmpty() && teCites.size() > 0) {
					return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_DELETE_HORARI, null, locale));
				} else {
					horariDao.deleteHorari(dao);
				}			
			}
		
		} catch (Exception e) {
			LOG.error("### Error HorariServiceImpl.deleteHorari: ", bundle.getMessage(Constants.ERROR_API_CRUD_HORARI, null, locale));
			e.printStackTrace();			
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final HorariServiceImpl.deleteHorari totalTime={}, horCon={}", totalTime, con);
		}
		return dto;	
		
	}

}
