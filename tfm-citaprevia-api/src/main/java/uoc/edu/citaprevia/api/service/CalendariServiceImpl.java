package uoc.edu.citaprevia.api.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import uoc.edu.citaprevia.api.dao.AgendaDao;
import uoc.edu.citaprevia.api.dao.CitaPreviaDao;
import uoc.edu.citaprevia.api.dao.SetmanaTipusDao;
import uoc.edu.citaprevia.api.jpa.HorariRepository;
import uoc.edu.citaprevia.api.model.Agenda;
import uoc.edu.citaprevia.api.model.SetmanaTipus;
import uoc.edu.citaprevia.dto.CalendariDto;
import uoc.edu.citaprevia.dto.DiaCalendariDto;
import uoc.edu.citaprevia.dto.FranjaDto;
import uoc.edu.citaprevia.dto.generic.ErrorDto;
import uoc.edu.citaprevia.model.TipusError;
import uoc.edu.citaprevia.util.Constants;
import uoc.edu.citaprevia.util.Utils;

@Service("calendariService")
public class CalendariServiceImpl implements CalendariService{
	
	@Autowired
    private AgendaDao agendaDao;

    @Autowired
    private CitaPreviaDao citaPreviaDao;

    @Autowired
    private SetmanaTipusDao setmanaTipusDao;
    
	@Autowired
	protected MessageSource bundle;

	private static final Logger LOGGER = LoggerFactory.getLogger(CalendariServiceImpl.class);

    @Override
    public CalendariDto getCalendariCites(String subaplCoa, Long tipcitCon, Locale locale) {
    	
    	LOGGER.info("### Inici CalendariService.getCalendariCites subaplCoa={}, tipcitCon={}", subaplCoa, tipcitCon);
    	CalendariDto calendari = new CalendariDto();
	    try {
	    	if (!Utils.isEmpty(subaplCoa) && !Utils.isEmpty(tipcitCon)) {
		        LocalDate avui = LocalDate.now();
		        YearMonth mesActual = YearMonth.from(avui);
		        LocalDate primerDia = mesActual.atDay(1);
		        LocalDate ultimDia = mesActual.atEndOfMonth();
		
		        // 1. Obtener agendas activas para este tipo de cita
		      /* List<Agenda> agendas = agendaRepository.findByHorari_TipusCita_ConAndHorari_Subapl_CoaAndDatiniLessThanEqualAndDatfinGreaterThanEqual(
		            tipcitCon, subaplCoa, ultimDia, primerDia);*/
		        List<Agenda> agendas = agendaDao.findByHorariTipusCitaConAndHorariSubaplCoaAndDatiniLessThanEqualAndDatfinGreaterThanEqual(tipcitCon, subaplCoa, ultimDia, primerDia);
		
		        if (agendas.isEmpty()) {
		            return crearCalendariBuit(mesActual);
		        }
		
		        // 2. Crear calendario base
		        //CalendariDto calendari = new CalendariDto();
		        calendari.setAny(mesActual.getYear());
		        calendari.setMes(mesActual.getMonthValue());
		        calendari.setDies(new ArrayList<>());
		
		        // 3. Para cada día del mes
		        for (LocalDate dia = primerDia; !dia.isAfter(ultimDia); dia = dia.plusDays(1)) {
		            DiaCalendariDto diaDto = new DiaCalendariDto();
		            diaDto.setDia(dia.getDayOfMonth());
		            diaDto.setDisponible(false);
		            diaDto.setFranges(new ArrayList<>());
		
		            // 4. Ver si hay citas libres ese día
		            for (Agenda agenda : agendas) {
		                List<FranjaDto> franges = obtenirFrangesLliuresPerDia(agenda, dia, tipcitCon);
		                if (!franges.isEmpty()) {
		                    diaDto.setDisponible(true);
		                    diaDto.getFranges().addAll(franges);
		                }
		            }
		
		            calendari.getDies().add(diaDto);
		        }
		
		        return calendari;
	    	} else {
				String errDem = bundle.getMessage(Constants.MSG_ERR_GET_CALENDARI, null, locale);
				LOGGER.error("[ERROR] {}: ", errDem);
				calendari.addError(new ErrorDto(Constants.CODI_ERROR_FATAL,TipusError.ERROR.getValor(), errDem));
	    	}
	    	
		} catch (Exception e) {
			String errDem = bundle.getMessage(Constants.MSG_ERR_FATAL, null, locale);
			LOGGER.error("[ERROR] {}: ", errDem, e);
			e.printStackTrace();
			calendari.addError(new ErrorDto(Constants.CODI_ERROR_FATAL,TipusError.ERROR.getValor(), errDem));
			
		} finally {
	    	LOGGER.info("### Final CalendariService.getCalendariCites subaplCoa={}, tipcitCon={}, calendari={}", subaplCoa, tipcitCon, calendari.toString());
		}
	    
	    return calendari;
    
  }

    // === MÉTODOS AUXILIARES ===

    private List<FranjaDto> obtenirFrangesLliuresPerDia(Agenda agenda, LocalDate dia, Long tipcitCon) {
        List<FranjaDto> franges = new ArrayList<>();

        // Obtener horario del día
        DayOfWeek diaSetmana = dia.getDayOfWeek();
        Long diasetCon = (long) (diaSetmana.getValue()); // 1=Lunes, 7=Domingo

        /*List<SetmanaTipus> setmanes = setmanaTipusRepository.findById_Horari_ConAndId_DiasetCon(
            agenda.getHorari().getCon(), diasetCon);*/
        List<SetmanaTipus> setmanes  = setmanaTipusDao.findByIdHorariConAndIdDiasetCon(agenda.getHorari().getCon(), diasetCon);

        for (SetmanaTipus st : setmanes) {
            LocalDateTime inici = LocalDateTime.of(dia, st.getId().getHorini().toLocalTime());
            LocalDateTime fi = LocalDateTime.of(dia, st.getId().getHorfin().toLocalTime());

            // Dividir en franjas de 30 min (ejemplo)
            LocalDateTime current = inici;
            while (current.isBefore(fi)) {
                LocalDateTime slotFi = current.plusMinutes(30);

                boolean lliure = !citaPreviaDao.existsByAgendaConAndDathorini(agenda.getCon(), current);

                FranjaDto franja = new FranjaDto();
                franja.setHoraInici(current.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                franja.setHoraFi(slotFi.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                franja.setAgendaCon(agenda.getCon());
                franja.setLliure(lliure);

                franges.add(franja);
                current = slotFi;
            }
        }

        return franges;
    }

    private CalendariDto crearCalendariBuit(YearMonth mes) {
        CalendariDto calendari = new CalendariDto();
        calendari.setAny(mes.getYear());
        calendari.setMes(mes.getMonthValue());
        calendari.setDies(new ArrayList<>());

        for (int i = 1; i <= mes.lengthOfMonth(); i++) {
            DiaCalendariDto dia = new DiaCalendariDto();
            dia.setDia(i);
            dia.setDisponible(false);
            calendari.getDies().add(dia);
        }
        return calendari;
    }
}

