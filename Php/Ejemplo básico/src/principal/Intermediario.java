package principal;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONObject;

import modelo.Jugador;

public class Intermediario {

	Scanner teclado;
	AccesoJSONRemoto acceso;

	public Intermediario() {
		this.teclado = new Scanner(System.in); // Para leer las opciones de										// teclado
		this.acceso = new AccesoJSONRemoto();
	}

	public void ejecucion() {
		int op = 0; // Opcion
		boolean salir = false;

		while (!salir) { // Estructura que repite el algoritmo del menu
							// principal hasta que se la condicion sea falsa
			// Se muestra el menu principal
			System.out.println();
			System.out.println("........ MENU ........... \n" + ".  0 Salir \n" + ".  1 Leer jugadores  \n"
					+ ".  2 Añadir jugador \n" + "..........................");
			try {
				op = teclado.nextInt();
				teclado.nextLine();
				System.out.println("OPCION SELECCIONADA:" + op);
				switch (op) {
				case 0:
					salir = true;
					break;
				case 1:
					HashMap<Integer, Jugador> hm = leeJugadores();
					pintaJugadores(hm);
					break;
				case 2:
					//Jugador auxJugador = this.crearJugador();		
					Jugador auxJugador = this.crearJugadorPruebas(); // Método para pruebas. Comentar y utilizar el anterior	 
					acceso.anadirJugadorJSON(auxJugador);
				default:
					System.out.println("Opcion invalida: marque un numero de 0 a 2");
					break;
				}

				// System.exit(1);

			} catch (InputMismatchException e) {
				System.out.println("Excepcion por opcion invalida: marque un numero de 0 a 1");
				teclado.next();
			} catch (Exception e) {
				System.out.println(
						"Excepcion desconocida. Traza de error comentada en el método 'ejecucion' de la clase intermediario");
				// e.printStackTrace();
				System.out.println("Fin ejecución");
				System.exit(-1);
			}
		}

		// teclado.close();

	}

	private HashMap<Integer, Jugador> leeJugadores() {

		HashMap<Integer, Jugador> hmAux = acceso.lee();

		return hmAux;

	}

	private void pintaJugadores(HashMap<Integer, Jugador> map) {

		// Recorre el hashmap y va pintando los jugadores (utiliza el método
		// toString de la clase Jgador
		for (Map.Entry<Integer, Jugador> entry : map.entrySet()) {
			System.out.println(entry.getValue());
		}

	}



	private Jugador crearJugador() {

		String nombre;
		int numero;
		int equipoFK;
		Jugador jAux = null;

		try {

			System.out.println("Escriba el nombre del jugador a añadir");
			nombre = teclado.nextLine();
			System.out.println("Escriba el número del jugador a añadir");
			numero = Integer.parseInt(teclado.nextLine());

			// Aquí lo lógico sería mostrar el listado de equipos y poder
			// seleccionar uno (habría que hacer otra consulta

			System.out.println("Escriba el equipo del jugador a añadir");
			equipoFK = Integer.parseInt(teclado.nextLine());

			jAux = new Jugador(nombre, numero, equipoFK);

		} catch (InputMismatchException e) {
			System.out.println("Excepcion por opcion invalida: marque un numero de 0 a 1");
			teclado.next();
		}

		return jAux;

	}
	
	/*
	 * Método para insertar datos de prueba 
	 * sin necesidad de rellenarlos por teclado
	 * Una vez que está probado el envío con el jugador
	 * creado en este método, utilizamos el crearJugador "normal"
	 */
	
	private Jugador crearJugadorPruebas() {

		String nombre = "PRUEBA DESDE ECLIPSE";
		int numero = 25;
		int equipoFK = 1;
		Jugador jAux = new Jugador(nombre, numero, equipoFK);

		return jAux;

	}

}
