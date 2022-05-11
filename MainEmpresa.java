package JDBC;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;  
import java.util.*;
import java.util.Map.Entry;

public class MainEmpresa {

//Atributos
	
	private static Connection conexion = null;
	private static Statement miStatement;
	private static String usuario = "pardillo";
	private static String contrasenia = "pardillo";
	private static String BD = "empresa";
	private static String puerto = "3333";
	private static String maquina = "localhost";
	private static String url = "jdbc:mysql://" + maquina + ":" + puerto + "/" + BD;
	
	static Scanner leer = new Scanner(System.in);
	
	static Map<Integer, String> mapaOficinas = new HashMap<>();		
	
	static final int topeEdadMaximo = 65;
	static final int topeEdadMinimo = 0;

//M�todos	
		
		
	public static void conexion() throws SQLException {
			
		if(conexion == null) {
			conexion = DriverManager.getConnection(url, usuario, contrasenia);
			miStatement = conexion.createStatement();
		}
		
		System.out.println();
	}
	
	
	
	public static void uno() {
		
		String query = "SELECT * FROM empleado;";
		ResultSet resultado;
		
		
		try {
			conexion();
			resultado = miStatement.executeQuery(query);

			while(resultado.next()) {
				System.out.println("nombre: " + resultado.getString("nombre") + " id: " + resultado.getInt("numemp") + ", edad: " + resultado.getString("edad")
						+ " oficina: " + resultado.getInt("oficina") + " puesto: " + resultado.getString("puesto") + " contrato: " + resultado.getString("contrato"));
			}
			
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		

	}
	
	
	public static List<Oficina> dos() {
		
		String query = "SELECT * FROM oficina;";
		ResultSet resultado;
		List<Oficina> lista = new ArrayList<>();
		
		
		try {
			conexion();
			resultado = miStatement.executeQuery(query);

			while(resultado.next()) {
				lista.add(new Oficina(resultado.getInt("oficina"), resultado.getString("ciudad"),
						resultado.getInt("superficie"), resultado.getInt("ventas")));

			}

		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println();
		
		if(lista.size() == 0) {
			System.out.println("No hay oficinas para mostrar.");
		}
		
		else {
			System.out.println("Lista de Oficinas:");
			for (Oficina a : lista) {
				System.out.println(a);
			}
		}
		
		return lista;
		
	}
	
	
	public static List<Oficina> tres() {
		
		String query = "SELECT * FROM oficina WHERE ciudad = ?;";
		ResultSet resultado;
		PreparedStatement prepareStatement;
		String ciudadDeseada;
		List<Oficina> lista = new ArrayList<>();
		
		System.out.println("Introduce una ciudad:");
		ciudadDeseada = leer.next();
		
		try {
			conexion();
			prepareStatement = conexion.prepareStatement(query);
			prepareStatement.setString(1, ciudadDeseada);
			resultado = prepareStatement.executeQuery();
			

			while(resultado.next()) {
				lista.add(new Oficina(resultado.getInt("oficina"), ciudadDeseada,
							resultado.getInt("superficie"), resultado.getInt("ventas")));
			}
			
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println();
		
		if(lista.size() == 0) {
			System.out.println("No hay oficinas para mostrar.");
		}
		
		else {
			System.out.println("Lista de Oficinas:");
			for (Oficina a : lista) {
				System.out.println(a);
			}
		}
		
		return lista;
		
	}
	
	
	
	public static void cuatro() {
		
		int edadMinima;
		int edadMaxima;
		int cambio;		
		
		
		String query = "SELECT * FROM empleado WHERE edad BETWEEN ? AND ?;";
		ResultSet resultado;
		PreparedStatement prepareStatement;
		
		System.out.println("Mostrar empleados entre un rango de edad.");
		System.out.println("Edad minima:");
			edadMinima = leer.nextInt();
			
				while(edadMinima < topeEdadMinimo || edadMinima > topeEdadMaximo) {
					System.out.println("Ese valor esta fuera del rango permitido.");
					edadMinima = leer.nextInt();
					
				}
		
		System.out.println("Edad maxima:");
			edadMaxima = leer.nextInt();
			
				while(edadMaxima < topeEdadMinimo || edadMaxima > topeEdadMaximo) {
					System.out.println("Ese valor esta fuera del rango permitido.");
					edadMaxima = leer.nextInt();
					
				}
			
			if(edadMinima > edadMaxima) {
				cambio = edadMaxima;
				edadMaxima = edadMinima;
				edadMinima = cambio;
				System.out.println("Como la edad m�nima era menor que la m�xima, se han intercambiado.");	
			}
			
		try {
			conexion();
			prepareStatement = conexion.prepareStatement(query);
			prepareStatement.setInt(1, edadMinima);
			prepareStatement.setInt(2, edadMaxima);
			resultado = prepareStatement.executeQuery();
			
			if(!resultado.next()) {
				System.out.println("No hay empleados para mostrar en ese rango de edad.");
			}
			
			else {
				System.out.println("Empleados:");
				
					while(resultado.next()) {	
						System.out.println(resultado.getString("nombre") + " " + resultado.getInt("edad"));
					}
			}
			
				
			
		} 
		
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
		
	public static void cinco() {
		
		//Variables		
		
		int numemp;
		String nombre;
		int edad;
		int fkIdOficina;
		String puesto;
		LocalDate fecha_contrato = java.time.LocalDate.now();
		
		ResultSet resultado;
		ResultSet resultadoFk;
		String queryNumemp = "SELECT numemp + 1 AS id FROM empleado ORDER BY numemp DESC LIMIT 1;";
		String queryOficina = "SELECT * FROM oficina;";
		String insertEmpleado = "";
		
		String eleccion;
		int idOficinaMayor = 0;
		
		
		
		//Programa el id es el mayor que haya ya en la BD
		
		System.out.println("Creacion de un empleado:");	
			System.out.println("Nombre:");
				nombre = leer.next();
			
			System.out.println("Edad:");
				edad = leer.nextInt();
				
					while(edad < topeEdadMinimo || edad > topeEdadMaximo) {
						System.out.println("Ese valor no es valido:");
						edad = leer.nextInt();
					}
			
			System.out.println("Puesto:");
				puesto = leer.next();
				
			
		try {
			conexion();
			resultado = miStatement.executeQuery(queryNumemp);
				
				if(resultado.next()) {
						numemp = resultado.getInt("id");
					}
					
					else {
						numemp = 1; //si no hay ning�n empleado va a darle el valor 1
					}
				
				
				resultadoFk = miStatement.executeQuery(queryOficina);
				
				/* Comprueba si hay oficinas creadas, si no, va a preguntar si quiere crear una. Si la crea, la asigna automaticamente al empleado */
				
					if(resultadoFk.next()) {
						
						System.out.println("Estas son las oficinas disponibles, �a cual pertenece el empleado?");
						
						while(resultadoFk.next()) {
								System.out.println(resultadoFk.getInt("oficina") + " " + resultadoFk.getString("ciudad"));
								idOficinaMayor = resultadoFk.getInt("oficina");
							}
							
							System.out.println("�A que oficina pertenece? Escribe un numero y si no es ninguna escribe -1:");
							fkIdOficina = leer.nextInt();
							
								if(fkIdOficina != -1) {
									while(fkIdOficina < -1 || fkIdOficina > idOficinaMayor) {
										System.out.println("Esa opcion no es valida.");
										fkIdOficina = leer.nextInt();
									}
									
						
										insertEmpleado = "INSERT INTO empleado VALUES (" + numemp + ", '" + 
												nombre + "', " + edad + ", " + fkIdOficina + ", '" + puesto + "','" + fecha_contrato + "');";
								}
								
					}
						
				/* Si no hay oficinas creadas */				
								
					else {
						System.out.println("Cada empleado debe pertencer a una oficina, pero no hay oficinas creadas."
								+ "�Quieres crear una? S/N");
						
						eleccion = leer.next();
						
							while(!eleccion.equalsIgnoreCase("s") && !eleccion.equalsIgnoreCase("n")) {
								System.out.println("�Quieres crear una? S/N");
								eleccion = leer.next();
							}
							
					/* Si quiere crear una oficina */		
							
						if(eleccion.equalsIgnoreCase("s")) {
														
							insertEmpleado = "INSERT INTO empleado VALUES (" + numemp + ", '" + 
									nombre + "', " + edad + ", " + creaOficinas(resultadoFk) + ", '" + puesto + "', '" + fecha_contrato + "');";
							

						}
						
						else if(eleccion.equalsIgnoreCase("n")){
							System.out.println("De acuerdo, saliendo de generar un empleado.");
						}
					
					}
					
										
							if(miStatement.executeUpdate(insertEmpleado) == 1) {
								System.out.println("Insercion hecha.");
							}
							
							else {
								System.out.println("Insercion fallida.");
							}
							
					
		}
			
		catch(SQLException e) {
			e.printStackTrace();
		}
				
			
	}
	
	/* Devuelve el idOficina */ 
	
	public static int creaOficinas(ResultSet resultadoFk) throws SQLException {
				
		String querySacaOficinaMayor = "SELECT id_oficina + 1FROM oficina ORDER BY id_oficina DESC LIMIT 1;";
		String insertOficina = "";
		int idOficinaNueva = 0;
		ResultSet resultado;
		
		System.out.println("Ciudad:");
			String ciudad = leer.next();
				
		System.out.println("Superficie:");
			int superficie = leer.nextInt();
				
		System.out.println("Ventas:");
			double ventas = leer.nextDouble();
				
		/* Como no hay oficinas, va a tener id 1*/	
				
			Oficina oficina = new Oficina(1, ciudad, superficie, ventas);
			
		
			if(resultadoFk.next()) {
				resultado = miStatement.executeQuery(querySacaOficinaMayor);
				
				idOficinaNueva  = resultado.getInt("id_oficina");
				insertOficina = "INSERT INTO oficina VALUES (" + idOficinaNueva + ", '" + ciudad + "', " + superficie + ", " + ventas + ");";
			}
			
		/* Si no hay oficinas creadas antes. Es para poner el id a 1 */	
			
			else {
				insertOficina = "INSERT INTO oficina VALUES (" + 1 + ", '" + ciudad + "', " + superficie + ", " + ventas + ");";
			}
			
			
			if(miStatement.executeUpdate(insertOficina) == 1) {
				System.out.println("Oficina creada.");
				
			}
			
			else {
				System.out.println("Error al crear una oficina, saliendo del programa.");
			}
			
		
		return idOficinaNueva; 
	}
	
	
	/*
	
	public static void seis() {
			
			//Variables		
			
			int numemp;
			String nombre;
			int edad;
			int fkIdOficina = 0;
			String puesto;
			LocalDate fecha_contrato = java.time.LocalDate.now();
			
			ResultSet resultado;
			ResultSet resultadoFk;
			String queryNumemp = "SELECT numemp + 1 AS id FROM empleado ORDER BY numemp DESC LIMIT 1;";
			String queryOficina = "SELECT * FROM oficina;";
			String insertEmpleado = "";
			String insertOficina = "";
			int idOficinaMayor = 0;
		
			
			
			//Programa el id es el mayor que haya ya en la BD
			
			System.out.println("Creacion de un empleado:");	
				System.out.println("Nombre:");
					nombre = leer.next();
				
				System.out.println("Edad:");
					edad = leer.nextInt();
				
				System.out.println("Puesto:");
					puesto = leer.next();
					
				
			try {
					resultado = miStatement.executeQuery(queryNumemp);
					
					if(resultado.next()) {
							numemp = resultado.getInt("id");
						}
						
						else {
							numemp = 1; //si no hay ning�n empleado va a darle el valor 1
						}
					
					
					resultadoFk = miStatement.executeQuery(queryOficina);
					
					/* Comprueba si hay oficinas creadas, si no, va a preguntar
					
						if(resultado.next()) {
							
							System.out.println("Estas son las oficinas disponibles, �a cual pertenece el empleado?");
							
							while(resultadoFk.next()) {
									System.out.println(resultadoFk.getInt("oficina") + " " + resultadoFk.getString("ciudad"));
									idOficinaMayor = resultadoFk.getInt("oficina");
								}
								
								System.out.println("�A que oficina pertenece? Escribe un numero y si no es ninguna escribe -1:");
								fkIdOficina = leer.nextInt();
								
									if(fkIdOficina != -1) {
										while(fkIdOficina < -1 || fkIdOficina > idOficinaMayor) {
											System.out.println("Esa opcion no es valida.");
											fkIdOficina = leer.nextInt();
										}
										
										Empleado empleado = new Empleado(numemp, nombre, edad, puesto, fecha_contrato, fkIdOficina);
										
											insertEmpleado = "INSERT INTO empleado VALUES (" + empleado.getNumemp() + ", '" + 
													empleado.getNombre() + "', " + empleado.getEdad() + ", " + empleado.getIdOficina()
													+ ", '" + empleado.getPuesto() + "','" + empleado.getContrato() + "');";
						}
									
						else {
							System.out.println("Cada empleado debe pertencer a una oficina, pero no hay oficinas creadas."
									+ "�Quieres crear una? S/N");
							
							if(leer.next().equalsIgnoreCase("s")) {
								System.out.println("Ciudad:");
									String ciudad = leer.next();
									
								System.out.println("Superficie:");
									int superficie = leer.nextInt();
									
								System.out.println("Ventas:");
									int ventas = leer.nextInt();
									
							/* Como no hay oficinas, va a tener id 1
									
								Oficina oficina = new Oficina(1, ciudad, superficie, ventas);
								
								if(miStatement.executeUpdate(insertOficina) == 1) {
									System.out.println("Oficina creada.");
									
									/* Modifico el insert para que le ponga el idOficina a 1.
									
									insertEmpleado = "INSERT INTO empleado VALUES (" + empleado.getNumemp() + ", '" + 
											empleado.getNombre() + "', " + empleado.getEdad() + ", " + 1
											+ ", '" + empleado.getPuesto() + "','" + empleado.getContrato() + "');";

								}
								
								else {
									System.out.println("Error al crear una oficina, saliendo del programa.");
								}
							}
							
							else if(leer.next().equalsIgnoreCase("n")){
								System.out.println("De acuerdo, saliendo de generar un empleado.");
							}
							
							else {
								
							}
							
						}
					
								if(miStatement.executeUpdate(insertEmpleado) == 1) {
									System.out.println("Insercion hecha.");
								}
								
								else {
									System.out.println("Insercion fallida.");
								}
								
							}
						
							else {
								System.out.println("Borrado de empleado desechado.");
							}
					
			}
				
			catch(SQLException e) {
				e.printStackTrace();
			}
							
		
	}
	
	*/
	
	public static void main(String[] args) {
		
	//	uno();
	//	dos();
	//	tres();
		cuatro();
		cinco();
		
	}

}
