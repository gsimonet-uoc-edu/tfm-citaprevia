package uoc.edu.citaprevia.model;

public enum DiaSetmana {
	
	DL(1L, "Dilluns"),
	DT(2L, "Dimarts"),
	DC(3L, "Dimecres"),
	DJ(4L, "Dijous"),
	DV(5L, "Divendres"),
	DS(6L, "Dissabte"),
	DG(7L, "Diumenge");
	
	private Long value;
	private String descripcio;
	
	private DiaSetmana(Long value, String descripcio) {
		this.value = value;
		this.descripcio = descripcio;
	}

	public Long getValue() {
		return value;
	}

	public String getDescripcio() {
		return descripcio;
	}
}