package uoc.edu.citaprevia.api.jpa;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import uoc.edu.citaprevia.api.model.SetmanaTipus;
import uoc.edu.citaprevia.api.model.SetmanaTipusId;

public interface SetmanaTipusRepository extends PagingAndSortingRepository<SetmanaTipus, SetmanaTipusId>{

	List<SetmanaTipus> findByIdHorariConAndIdDiasetCon (Long horCon, Long diasetCon);
	List<SetmanaTipus> findByIdHorariCon(Long horCon);
	SetmanaTipus findByIdHorariConAndIdDiasetConAndIdHoriniAndIdHorfin(Long horCon, Long diasetCon, LocalTime horini, LocalTime horfin);

}
