package uoc.edu.citaprevia.api.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uoc.edu.citaprevia.api.jpa.UbicacioRepository;
import uoc.edu.citaprevia.api.model.Ubicacio;

@Component
public class UbicacioDao {
	
	@Autowired
	private UbicacioRepository ubicacioRepository;
	
	public List<Ubicacio> findUbicacions () {
		return ubicacioRepository.findAll();
	}

}
