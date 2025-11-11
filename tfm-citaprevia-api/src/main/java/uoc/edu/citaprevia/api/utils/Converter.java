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
			dto.setAgenda(toDto(dao.getAgenda()));
			dto.setEma(dao.getEma());
			dto.setLit5(dao.getLit05e());
			dto.setLit6(dao.getLit06e());
			dto.setLit7(dao.getLit07e());
			dto.setLit8(dao.getLit08e());
			dto.setLit9(dao.getLit09e());
			dto.setLit10(dao.getLit10e());
			dto.setLit1(dao.getLit1er());
			dto.setLit2(dao.getLit2on());
			dto.setLit3(dao.getLit3er());
			dto.setLit4(dao.getLit4rt());
			dto.setNomcar(dao.getNomcar());
			dto.setNom(dao.getNom());
			dto.setLlis(dao.getLlis());
			dto.setTel(dao.getTel());
			dto.setTipusCita(toDto(dao.getTipcit()));
			dto.setNumdoc(dao.getNumdoc());
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
			dao.setAgenda(toDao(dto.getAgenda()));
			dao.setEma(dto.getEma());
			dao.setLit05e(dto.getLit5());
			dao.setLit06e(dto.getLit6());
			dao.setLit07e(dto.getLit7());
			dao.setLit08e(dto.getLit8());
			dao.setLit09e(dto.getLit9());
			dao.setLit10e(dto.getLit10());
			dao.setLit1er(dto.getLit1());
			dao.setLit2on(dto.getLit2());
			dao.setLit3er(dto.getLit3());
			dao.setLit4rt(dto.getLit4());
			dao.setNom(dto.getNom());
			dao.setNomcar(dto.getNomcar());
			dao.setLlis(dto.getLlis());
			dao.setTel(dto.getTel());
			dao.setTipcit(toDao(dto.getTipusCita()));
			dao.setNumdoc(dto.getNumdoc());
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
	
	public static Subaplicacio toDao(SubaplicacioDto dto) {
		Subaplicacio dao = new Subaplicacio();
		if (dao != null) {
			dao.setCoa(dto.getCoa());
			dao.setDec(dto.getDec());
			dao.setDem(dto.getDem());
		}
		return dao;
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
	
	public static TipusCita toDao(TipusCitaDto dto) {
		TipusCita dao = new TipusCita();
		if (dto != null) {
			dao.setCon(dto.getCon());
			dao.setDec(dto.getDec());
			dao.setDem(dto.getDem());
			dao.setSubaplicacio(toDao(dto.getSubapl()));
		}
		return dao;
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
	
	public static Ubicacio toDao(UbicacioDto dto) {
		Ubicacio dao = new Ubicacio();
		if (dto != null) {
			dao.setCon(dto.getCon());
			dao.setNom(dto.getNom());
			dao.setObs(dto.getObs());
		}
		return dao;
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
	
	public static Horari toDao(HorariDto dto) {
		Horari dao = new Horari();
		if (dto != null) {
			dao.setCon(dto.getCon());
			dao.setDec(dto.getDec());
			dao.setDem(dto.getDem());
			dao.setNotval(dto.getNotval());
			dao.setSubapl(toDao(dto.getSubapl()));
			dao.setTipusCita(toDao(dto.getTipusCita()));
		}
		return dao;
	}
	
	public static TecnicDto toDto(Tecnic dao) {
		TecnicDto dto = new TecnicDto();
		if (dao != null) {
			dto.setCoa(dao.getCoa());
			dto.setPass(dao.getPass());
			dto.setLl1(dao.getLl1());
			dto.setLl2(dao.getLl2());
			dto.setNom(dao.getNom());
			dto.setNotval(dao.getNotval());
			dto.setPrf(dao.getPrf());
		}
		return dto;
	}
	
	public static Tecnic toDao(TecnicDto dto) {
		Tecnic dao = new Tecnic();
		if (dto != null) {
			dao.setCoa(dto.getCoa());
			dao.setPass(dto.getPass());
			dao.setLl1(dto.getLl1());
			dao.setLl2(dto.getLl2());
			dao.setNom(dto.getNom());
			dao.setNotval(dto.getNotval());
			dao.setPrf(dto.getPrf());
		}
		return dao;
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
	
	public static Agenda toDao(AgendaDto dto) {
		Agenda dao = new Agenda();
		if (dto != null) {
			dao.setCentre(toDao(dto.getCentre()));
			dao.setCon(dto.getCon());
			dao.setDatini(dto.getDatini());
			dao.setDatfin(dto.getDatfin());
			dao.setHorari(toDao(dto.getHorari()));
			dao.setTecnic(toDao(dto.getTecnic()));
		}
		return dao;
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
