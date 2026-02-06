import java.util.Scanner;

public class MainMaquinaRefrescos {

	final static Scanner sc = new Scanner(System.in);

	public static int opcion() {
        int opcion = -1;
        boolean entradaValida = false;
        do { 
            try {
                System.out.print("Seleccione una opción: ");
                opcion = Integer.parseInt(sc.nextLine()); 
                entradaValida = true;
            } catch (NumberFormatException e) { 
                System.err.println("Error: Opción inválida.");
                System.out.println("Por favor, introduce un opción válida.");
            }
        } while (!entradaValida);
        return opcion;
    }

	public static void main(String[] args) {
		
		MaquinaRefrescos manager = new MaquinaRefrescos();
		boolean seguir = true;
		while (seguir) {
			int opcion = opcion();
			switch (opcion) {
				case 0:
					seguir = false;
					break;
				case 1:
					manager.leerDatos();
					break;
				case 2:
					manager.insercionPrueba();
					break;
				case 3:
					manager.pruebaBusqueda();
					break;
				case 4:
					manager.insercionPrueba2();
					break;
				case 5:
					manager.pruebaUpdate();
					break;
				case 6:
					manager.pruebaBorrado();
					break;
				default:
					seguir = true;
					break;
			}
		}

	}

}
