package uoc.edu.citaprevia.model;

/**
 * Enum per centralitzar i gestionar les modalitats que pot tenir una cita
 */
public enum ModalitatTipusCita {
	P("P","Presencial"),
	T("T","Telefonica"),
	V("V", "Video Conferencia");
	
	private String valor;
	private String descripcio;
	
	ModalitatTipusCita(String valor, String descripcio){
		this.valor = valor;
		this.descripcio = descripcio;
	}
	
	public String getValor(){
		return valor;
	}
	
	public String getDescripcio(){
		return descripcio;
	}
}

