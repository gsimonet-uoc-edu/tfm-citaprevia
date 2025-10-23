package uoc.edu.citaprevia.model;

public enum ModalitatTipusCita {
	P("P","Presencial"),
	T("T","Telèfon"),
	V("V", "Video Conferència");
	
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

