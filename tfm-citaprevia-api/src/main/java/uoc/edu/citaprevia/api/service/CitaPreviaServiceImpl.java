package uoc.edu.citaprevia.api.service;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.CitaPreviaDao;
import uoc.edu.citaprevia.api.utils.Converter;
import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.util.Utils;


@Service("citaPreviaService")
public class CitaPreviaServiceImpl implements CitaPreviaService{
	
	@Autowired
	private CitaPreviaDao citaPreviaDao;
	
	@Override
	public CitaDto getCita (Long con) {
		
		CitaDto dto = new CitaDto();
		
		if (!Utils.isEmpty(con)) {
			dto = Converter.toDto(citaPreviaDao.findCitaByCon(con));
		}
		return dto; 
	}
	
	@Override
	public CitaDto saveCita (CitaDto cita, Locale locale) {
		return Converter.toDto(citaPreviaDao.saveCita(Converter.toDao(cita)));				
	}
	
	@Override
	public boolean existeixCita (Long agendaCon, LocalDateTime fi, LocalDateTime inici) {
		return citaPreviaDao.existeixCita(agendaCon, fi, inici);	
	}
	
	@Override
	public CitaDto existeixCitaAgenda (Long ageCon, LocalDateTime inici, LocalDateTime fi, Long tipcitCon) {
		
		CitaDto cita = new CitaDto();
		
		if (!Utils.isEmpty(ageCon) && inici != null && fi != null && !Utils.isEmpty(tipcitCon)) {
			cita = Converter.toDto(citaPreviaDao.existeixCitaAgenda(ageCon, inici, fi, tipcitCon));
		}
		return cita;			
	}

}
