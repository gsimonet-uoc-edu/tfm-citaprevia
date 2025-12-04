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
import uoc.edu.citaprevia.api.dao.CitaDao;
import uoc.edu.citaprevia.api.model.Agenda;
import uoc.edu.citaprevia.api.model.Cita;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@Service("agendaService")
public class AgendaServiceImpl implements AgendaService{
	
	@Autowired
	private AgendaDao agendaDao;
	
	@Autowired
	private CitaDao citaDao;
	
	@Autowired
	private MessageSource bundle;
	
	private static final Logger LOG = LoggerFactory.getLogger(UbicacioServiceImpl.class);

	
	@Override
	public List<AgendaDto> getAgendesByTipusCitaAndSubaplicacio (Long tipCitCon, String subaplCoa, Locale locale) {
		
		List<AgendaDto> dtos = new ArrayList<AgendaDto>();
		
		if (!Utils.isEmpty(tipCitCon) && !Utils.isEmpty(subaplCoa)) {
			List<Agenda> daos = agendaDao.findAgendesByTipusCitaAndSubaplicacio(tipCitCon, subaplCoa);	
			daos.forEach(item->dtos.add(Converter.toDto(item)));
		}
		
		return dtos;
	}
	
	@Override
	public List<AgendaDto> getAgendesObertesByTipusCitaAndSubaplicacio (Long tipCitCon, String subaplCoa, Locale locale) {
		
		List<AgendaDto> dtos = new ArrayList<AgendaDto>();
		
		if (!Utils.isEmpty(tipCitCon) && !Utils.isEmpty(subaplCoa)) {
			List<Agenda> daos = agendaDao.findAgendesObertesByTipusCitaAndSubaplicacio(tipCitCon, subaplCoa);	
			daos.forEach(item->dtos.add(Converter.toDto(item)));
		}
		
		return dtos;
	}
	
	@Override
	public AgendaDto getAgenda(Long con, Locale locale) {
		AgendaDto dto = new AgendaDto();
		if (!Utils.isEmpty(con)) {
			Agenda dao = agendaDao.findAgendaByCon(con);
			dto = Converter.toDto(dao);
		}
		return dto;
	}
	
	@Override
	public List<AgendaDto> getAgendesBySubaplicacio (String subaplCoa, Locale locale) {
		
		List<AgendaDto> dtos = new ArrayList<AgendaDto>();
		
		if (!Utils.isEmpty(subaplCoa)) {
			List<Agenda> daos = agendaDao.findAgendesBySubaplicacio(subaplCoa);
			daos.forEach(item->dtos.add(Converter.toDto(item)));
		}
		
		return dtos;
	}
	
	@Override
	public List<AgendaDto> getAgendesByTecnic (String tecCoa, Locale locale) {
		
		List<AgendaDto> dtos = new ArrayList<AgendaDto>();
		
		if (!Utils.isEmpty(tecCoa)) {
			List<Agenda> daos = agendaDao.findAgendesByTecnicCoa(tecCoa);
			daos.forEach(item->dtos.add(Converter.toDto(item)));
		}
		
		return dtos;
	}
	
	
	@Override
	public AgendaDto saveAgenda (AgendaDto agenda, Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici AgendaServiceImpl.saveAgenda startTime={}, agenda={}", startTime, agenda.toString());
		AgendaDto dto = new AgendaDto();
		try {
			if (dto != null && Utils.isEmpty(dto.getCon())) {
				dto = Converter.toDto(agendaDao.saveAgenda(Converter.toDao(agenda)));
			} else {
				dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_AGENDA, null, locale)));
				LOG.error("### Error AgendaServiceImpl.saveAgenda={} " , dto.getErrors().get(0).toString());
			}
		} catch (Exception e) {
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_AGENDA, null, locale)));
			LOG.error("### Error AgendaServiceImpl.saveAgenda:", e);
			LOG.error("### Error AgendaServiceImpl.saveAgenda={} " , bundle.getMessage(Constants.ERROR_API_CRUD_AGENDA, null, locale));
						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final AgendaServiceImpl.saveAgenda totalTime={}, agenda={}", totalTime, dto.toString());
		}
		return dto;				
	}
	
	@Override
	public AgendaDto updateAgenda (AgendaDto agenda, Locale locale) {
		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici AgendaServiceImpl.updateAgenda startTime={}, agenda={}", startTime, agenda.toString());
		AgendaDto dto = new AgendaDto();
		try {
			if (agenda != null && !Utils.isEmpty(agenda.getCon())) {
				// Validacions per actualitzar agenda
				List<Cita> teCites = citaDao.findCitesByAgenda(agenda.getCon());
				// Comprovar que l'agenda no te cites
				if (teCites != null && !teCites.isEmpty() && teCites.size() > 0) {
					dto.addError(new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_UPDATE_AGENDA_AMB_CITES, null, locale)));
				} else {
					dto = Converter.toDto(agendaDao.updateAgenda(Converter.toDao(agenda)));
				}
			} else {
				dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_AGENDA, null, locale)));
				LOG.error("### Error AgendaServiceImpl.updateAgenda={} " , dto.getErrors().get(0).toString());
			}
		} catch (Exception e) {
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_AGENDA, null, locale)));
			LOG.error("### Error AgendaServiceImpl.updateAgenda:" , e);
			LOG.error("### Error AgendaServiceImpl.updateAgenda={} " , bundle.getMessage(Constants.ERROR_API_CRUD_AGENDA, null, locale));
						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final AgendaServiceImpl.updateAgenda totalTime={}, agenda={}", totalTime, agenda.toString());
		}
		return dto;				
	}
	
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
				// Validacions per esborrar agenda
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
			return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_CRUD_AGENDA, null, locale));

		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final AgendaServiceImpl.deleteAgenda totalTime={}, ageCon={}", totalTime, con);
		}
		return dto;	
		
	}
	

}
