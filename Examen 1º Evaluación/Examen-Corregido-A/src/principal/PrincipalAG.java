package principal;

import java.io.File;
import java.util.Scanner;

import accesodatos_alumnos.*;

public class PrincipalAG {

	Scanner miScanner;

	public static void main(String[] args) {

		try {

			System.out.println("Inicio Ejecucion - Examen Albumes Grupos - Alumnos ");

			PrincipalAG principal = new PrincipalAG();
			principal.ejecucion();

			System.out.println("Fin Ejecucion - Examen Albumes Grupos - Alumnos ");
			System.exit(0);

		} catch (Exception e) {

			System.out.println("Se ha producido una excepción");
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
			System.exit(-1);

		}
	}

	public PrincipalAG() {

		miScanner = new Scanner(System.in); // Para leer las opciones de teclado

	}

	public int opcionMenu() {

		int opcion = 0;
		System.out.println();
		System.out.println("MENU DE OPCIONES");
		System.out.println("0 - Salir");
		System.out.println("CON JDBC");
		System.out.println("1 - Consulta Grupo por nombre (con JDBC)");
		System.out.println("2 - Insertar un grupo (con JDBC)");
		System.out.println("3 - Modificar un grupo (con JDBC)");
		System.out.println("4 - Eliminar un grupo (con JDBC)");
		System.out.println("5 - Consulta discos (con JDBC)");
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
			case 1: // Buscar grupo por nombre (JDBC)
				this.buscarGrupoXnombre_JDBC();
				break;
			case 2: // Insertar Grupo (JDBC)
				this.insertarGrupo_JDBC();
				break;
			case 3:	// Modificar grupo (JDBC)
				this.modificarGrupo_JDBC();
				break;
			case 4: // Borrar grupo (JDBC)
				this.borrarGrupo_JDBC();
				break;
			case 5: // Consulta albumes (JDBC)
				this.consultaAlbumes_JDBC();
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

	// Opcion 1 - Buscar grupo por nombre con JDBC
	private void buscarGrupoXnombre_JDBC() {
		System.out.println("Escribe el nombre del grupo que quieres buscar");
		String nombreGrupo = miScanner.nextLine();
		IntermediarioBD_AG intermediarioJDBC = new IntermediarioBD_AG();
		intermediarioJDBC.buscarUnGrupo(nombreGrupo);
	}

	// Opcion 2 - Insertar grupo con JDBC
	private void insertarGrupo_JDBC() {
		System.out.println("Escribe el nombre del grupo que quieres insertar");
		String nombreGrupo = miScanner.nextLine();
		IntermediarioBD_AG intermediarioJDBC = new IntermediarioBD_AG();
		intermediarioJDBC.insertarUnGrupo(nombreGrupo);
	}

	// Opcion 3 - Modificar grupo con JDBC
	private void modificarGrupo_JDBC() {
		int idGrupo = this.leerEntero("Escribe el id del grupo que quieres modificar");
		System.out.println("Escribe el nuevo nombre del grupo");
		String nombreNuevo = miScanner.nextLine();
		IntermediarioBD_AG intermediarioJDBC = new IntermediarioBD_AG();
		intermediarioJDBC.modificarUnGrupo(idGrupo, nombreNuevo);
	}

	// Opcion 4 - Borrar grupo con JDBC
	private void borrarGrupo_JDBC() {
		int idGrupo = this.leerEntero("Escribe el id del grupo que quieres borrar (se borrarán los discos asociados!!!)");
		IntermediarioBD_AG intermediarioJDBC = new IntermediarioBD_AG();
		intermediarioJDBC.borrarUnGrupo(idGrupo);
	}
	
	// Opcion 5 - Consulta Albumes
	private void consultaAlbumes_JDBC() {
		IntermediarioBD_AG intermediarioJDBC = new IntermediarioBD_AG();
		intermediarioJDBC.consultaAlbumes();
	}

	/*
	 * METODOS FICHEROS
	 */

	// Opción Leer Fichero
	private void leer_Fichero() {
		IntermediarioFichero_AG intermediarioFichero = new IntermediarioFichero_AG();
		intermediarioFichero.leerDatos("Ficheros/grupos.txt","Ficheros/albumes.txt");
	}
	
	// Opción insertar Fichero
	private void insertar_Fichero() {
		System.out.println("Escribe el nombre del grupo que quieres insertar");
		String nombre = miScanner.nextLine();		
		IntermediarioFichero_AG intermediarioFichero = new IntermediarioFichero_AG();
		intermediarioFichero.insertarGrupo("Ficheros/grupos.txt",nombre);
	}
	
	/*
	 * MËTODOS FICHEROS XML
	 */

	// Opción Leer XML
	private void leer_XML() {
		IntermediarioXML_AG intermediarioXML = new IntermediarioXML_AG();
		intermediarioXML.leerDatos("Ficheros/grupos.xml");
	}
	
	// Opción insertar XML
	private void insertar_XML() {
		System.out.println("Escribe el nombre del grupo que quieres insertar");
		String nombre = miScanner.nextLine();
		IntermediarioXML_AG intermediarioXML = new IntermediarioXML_AG();
		intermediarioXML.insertarGrupo("Ficheros/grupos.xml",nombre);
	}
	
}
