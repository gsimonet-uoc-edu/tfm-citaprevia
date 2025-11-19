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
import uoc.edu.citaprevia.api.dao.TipusCitaDao;
import uoc.edu.citaprevia.api.model.Horari;
import uoc.edu.citaprevia.api.model.TipusCita;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.TipusCitaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@Service("tipusCitaService")
public class TipusCitaServiceImpl implements TipusCitaService{
	
	@Autowired
	private TipusCitaDao tipusCitaDao;
	
	@Autowired
	private HorariDao horariDao;
	
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
	
	
	@Override
	public TipusCitaDto saveTipusCita (TipusCitaDto tipusCita, Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici TipusCitaServiceImpl.saveTipusCita startTime={}, tipusCita={}", startTime, tipusCita.toString());
		TipusCitaDto dto = new TipusCitaDto();
		try {
			if (dto != null && Utils.isEmpty(dto.getCon())) {
				dto = Converter.toDto(tipusCitaDao.saveTipusCita(Converter.toDao(tipusCita)));
			} else {
				dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_TIPUS_CITA, null, locale)));
				LOG.error("### Error TipusCitaServiceImpl.saveTipusCita={} " , dto.getErrors().get(0).toString());
			}
		} catch (Exception e) {
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_TIPUS_CITA, null, locale)));
			LOG.error("### Error TipusCitaServiceImpl.saveTipusCita:", e);
			LOG.error("### Error TipusCitaServiceImpl.saveTipusCita={} " , bundle.getMessage(Constants.ERROR_API_CRUD_TIPUS_CITA, null, locale));
						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final TipusCitaServiceImpl.saveTipusCita totalTime={}, tipusCita={}", totalTime, dto.toString());
		}
		return dto;				
	}
	
	@Override
	public TipusCitaDto updateTipusCita (TipusCitaDto tipusCita, Locale locale) {
		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici TipusCitaServiceImpl.updateTipusCita startTime={}, tipusCita={}", startTime, tipusCita.toString());
		TipusCitaDto dto = new TipusCitaDto();
		try {
			if (tipusCita != null && !Utils.isEmpty(tipusCita.getCon())) {
				dto = Converter.toDto(tipusCitaDao.updateTipusCita(Converter.toDao(tipusCita)));
			} else {
				dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_TIPUS_CITA, null, locale)));
				LOG.error("### Error TipusCitaServiceImpl.updateTipusCita={} " , dto.getErrors().get(0).toString());
			}
		} catch (Exception e) {
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_TIPUS_CITA, null, locale)));
			LOG.error("### Error TipusCitaServiceImpl.updateTipusCita:" , e);
			LOG.error("### Error TipusCitaServiceImpl.updateTipusCita={} " , bundle.getMessage(Constants.ERROR_API_CRUD_TIPUS_CITA, null, locale));						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final TipusCitaServiceImpl.updateTipusCita totalTime={}, tipusCita={}", totalTime, dto.toString());
		}
		return dto;				
	}
	
	@Override
	public ErrorDto deleteTipusCita(Long con, Locale locale) {		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici TipusCitaServiceImpl.deleteTipusCita startTime={}, tipcitCon={}", startTime, con);
		ErrorDto dto = null;
		try {
			
			TipusCita dao = tipusCitaDao.findTipusCitaByCon(con);
			
			if (dao == null) {
				return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_CRUD_TIPUS_CITA, null, locale));
			} else {
				// Validacions per esborrar cita
				List<Horari> teHoraris = horariDao.findHorarisByTipusCita(con);
				// Comprovar que l'agenda no te cites
				if (teHoraris != null && !teHoraris.isEmpty() && teHoraris.size() > 0) {
					return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_DELETE_TIPUS_CITES_HORARIS, null, locale));
				} else {
					tipusCitaDao.deleteTipusCita(dao);
				}			
			}
		
		} catch (Exception e) {
			LOG.error("### TipusCitaServiceImpl.deleteTipusCita:" , e);
			LOG.error("### TipusCitaServiceImpl.deleteTipusCitaa={} ", bundle.getMessage(Constants.ERROR_API_CRUD_TIPUS_CITA, null, locale));		
			return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_CRUD_TIPUS_CITA, null, locale));

		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final TipusCitaServiceImpl.deleteTipusCita totalTime={}, tipcitCon={}", totalTime, con);
		}
		return dto;	
		
	}
}
