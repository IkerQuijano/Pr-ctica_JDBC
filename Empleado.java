package JDBC;

import java.time.*;

public class Empleado {
	
//Atributos
	
	private String nombre;
	private int numemp;
	private int edad;
	private String puesto;
	private LocalDate contrato;
	private int idOficina;
	
	
//Constructor
	
	public Empleado(int numemp, String nombre, int edad, String puesto, LocalDate contrato, Oficina oficina) {
		this.nombre = nombre;
		this.numemp = numemp;
		this.edad = edad;
		this.puesto = puesto;
		this.contrato = contrato;
		this.idOficina = oficina.getIdOficina();
	}
	
	public Empleado(int numemp, String nombre, int edad, String puesto, LocalDate contrato, int oficina) {
		this.nombre = nombre;
		this.numemp = numemp;
		this.edad = edad;
		this.puesto = puesto;
		this.contrato = contrato;
		this.idOficina = oficina;
	}

	
//Getters y Setters
	
	//Nombre
	
		public String getNombre() {
			return nombre;
		}
	
		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

	
	//Numemp	
		
	public int getNumemp() {
		return numemp;
	}

	public void setNumemp(int numemp) {
		this.numemp = numemp;
	}

	
	//Edad
	
		public int getEdad() {
			return edad;
		}
	
		public void setEdad(int edad) {
			this.edad = edad;
		}

		
	//Puesto	
		
		public String getPuesto() {
			return puesto;
		}
	
		public void setPuesto(String puesto) {
			this.puesto = puesto;
		}

		
	//Fecha_Contrato	
		
		public LocalDate getContrato() {
			return contrato;
		}
	
		public void setContrato(LocalDate contrato) {
			this.contrato = contrato;
		}

		
	//IdOficina	
		
		public int getIdOficina() {
			return idOficina;
		}
	
		public void setIdOficina(int idOficina) {
			this.idOficina = idOficina;
		}
		
	
}
