package uoc.edu.citaprevia.api.service;

import java.time.LocalDateTime;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.CitaDao;
import uoc.edu.citaprevia.api.model.Cita;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.dto.HorariDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;


@Service("citaPreviaService")
public class CitaServiceImpl implements CitaService{
	
	@Autowired
	private CitaDao citaDao;
	
	@Autowired
	private MessageSource bundle;
	
	private static final Logger LOG = LoggerFactory.getLogger(CitaServiceImpl.class);

	
	@Override
	public CitaDto getCita (Long con, Locale locale) {
		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici CitaServiceImpl.getCita startTime={}, citCon={}", startTime, con);
		CitaDto cita = new CitaDto();
		try {
			if (!Utils.isEmpty(con)) {
				cita = Converter.toDto(citaDao.findCitaByCon(con));
			}
		} catch (Exception e) {
			cita.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale)));
			LOG.error("### Error CitaServiceImpl.getCita:" , e);
			LOG.error("### Error CitaServiceImpl.getCita={} " , bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale));						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final CitaServiceImpl.getCita totalTime={}, citCon={}, cita={}", totalTime, con, cita.toString());
		}
		return cita; 
	}
	
	@Override
	public CitaDto saveCita (CitaDto cita, Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici CitaServiceImpl.saveCita startTime={}, cita={}", startTime, cita.toString());
		CitaDto dto = new CitaDto();
		try {
			
			Cita citaConcurrent = citaDao.existeixCitaAgenda(cita.getAgenda().getCon(), cita.getDathorini(), cita.getDathorfin(), cita.getAgenda().getHorari().getTipusCita().getCon());
			// Comprovar concurrència, que al mateix temps dos usuaris distints estan emplenant les dades de la mateixa cita.
			if (citaConcurrent != null && !Utils.isEmpty(citaConcurrent.getCon())) {
				dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CITA_CONCURRENT, null, locale)));
			} else {
				dto = Converter.toDto(citaDao.saveCita(Converter.toDao(cita)));
			}
			
		
		} catch (Exception e) {
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale)));
			LOG.error("### Error CitaServiceImpl.saveCita:" , e);
			LOG.error("### Error CitaServiceImpl.saveCita: " , bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale));
						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final CitaServiceImpl.saveCita totalTime={}, citaSaved={}", totalTime, dto.toString());
		}
		
		return dto;
		
	}
	
	@Override
	public CitaDto updateCita (CitaDto cita, Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici CitaServiceImpl.updateCita startTime={}, cita={}", startTime, cita.toString());
		CitaDto dto = new CitaDto();
		try {
			dto = Converter.toDto(citaDao.updateCita(Converter.toDao(cita)));		
		} catch (Exception e) {
			dto.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale)));
			LOG.error("### Error CitaServiceImpl.updateCita:" , e);
			LOG.error("### Error CitaServiceImpl.updateCita: " , bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale));
						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final CitaServiceImpl.updateCita totalTime={}, citaUpdated={}", totalTime, dto.toString());
		}
		return dto;
	}
	
	@Override
	public ErrorDto deleteCitaPersona(Long con, String numdoc, Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici CitaServiceImpl.deleteCitaPersona startTime={}, citCon={}, numdoc={}", startTime, con, numdoc);
		ErrorDto dto = null;
		try {
			Cita dao = citaDao.findCitaByCon(con);
			
			if (dao == null) {
				return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale));
			} else {
				// Comprovar que la cita està assignada a la persona (numdoc) per a ser esborrada
				if (!StringUtils.upperCase(numdoc).equals(StringUtils.upperCase(dao.getNumdoc()))) {
					return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale));
				}
				citaDao.deleteCita(dao);
			}
		} catch (Exception e) {
			LOG.error("### Error CitaServiceImpl.deleteCitaPersona:" , e);
			LOG.error("### Error CitaServiceImpl.deleteCitaPersona={} ", bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale));		
			return new ErrorDto(Constants.CODI_ERROR_FATAL,bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale));

		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final CitaServiceImpl.deleteCitaPersona totalTime={}, citCon={}, numdo={}", totalTime, con, numdoc);
		}		
		return dto;
		
	}
	
	@Override
	public ErrorDto deleteCita(Long con, Locale locale) {
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici CitaServiceImpl.deleteCita startTime={}, citCon={}", startTime, con);
		ErrorDto dto = null;
		try {

			Cita dao = citaDao.findCitaByCon(con);
			
			if (dao == null) {
				return new ErrorDto(9999L,bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale));
			} else {
				citaDao.deleteCita(dao);
			}
		} catch (Exception e) {
			LOG.error("### Error CitaServiceImpl.deleteCita:" , e);
			LOG.error("### Error CitaServiceImpl.deleteCita={} ", bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale));		
			return new ErrorDto(Constants.CODI_ERROR_FATAL,bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale));

		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final CitaServiceImpl.deleteAgenda totalTime={}, citCon={}", totalTime, con);
		}
		return dto;
		
	}
	
	@Override
	public CitaDto existeixCitaAgenda (Long ageCon, LocalDateTime inici, LocalDateTime fi, Long tipCitCon, Locale locale) {
		
		long startTime=System.currentTimeMillis();
		LOG.info("### Inici CitaServiceImpl.existeixCitaAgenda startTime={}, ageCon={}, inici={}, fi={}, tipCitCon={}", startTime, ageCon, inici, fi, tipCitCon);
		CitaDto cita = new CitaDto();
		try {
				
			if (!Utils.isEmpty(ageCon) && inici != null && fi != null && !Utils.isEmpty(tipCitCon)) {
				cita = Converter.toDto(citaDao.existeixCitaAgenda(ageCon, inici, fi, tipCitCon));
			}
			
		} catch (Exception e) {
			cita.addError(new ErrorDto(Constants.CODI_ERROR_FATAL, bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale)));
			LOG.error("### Error CitaServiceImpl.existeixCitaAgenda:" , e);
			LOG.error("### Error CitaServiceImpl.existeixCitaAgenda={} " , bundle.getMessage(Constants.ERROR_API_CRUD_CITA, null, locale));
						
		} finally {
			long totalTime = (System.currentTimeMillis() - startTime);
			LOG.info("### Final CitaServiceImpl.existeixCitaAgenda totalTime={}, ageCon={}, inici={}, fi={}, tipCitCon={, cita={}", totalTime, ageCon, inici, fi, tipCitCon, cita.toString());
		}
		return cita;			
	}

}
