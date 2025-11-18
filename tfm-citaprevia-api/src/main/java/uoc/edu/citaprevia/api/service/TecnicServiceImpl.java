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
import uoc.edu.citaprevia.api.model.Agenda;
import uoc.edu.citaprevia.api.model.Cita;
import uoc.edu.citaprevia.api.model.Tecnic;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
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
			LOG.info("### Final TecnicServiceImpl.saveTecnic totalTime={}, tecnic={}", totalTime, tecnic.toString());
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
			LOG.error("### Error TecnicServiceImpl.updateTecnic:" , e);
			LOG.error("### Error TecnicServiceImpl.updateTecnic={} " , bundle.getMessage(Constants.ERROR_API_CRUD_TECNIC, null, locale));
						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final TecnicServiceImpl.updateTecnic totalTime={}, tecnic={}", totalTime, tecnic.toString());
		}
		return dto;				
	}
	/*
	@Override
	public ErrorDto deleteAgenda(Long con, Locale locale) {		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici AgendaServiceImpl.deleteAgenda startTime={}, ageCon={}", startTime, con);
		ErrorDto dto = null;
		try {
			
			Agenda dao = agendaDao.findAgendaByCon(con);
			
			if (dao == null) {
				return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_CRUD_AGENDA, null, locale));
			} else {
				// Validacions per esborrar cita
				List<Cita> teCites = citaDao.findCitesByAgenda(con);
				// Comprovar que l'agenda no te cites
				if (teCites != null && !teCites.isEmpty() && teCites.size() > 0) {
					return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_DELETE_AGENDA, null, locale));
				} else {
					agendaDao.deleteAgenda(dao);
				}			
			}
		
		} catch (Exception e) {
			LOG.error("### Error AgendaServiceImpl.deleteAgenda:" , e);
			LOG.error("### Error AgendaServiceImpl.deleteAgenda={} ", bundle.getMessage(Constants.ERROR_API_CRUD_AGENDA, null, locale));		
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final AgendaServiceImpl.deleteAgenda totalTime={}, ageCon={}", totalTime, con);
		}
		return dto;	
		
	}
*/
}
