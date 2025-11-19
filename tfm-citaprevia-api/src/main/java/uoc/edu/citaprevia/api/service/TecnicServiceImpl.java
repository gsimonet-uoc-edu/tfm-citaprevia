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
import uoc.edu.citaprevia.api.dao.TecnicDao;
import uoc.edu.citaprevia.api.model.Agenda;
import uoc.edu.citaprevia.api.model.Tecnic;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@Service("tecnicService")
public class TecnicServiceImpl implements TecnicService{
	
	@Autowired
	private TecnicDao tecnicDao;
	
	@Autowired
	private AgendaDao agendaDao;
	
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
	
	@Override
	public TecnicDto saveTecnic (TecnicDto tecnic, Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici TecnicServiceImpl.saveTecnic startTime={}, tecnic={}", startTime, tecnic.toString());
		TecnicDto dto = new TecnicDto();
		try {
			if (dto != null && Utils.isEmpty(dto.getCoa())) {
				dto = Converter.toDto(tecnicDao.saveTecnic(Converter.toDao(tecnic)));
			} else {
				dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_TECNIC, null, locale)));
				LOG.error("### Error TecnicServiceImpl.saveTecnic={} " , dto.getErrors().get(0).toString());
			}
		} catch (Exception e) {
			LOG.error("### Error TecnicServiceImpl.saveTecnic:", e);
			LOG.error("### Error TecnicServiceImpl.saveTecnic={} " , bundle.getMessage(Constants.ERROR_API_CRUD_TECNIC, null, locale));
						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final TecnicServiceImpl.saveTecnic totalTime={}, tecnic={}", totalTime, dto.toString());
		}
		return dto;				
	}
	
	@Override
	public TecnicDto updateTecnic (TecnicDto tecnic, Locale locale) {
		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici TecnicServiceImpl.updateTecnic startTime={}, tecnic={}", startTime, tecnic.toString());
		TecnicDto dto = new TecnicDto();
		try {
			if (tecnic != null && !Utils.isEmpty(tecnic.getCoa())) {
				dto = Converter.toDto(tecnicDao.updateTecnic(Converter.toDao(tecnic)));
			} else {
				dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_TECNIC, null, locale)));
				LOG.error("### Error TecnicServiceImpl.updateTecnic={} " , dto.getErrors().get(0).toString());
			}
		} catch (Exception e) {
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_TECNIC, null, locale)));
			LOG.error("### Error TecnicServiceImpl.updateTecnic:" , e);
			LOG.error("### Error TecnicServiceImpl.updateTecnic={} " , bundle.getMessage(Constants.ERROR_API_CRUD_TECNIC, null, locale));
						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final TecnicServiceImpl.updateTecnic totalTime={}, tecnic={}", totalTime, tecnic.toString());
		}
		return dto;				
	}
	
	@Override
	public ErrorDto deleteTecnic(String coa, Locale locale) {		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici TecnicServiceImpl.deleteTecnic startTime={}, tecCoa={}", startTime, coa);
		ErrorDto dto = null;
		try {
			
			Tecnic dao = tecnicDao.findTecnicByCoa(coa);
			
			if (dao == null) {
				return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_CRUD_TECNIC, null, locale));
			} else {
				// Validacions per esborrar tecnic
				List<Agenda> teAgendes = agendaDao.findAgendesByTecnicCoa(coa);
				// Comprovar que el tècnic no te cites
				if (teAgendes != null && !teAgendes.isEmpty() && teAgendes.size() > 0) {
					return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_DELETE_TECNIC_AGENDES, null, locale));
				} else {
					tecnicDao.deleteTecnic(dao);
				}			
			}
		
		} catch (Exception e) {
			LOG.error("### Error TecnicServiceImpl.deleteTecnic:" , e);
			LOG.error("### Error TecnicServiceImpl.deleteTecnic={} ", bundle.getMessage(Constants.ERROR_API_CRUD_TECNIC, null, locale));		
			return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_CRUD_TECNIC, null, locale));
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final TecnicServiceImpl.deleteTecnic totalTime={}, tecCoa={}", totalTime, coa);
		}
		return dto;	
		
	}

}
