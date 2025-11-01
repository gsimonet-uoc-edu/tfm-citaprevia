package uoc.edu.citaprevia.api.utils;

import uoc.edu.citaprevia.api.model.Agenda;
import uoc.edu.citaprevia.api.model.Cita;
import uoc.edu.citaprevia.api.model.Horari;
import uoc.edu.citaprevia.api.model.SetmanaTipus;
import uoc.edu.citaprevia.api.model.Subaplicacio;
import uoc.edu.citaprevia.api.model.Tecnic;
import uoc.edu.citaprevia.api.model.TipusCita;
import uoc.edu.citaprevia.api.model.Ubicacio;
import uoc.edu.citaprevia.dto.AgendaDto;
import uoc.edu.citaprevia.dto.CitaDto;
import uoc.edu.citaprevia.dto.HorariDto;
import uoc.edu.citaprevia.dto.SetmanaTipusDto;
import uoc.edu.citaprevia.dto.SubaplicacioDto;
import uoc.edu.citaprevia.dto.TecnicDto;
import uoc.edu.citaprevia.dto.TipusCitaDto;
import uoc.edu.citaprevia.dto.UbicacioDto;

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
	
	public static UbicacioDto toDto(Ubicacio dao) {
		UbicacioDto dto = new UbicacioDto();
		if (dao != null) {
			dto.setCon(dao.getCon());
			dto.setNom(dao.getNom());
			dto.setObs(dao.getObs());
		}
		return dto;
	}
	
	public static HorariDto toDto(Horari dao) {
		HorariDto dto = new HorariDto();
		if (dao != null) {
			dto.setCon(dao.getCon());
			dto.setDec(dao.getDec());
			dto.setDem(dao.getDem());
			dto.setNotval(dao.getNotval());
			dto.setSubapl(toDto(dao.getSubapl()));
			dto.setTipusCita(toDto(dao.getTipusCita()));
		}
		return dto;
	}
	
	public static TecnicDto toDto(Tecnic dao) {
		TecnicDto dto = new TecnicDto();
		if (dao != null) {
			dto.setCoa(dao.getCoa());
			dto.setLl1(dao.getLl1());
			dto.setLl2(dao.getLl2());
			dto.setNom(dao.getNom());
			dto.setNotval(dao.getNotval());
		}
		return dto;
	}
	
	
	public static AgendaDto toDto(Agenda dao) {
		AgendaDto dto = new AgendaDto();
		if (dao != null) {
			dto.setCentre(toDto(dao.getCentre()));
			dto.setCon(dao.getCon());
			dto.setDatini(dao.getDatini());
			dto.setDatfin(dao.getDatfin());
			dto.setHorari(toDto(dao.getHorari()));
			dto.setTecnic(toDto(dao.getTecnic()));
		}
		return dto;
	}
	
	public static SetmanaTipusDto toDto(SetmanaTipus dao) {
		SetmanaTipusDto dto = new SetmanaTipusDto();
		if (dao != null) {
			if (dao.getId() != null) {
				dto.setHorari(toDto(dao.getId().getHorari()));
				dto.setHorini(dao.getId().getHorini());
				dto.setHorfin(dao.getId().getHorfin());
				dto.setDiasetCon(dao.getId().getDiasetCon());
			}
			dto.setTipcit(toDto(dao.getTipcit()));
		}
		return dto;
	}

}
