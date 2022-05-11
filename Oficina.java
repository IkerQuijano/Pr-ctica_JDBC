package JDBC;

public class Oficina {

//Atributos
	
	private int idOficina;
	private String ciudad;
	private int superficie;
	private double ventas;
	
	
//constructor
	
	public Oficina(int oficina, String ciudad, int superficie, double ventas) {
		this.idOficina = oficina;
		this.ciudad = ciudad;
		this.superficie = superficie;
		this.ventas = ventas;
	}

	
//Getters y Setters
	
	public int getIdOficina() {
		return idOficina;
	}


	public void setIdOficina(int oficina) {
		this.idOficina = oficina;
	}
	
	
//Metodos
	
	//toString
	@Override
	public String toString() {
		return "\t - " + idOficina + " " + ciudad + ". S: " + superficie + "m2, ventas: " + ventas + " uds."; 
	}
}
