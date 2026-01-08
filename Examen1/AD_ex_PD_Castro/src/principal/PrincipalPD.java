package principal;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import accesodatos_alumnos.*;

public class PrincipalPD {

	Scanner miScanner;

	public static void main(String[] args) {

		try {

			System.out.println("Inicio Ejecucion - Examen Películas Directores - Alumnos");

			PrincipalPD principal = new PrincipalPD();
			principal.ejecucion();

			System.out.println("Fin Ejecucion - Examen Películas Directores - Alumnos");
			System.exit(0);

		} catch (Exception e) {

			System.out.println("Se ha producido una excepción");
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
			System.exit(-1);

		}
	}

	public PrincipalPD() {

		miScanner = new Scanner(System.in); // Para leer las opciones de teclado

	}

	public int opcionMenu() {

		int opcion = 0;
		System.out.println();
		System.out.println("MENU DE OPCIONES");
		System.out.println("0 - Salir");
		System.out.println("CON JDBC");
		System.out.println("1 - Consulta director por nombre (con JDBC)");
		System.out.println("2 - Insertar un director (con JDBC)");
		System.out.println("3 - Modificar un director (con JDBC)");
		System.out.println("4 - Eliminar un director (con JDBC)");
		System.out.println("5 - Consulta peliculas (con JDBC)");		
		System.out.println("CON FICHEROS");
		System.out.println("6 - Leer datos del fichero (con ficheros de texto)");
		System.out.println("7 - Insertar (con ficheros de texto)");
		System.out.println("CON FICHEROS XML");
		System.out.println("8 - Leer datos del fichero (con XML)");
		System.out.println("9 - Insertar (con ficheros de XML)");		

		opcion = this.leerEntero("Selecciona una opción");

		return opcion;

	}

	public void ejecucion() {
		
		boolean salir = false;
		int opcion;

		try {
		while (!salir) {
			opcion = opcionMenu();
			switch (opcion) {
			case 0: // Salir
				salir = true;
				System.out.println("HASTA LA PROXIMA!!!");
				break;
			case 1: // Buscar Director por nombre (JDBC)
				this.buscarDirectorXnombre_JDBC();
				break;
			case 2: // Insertar director (JDBC)
				this.insertarDirector_JDBC();
				break;
			case 3:	// Modificar Director (JDBC)
				this.modificarDirector_JDBC();
				break;
			case 4: // Borrar Director (JDBC)
				this.borrarDirector_JDBC();
				break;
			case 5: // Consulta albumes (JDBC)
				this.consultaPeliculas_JDBC();
				break;				
			case 6: // Leer datos (Fichero)
				this.leer_Fichero();
				break;
			case 7: // Insertar uno (Fichero)
				this.insertar_Fichero();
				break;
			case 8: // Leer datos (XML)
				this.leer_XML();
				break;
			case 9: // Insertar uno (XML)
				this.insertar_XML();
				break;
			default:
				System.out.println("OPCIÓN NO VÁLIDA O NO IMPLEMENTADA");
				break;
			}

			if (!salir) {
				System.out.println("\n PULSA ENTER PARA CONTINUAR \n");
				System.out.println();
				System.out.println();
				miScanner.nextLine();
			}

		}
		}catch(Exception e) {
			System.err.println("Se ha producido una excepción!!!");
			e.printStackTrace();
		}

	}

	/*
	 * Auxiliar para leer entero controlando excepciones
	 */

	public int leerEntero(String textoPeticion) {

		int var = 0;

		boolean salir = false;

		while (!salir) {
			try {
				System.out.println(textoPeticion);
				var = Integer.parseInt(miScanner.nextLine());
				salir = true;
			} catch (Exception e) {
				System.out.println("No es un numero entero");
			}

		}

		return var;

	}


	/*
	 * MÉTODOS JDBC
	 */

	// Opcion 1 - Buscar Director por nombre con JDBC
	private void buscarDirectorXnombre_JDBC() {
		System.out.println("Escribe el nombre del Director que quieres buscar");
		String nombreDirector = miScanner.nextLine();
		IntermediarioBD_Castro intermediarioJDBC = new IntermediarioBD_Castro();
		intermediarioJDBC.buscarUnDirector(nombreDirector);
	}

	// Opcion 2 - Insertar Director con JDBC
	private void insertarDirector_JDBC() {
		System.out.println("Escribe el nombre del Director que quieres buscar");
		String nombreDirector = miScanner.nextLine();
		IntermediarioBD_Castro intermediarioJDBC = new IntermediarioBD_Castro();
		intermediarioJDBC.insertarUnDirector(nombreDirector);
	}

	// Opcion 3 - Modificar Director con JDBC
	private void modificarDirector_JDBC() {
		int idDirector = this.leerEntero("Escribe el id del director que quieres modificar");
		System.out.println("Escribe el nuevo nombre del Director");
		String nombreNuevo = miScanner.nextLine();
		IntermediarioBD_Castro intermediarioJDBC = new IntermediarioBD_Castro();
		intermediarioJDBC.modificarUnDirector(idDirector, nombreNuevo);
	}

	// Opcion 4 - Borrar Director con JDBC
	private void borrarDirector_JDBC() {
		int idDirector = this.leerEntero("Escribe el id del director que quieres borrar (se borrarán las peliculas asociadas!!!)");
		IntermediarioBD_Castro intermediarioJDBC = new IntermediarioBD_Castro();
		intermediarioJDBC.borrarUnDirector(idDirector);
	}
	
	// Opcion 5 - Consulta Peliculas
	private void consultaPeliculas_JDBC() {
		IntermediarioBD_Castro intermediarioJDBC = new IntermediarioBD_Castro();
		intermediarioJDBC.consultaPeliculas();
	}
	
	/*
	 * FICHEROS
	 */

	// Opción Leer Fichero
	private void leer_Fichero() {
		IntermediarioFichero_Castro intermediarioFichero = new IntermediarioFichero_Castro();
		intermediarioFichero.leerFichero("Ficheros/directores.txt","Ficheros/peliculas.txt");
	}
	
	// Opción insertar Fichero
	private void insertar_Fichero() {
		System.out.println("Escribe el nombre del director que quieres insertar");
		String nombre = miScanner.nextLine();		
		IntermediarioFichero_Castro intermediarioFichero = new IntermediarioFichero_Castro();
		intermediarioFichero.insertarDirector("Ficheros/directores.txt",nombre);
	}
	
	/*
	 * MËTODOS FICHEROS XML
	 */

	// Opción Leer XML
	private void leer_XML() {
		IntermediarioXML_Castro intermediarioXML = new IntermediarioXML_Castro();
		intermediarioXML.leerDatos("Ficheros/directores.xml");
	}
	
	// Opción insertar XML
	private void insertar_XML() {
		System.out.println("Escribe el nombre del director que quieres insertar");
		String nombre = miScanner.nextLine();
		IntermediarioXML_Castro intermediarioXML = new IntermediarioXML_Castro();
		intermediarioXML.insertarDirector("Ficheros/directores.xml",nombre);
	}

}
