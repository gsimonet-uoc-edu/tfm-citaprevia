package uoc.edu.citaprevia.api.service;

import java.time.LocalDateTime;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.CitaDao;
import uoc.edu.citaprevia.api.model.Cita;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;


@Service("citaPreviaService")
public class CitaServiceImpl implements CitaService{
	
	@Autowired
	private CitaDao citaDao;
	
	@Autowired
	private MessageSource bundle;
	
	@Override
	public CitaDto getCita (Long con) {
		
		CitaDto dto = new CitaDto();
		
		if (!Utils.isEmpty(con)) {
			dto = Converter.toDto(citaDao.findCitaByCon(con));
		}
		return dto; 
	}
	
	@Override
	public CitaDto saveCita (CitaDto cita, Locale locale) {
		return Converter.toDto(citaDao.saveCita(Converter.toDao(cita)));				
	}
	
	@Override
	public ErrorDto deleteCitaPersona(Long con, String numdoc, Locale locale) {
		ErrorDto dto= null;
		Cita dao = citaDao.findCitaByCon(con);
		
		if (dao == null) {
			return new ErrorDto(9999L,bundle.getMessage(Constants.MSG_ERR_DELETE_CIT, null, locale));
		} else {
			// Comprovar que la cita està assignada a la persona (numdoc) per a ser esborrada
			if (!StringUtils.upperCase(numdoc).equals(StringUtils.upperCase(dao.getNumdoc()))) {
				return new ErrorDto(9999L,bundle.getMessage(Constants.MSG_ERR_DELETE_CIT, null, locale));
			}
			citaDao.deleteCita(dao);
		}
		
		return dto;
		
	}
	
	@Override
	public ErrorDto deleteCita(Long con, Locale locale) {
		ErrorDto dto= null;
		Cita dao = citaDao.findCitaByCon(con);
		
		if (dao == null) {
			return new ErrorDto(9999L,bundle.getMessage(Constants.MSG_ERR_DELETE_CIT, null, locale));
		} else {
			citaDao.deleteCita(dao);
		}
		
		return dto;
		
	}
	
	@Override
	public CitaDto existeixCitaAgenda (Long ageCon, LocalDateTime inici, LocalDateTime fi, Long tipCitCon, Locale locale) {
		
		CitaDto cita = new CitaDto();
		
		if (!Utils.isEmpty(ageCon) && inici != null && fi != null && !Utils.isEmpty(tipCitCon)) {
			cita = Converter.toDto(citaDao.existeixCitaAgenda(ageCon, inici, fi, tipCitCon));
		}
		return cita;			
	}

}
