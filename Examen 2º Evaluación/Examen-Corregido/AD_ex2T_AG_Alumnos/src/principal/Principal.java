package principal;

import java.util.Scanner;

import accesodatos_alumnos.*;

public class Principal {

	Scanner miScanner;

	public static void main(String[] args) {

		try {

			System.out.println("Inicio Ejecucion - Examen Albumes Grupos - Alumnos");

			Principal principal = new Principal();
			principal.ejecucion();

			System.out.println("Fin Ejecucion - Examen Albumes Grupos - Alumnos");
			System.exit(0);

		} catch (Exception e) {

			System.out.println("Se ha producido una excepción");
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
			System.exit(-1);

		}
	}

	public Principal() {

		miScanner = new Scanner(System.in); // Para leer las opciones de teclado

	}

	public int opcionMenu() {

		int opcion = 0;
		System.out.println();
		System.out.println("MENU DE OPCIONES");
		System.out.println("0 - Salir");
		System.out.println("MONGODB");
		System.out.println("1 - Leer Grupos (MONGODB)");
		System.out.println("2 - Insertar un grupo (MONGODB)");
		System.out.println("3 - Buscar grupo por nombre (MONGODB)");
		System.out.println("4 - Añadir album a grupo (MONGODB)");
		System.out.println("OBJECTDB");
		System.out.println("5 - Insertar un grupo y ver grupos (OBJECTDB) ");
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
				case 1:
					this.leeGruposMongo();
					break;
				case 2:
					this.insertarGrupoMongo();
					break;
				case 3:
					this.buscarGrupoMongo();
					break;
				case 4:
					this.addAlbumMongo();
					break;					
				case 5:
					this.insertarGrupoObjectDB();
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
		} catch (Exception e) {
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
	 * METODOS MONGODB
	 */
	
	private void leeGruposMongo() {
		IntermediarioMongo intermediario = new IntermediarioMongo();
		intermediario.leerInfoGrupos();
	}
	
	private void insertarGrupoMongo() {
		System.out.println("Escribe el nombre del grupo que quieres insertar");
		String nombreGrupo = miScanner.nextLine();
		IntermediarioMongo intermediario = new IntermediarioMongo();
		intermediario.insertarGrupo(nombreGrupo);
	}
	
	private void buscarGrupoMongo() {
		System.out.println("Escribe el nombre del grupo que quieres buscar");
		String nombreGrupo = miScanner.nextLine();
		IntermediarioMongo intermediario = new IntermediarioMongo();
		intermediario.buscarGrupoXNombre(nombreGrupo);
	}	

	private void addAlbumMongo() {
		System.out.println("Escribe el nombre del grupo al que le quieres añadir el album");
		String nombreGrupo = miScanner.nextLine();
		System.out.println("Escribe el nombre del album");
		String album = miScanner.nextLine();		
		IntermediarioMongo intermediario = new IntermediarioMongo();
		intermediario.addAlbum(nombreGrupo,album);
	}	
	
	/*
	 * METODOS OBJECTDB
	 */
	
	private void insertarGrupoObjectDB() {
		System.out.println("Escribe el nombre del grupo que quieres insertar");
		String nombreGrupo = miScanner.nextLine();
		IntermediarioObjectDB intermediario = new IntermediarioObjectDB();
		intermediario.insertarGrupo(nombreGrupo);
		System.out.println("Una vez hecha la inserción, llamamos a leerInfoGrupos para ver todos los grupos y ver si se ha insertado");
		intermediario.leerInfoGrupos();
	}
	
	
}
