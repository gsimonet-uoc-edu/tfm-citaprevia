package uoc.edu.citaprevia.api.jpa;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import uoc.edu.citaprevia.api.model.SetmanaTipus;
import uoc.edu.citaprevia.api.model.SetmanaTipusId;

public interface SetmanaTipusRepository extends PagingAndSortingRepository<SetmanaTipus, SetmanaTipusId>{

	List<SetmanaTipus> findByIdHorariConAndIdDiasetCon (Long horCon, Long diasetCon);

}
