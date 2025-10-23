package uoc.edu.citaprevia.model;

public enum TipusError {
	
	ERROR("E","Error"),
	ALERTA("A","Alerta"),
	INFO("I", "Informatiu");

	private String valor;
	private String descripcio;
	
	
	private TipusError(String valor, String descripcio) {
		this.valor = valor;
		this.descripcio = descripcio;
	}

	public String getValor() {
		return valor;
	}

	public String getDescripcio() {
		return descripcio;
	}

}