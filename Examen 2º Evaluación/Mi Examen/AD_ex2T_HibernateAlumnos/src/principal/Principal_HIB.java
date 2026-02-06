package principal;

import java.util.Scanner;

import accesodatos_alumnos.IntermediarioHibernate;

public class Principal_HIB {

	Scanner miScanner;

	public static void main(String[] args) {

		try {

			System.out.println("Inicio Ejecucion - Examen Hibernate - Nombre Alumno");

			Principal_HIB principal = new Principal_HIB();
			principal.ejecucion();

			System.out.println("Fin Ejecucion - Examen Hibernate - Nombre Alumno");
			System.exit(0);

		} catch (Exception e) {

			System.out.println("Se ha producido una excepción");
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
			System.exit(-1);

		}
	}

	public Principal_HIB() {

		miScanner = new Scanner(System.in); // Para leer las opciones de teclado

	}

	public int opcionMenu() {

		int opcion = 0;
		System.out.println();
		System.out.println("MENU DE OPCIONES");
		System.out.println("0 - Salir");
		System.out.println("CON HIBERNATE");
		System.out.println("1 - Muestra todos los profesores (solo los datos del profesor - no hace falta relación)");
		System.out.println("2 - Inserta un profesor");
		System.out.println("3 - Modificar experienca de un profesor");
		System.out.println("4 - Muestra todas las asignaturas (debe aparecer el nombre del profesor)");
		System.out.println("5 - Muestra todas los profesores (debe aparecer el nombre de las asignaturas que imparte)");

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
					miScanner.close();
					System.exit(0);
					break;	
				case 1: //System.out.println("1 - Muestra todos los profesores (solo los datos del profesor)");
					this.e1_mostrarProfesores();
					break;
				case 2: //System.out.println("2 - Inserta un profesor");
					this.e2_insertarProfesor();
					break;				
				case 3: //System.out.println("4 - Modificar experienca de un profesor");
					this.e3_modificarProfesor();
					break;
				case 4: //System.out.println("6 - Muestra todas las asignaturas (debe aparecer el nombre del profesor)");
					this.e4_mostrarAsignaturas();
					break;
				case 5: //System.out.println("7 - Muestra todas los profesores (debe aparecer el nombre de las asignaturas que imparte)");
					this.e5_mostrarProfesoresAvanzado();
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
	 * Auxiliar para leer string 
	 */

	public String leerString(String textoPeticion) {

		System.out.println(textoPeticion);
		return miScanner.nextLine();

	}	

	/*
	 * METODOS HIBERNATE
	 */
	
	
//	System.out.println("1 - Muestra todos los profesores (solo los datos del profesor)");
	
	public void e1_mostrarProfesores() {
		IntermediarioHibernate intermediarioHibernate = new IntermediarioHibernate();
		intermediarioHibernate.e1_mostrarProfesores();
		intermediarioHibernate.cerrarSesion();
	}
	
//	System.out.println("2 - Inserta un profesor");
	
	public void e2_insertarProfesor() {
		String nombre = this.leerString("Escribe el nombre del nuevo profesor");
		int experiencia = this.leerEntero("Escribe los años de experiencia");
		IntermediarioHibernate intermediarioHibernate = new IntermediarioHibernate();
		intermediarioHibernate.e2_insertarProfesor(nombre, experiencia);
		intermediarioHibernate.cerrarSesion();
	}	
		
//	System.out.println("3 - Modificar experienca de un profesor");
	
	public void e3_modificarProfesor() {
		int idProfesor = this.leerEntero("Escribe el id del profesor que quieres modificar");
		int nuevaExperiencia = this.leerEntero("Escribe los nuevos años de experiencia");
		IntermediarioHibernate intermediarioHibernate = new IntermediarioHibernate();
		intermediarioHibernate.e3_modificarProfesor(idProfesor, nuevaExperiencia);
		intermediarioHibernate.cerrarSesion();
	}
	
//	System.out.println("4 - Muestra todas las asignaturas (debe aparecer el nombre del profesor)");
	
	public void e4_mostrarAsignaturas() {
		IntermediarioHibernate intermediarioHibernate = new IntermediarioHibernate();
		intermediarioHibernate.e4_mostrarAsignaturas();;
		intermediarioHibernate.cerrarSesion();
	}
	
//	System.out.println("5 - Muestra todas los profesores (debe aparecer el nombre de las asignaturas que imparte)");
	
	public void e5_mostrarProfesoresAvanzado() {
		IntermediarioHibernate intermediarioHibernate = new IntermediarioHibernate();
		intermediarioHibernate.e5_mostrarProfesoresAvanzado();
		intermediarioHibernate.cerrarSesion();
	}	

}
