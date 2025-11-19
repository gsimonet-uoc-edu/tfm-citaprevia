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
import uoc.edu.citaprevia.api.dao.SetmanaTipusDao;
import uoc.edu.citaprevia.api.model.Agenda;
import uoc.edu.citaprevia.api.model.Cita;
import uoc.edu.citaprevia.api.model.SetmanaTipus;
import uoc.edu.citaprevia.api.model.SetmanaTipusId;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.SetmanaTipusDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@Service("setmanaTipusService")
public class SetmanaTipusServiceImpl implements SetmanaTipusService{
	
	@Autowired
	private SetmanaTipusDao setmanaTipusDao;
	
	@Autowired CitaDao citaDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(SetmanaTipusServiceImpl.class);

	
	@Autowired
	protected MessageSource bundle;
	
	@Override
	public List<SetmanaTipusDto> getSetmanaTipusByHorari(Long horCon, Locale locale) {
		List<SetmanaTipusDto> dtos = new ArrayList<>();
		
		if (!Utils.isEmpty(horCon)) {
			List<SetmanaTipus> llista = setmanaTipusDao.findSetmanaTipusByHorari(horCon);
			llista.forEach(item->dtos.add(Converter.toDto(item)));
		}	
		return dtos;
	}
	
	@Override
	public SetmanaTipusDto saveSetmanaTipus (SetmanaTipusDto settip, Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici SetmanaTipusServiceImpl.saveSetmanaTipus startTime={}, settip={}", startTime, settip.toString());
		SetmanaTipusDto dto = new SetmanaTipusDto();
		try {
			if (settip != null && settip.getHorari() != null && !Utils.isEmpty(settip.getHorari().getCon()) && !Utils.isEmpty(settip.getDiasetCon()) && settip.getHorini() != null && settip.getHorfin() != null) {
				SetmanaTipusId id = new SetmanaTipusId();
				id.setHorari(Converter.toDao(settip.getHorari()));
				id.setDiasetCon(settip.getDiasetCon());
				id.setHorini(settip.getHorini());
				id.setHorfin(settip.getHorfin());
				SetmanaTipus settipExist = setmanaTipusDao.findSetmanaTipusById(id);
				// Restricció per no duplicar franges
				if (settipExist != null && settipExist.getId() != null && settipExist.getId().getHorari() != null &&  !Utils.isEmpty(settipExist.getId().getHorari().getCon())) {
					dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_ADD_CITES_SETMANES_TIPUS, null, locale)));
					LOG.error("### Error SetmanaTipusServiceImpl.saveSetmanaTipus={} " , dto.getErrors().get(0).toString());
				} else {
					dto = Converter.toDto(setmanaTipusDao.saveSetmanaTipus(Converter.toDao(settip)));
				}
				
			} else {
				dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_SETMANES_TIPUS, null, locale)));
				LOG.error("### Error SetmanaTipusServiceImpl.saveSetmanaTipus={} " , dto.getErrors().get(0).toString());
			}
		} catch (Exception e) {
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_SETMANES_TIPUS, null, locale)));
			LOG.error("### Error SetmanaTipusServiceImpl.saveSetmanaTipus:", e);
			LOG.error("### Error SetmanaTipusServiceImpl.saveSetmanaTipus={} " , bundle.getMessage(Constants.ERROR_API_CRUD_SETMANES_TIPUS, null, locale));
						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final SetmanaTipusServiceImpl.saveSetmanaTipus totalTime={}, settip={}", totalTime, dto.toString());
		}
		return dto;				
	}
	
	@Override
	public SetmanaTipusDto updateSetmanaTipus(SetmanaTipusDto settip, Locale locale) {
		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici SetmanaTipusServiceImpl.updateSetmanaTipus startTime={}, settip={}", startTime, settip.toString());
		SetmanaTipusDto dto = new SetmanaTipusDto();
		try {
			if (settip != null && settip.getHorari() != null && !Utils.isEmpty(settip.getHorari().getCon()) && !Utils.isEmpty(settip.getDiasetCon()) && settip.getHorini() != null && settip.getHorfin() != null) {
				List<Cita> cites = citaDao.findCitesByHorari(settip.getHorari().getCon());
				// Restricció: Comprovar si l'horari té cites ocupades
				if (cites != null && !cites.isEmpty()) {
					dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_UPDATE_CITES_SETMANES_TIPUS, null, locale)));
					LOG.error("### Error SetmanaTipusServiceImpl.updateSetmanaTipus={} " , dto.getErrors().get(0).toString());

				} else {
					dto = Converter.toDto(setmanaTipusDao.updateSetmanaTipus(Converter.toDao(settip)));

				}
			} else {
				dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_SETMANES_TIPUS, null, locale)));
				LOG.error("### Error SetmanaTipusServiceImpl.updateSetmanaTipus: " , dto.getErrors().get(0).toString());
			}
		} catch (Exception e) {
			LOG.error("### Error SetmanaTipusServiceImpl.updateSetmanaTipus:" , e);
			LOG.error("### Error SetmanaTipusServiceImpl.updateSetmanaTipus={} " , bundle.getMessage(Constants.ERROR_API_CRUD_SETMANES_TIPUS, null, locale));
						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final SetmanaTipusServiceImpl.updateSetmanaTipus totalTime={}, settip={}", totalTime, settip.toString());
		}
		return dto;				
	}
	
	@Override
	public ErrorDto deleteSetmanaTipus(SetmanaTipusDto settip, Locale locale) {		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici SetmanaTipusServiceImpl.deleteSetmanaTipus startTime={}, settip={}", startTime, settip.toString());
		ErrorDto dto = null;
		try {
			SetmanaTipusId id = new SetmanaTipusId();
			id.setHorari(Converter.toDao(settip.getHorari()));
			id.setDiasetCon(settip.getDiasetCon());
			id.setHorini(settip.getHorini());
			id.setHorfin(settip.getHorfin());
			SetmanaTipus dao = setmanaTipusDao.findSetmanaTipusById(id);
						
			if (dao == null) {
				return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_CRUD_AGENDA, null, locale));
			} else {
				// Validacions per esborrar setmana cita
				List<Cita> teCites = citaDao.findCitesByHorari(settip.getHorari().getCon());
				// Comprovar que l'horari no te cites
				if (teCites != null && !teCites.isEmpty() && teCites.size() > 0) {
					return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_DELETE_SETMANES_TIPUS, null, locale));
				} else {
					setmanaTipusDao.deleteSetmanaTipus(dao);
				}			
			}
		
		} catch (Exception e) {
			LOG.error("### SetmanaTipusServiceImpl.deleteSetmanaTipus:" , e);
			LOG.error("### SetmanaTipusServiceImpl.deleteSetmanaTipus={} ", bundle.getMessage(Constants.ERROR_API_DELETE_SETMANES_TIPUS, null, locale));		
			return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_DELETE_SETMANES_TIPUS, null, locale));			
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final AgendaServiceImpl.deleteAgenda totalTime={}, settip={}", totalTime, settip.toString());
		}
		return dto;	
		
	}

}
