package uoc.edu.citaprevia.api.utils;

import uoc.edu.citaprevia.api.model.Cita;
import uoc.edu.citaprevia.api.model.Subaplicacio;
import uoc.edu.citaprevia.api.model.TipusCita;
import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.dto.SubaplicacioDto;
import uoc.edu.citaprevia.dto.TipusCitaDto;

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
	
	public static SubaplicacioDto toDto(Subaplicacio dao) {
		SubaplicacioDto dto = new SubaplicacioDto();
		if (dao != null) {
			dto.setCoa(dao.getCoa());
			dto.setDec(dao.getDec());
			dto.setDem(dao.getDem());
		}
		return dto;
	}
	
	
	public static TipusCitaDto toDto(TipusCita dao) {
		TipusCitaDto dto = new TipusCitaDto();
		if (dao != null) {
			dto.setCon(dao.getCon());
			dto.setDec(dao.getDec());
			dto.setDem(dao.getDem());
			dto.setSubapl(toDto(dao.getSubaplicacio()));
		}
		return dto;
	}

}
