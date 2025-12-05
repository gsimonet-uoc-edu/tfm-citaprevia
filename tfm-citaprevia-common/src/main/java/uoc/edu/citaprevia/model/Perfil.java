package uoc.edu.citaprevia.model;

/**
 * Enum que representa els perfils que pot tenir un usuari al loguear-se desde la part privada
 */
public enum Perfil {
	TECNIC("TECNIC","Tècnic que atèn les cites"),
	ADMINISTRADOR("ADMINISTRADOR","Administrador");
	
	private String valor;
	private String descripcio;
	
	Perfil(String valor, String descripcio){
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
