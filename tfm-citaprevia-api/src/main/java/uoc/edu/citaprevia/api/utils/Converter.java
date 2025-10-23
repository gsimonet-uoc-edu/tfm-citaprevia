package uoc.edu.citaprevia.api.utils;

import uoc.edu.citaprevia.api.model.Cita;
import uoc.edu.citaprevia.dto.CitaDto;

public class Converter {
	
	public static CitaDto toDto(Cita dao) {
		CitaDto dto = new CitaDto();
		if (dao != null) {
			dto.setCon(dao.getCon());
			dto.setDathorini(dao.getDathorini());
			dto.setDathorfin(dao.getDathorfin());
			dto.setObs(dao.getObs());
		}
		return dto;
	}
	
	public static Cita toDao (CitaDto dto) {
		Cita dao = new Cita();
		if (dto != null) {
			dao.setCon(dto.getCon());
			dao.setDathorini(dto.getDathorini());
			dao.setDathorfin(dto.getDathorfin());
			dao.setObs(dto.getObs());
		}
		
		return dao;
	}

}
