package uoc.edu.citaprevia.model;

public enum SiNo {
	S("S","Si"),
	N("N", "No");
	
	private String valor;
	private String descripcio;
	
	SiNo(String valor, String descripcio){
		this.valor = valor;
		this.descripcio = descripcio;
	}
	
	public String getValor(){
		return valor;
	}
	
	public String getDescripcio(){
		return descripcio;
	}
	
	public static SiNo valueOf(Boolean bool) {
		return bool != null && bool ? SiNo.S : SiNo.N;
	}
}