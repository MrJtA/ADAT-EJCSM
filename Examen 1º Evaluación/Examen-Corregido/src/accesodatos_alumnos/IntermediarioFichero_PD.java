package accesodatos_alumnos;

import java.io.*;
import java.util.*;


public class IntermediarioFichero_PD {

	/*
	 * Formato de salida
	 * 
	 * --------------------------------------------------------------
	 * ID: 1 - Nombre: Almodovar
	 * Peliculas: Volver, Julieta
	 * --------------------------------------------------------------
	 * ID: 2 - Nombre: Nolan
	 * Peliculas: Memento, Dunkerke
	 * --------------------------------------------------------------
	 * 
	 * Si se escribe nombre e id del director: 0.75 puntos
	 * Si se escribe además el nombre de las peliculas: 1,5 puntos
	 */

	public void leerFichero(String rutaFicheroDirectores, String rutaFicheroPeliculas) {
		File ficheroDirectores = new File(rutaFicheroDirectores);
		File ficheroPeliculas = new File(rutaFicheroPeliculas);
		try (FileReader fr1 = new FileReader(ficheroDirectores);
		BufferedReader br1 = new BufferedReader(fr1)) {
			String lineaDirector;
			System.out.println("--------------------------------------------------------------");
			while ((lineaDirector = br1.readLine()) != null) {
				String[] director = lineaDirector.split(";");
				int id = Integer.parseInt(director[0]);
				String nombre = director[1];
				System.out.println("ID: " + id + " - Nombre: " + nombre);
				ArrayList<String> peliculas = new ArrayList<>();
				String lineaPelicula;
				try (FileReader fr2 = new FileReader(ficheroPeliculas);
					BufferedReader br2 = new BufferedReader(fr2)) {
					while ((lineaPelicula = br2.readLine()) != null) {
						String[] pelicula = lineaPelicula.split(";");
						String nombrePelicula = pelicula[1];
						int idDirectorPelicula = Integer.parseInt(pelicula[2]);
						if (idDirectorPelicula == id) {
							peliculas.add(nombrePelicula);
						}
					}
				}
				if (!peliculas.isEmpty()) {
					System.out.print("Peliculas: ");
					for (int i = 0; i < peliculas.size(); i++) {
						System.out.print(peliculas.get(i));
						if (i < peliculas.size() - 1) {
							System.out.print(", ");
						}
					}
				}
			}
			System.out.println("--------------------------------------------------------------");
		} catch (IOException e) {
			System.out.println("Error al leer los ficheros: " + e.getMessage());
		}
	}

	/*
	 * Insertar un director. Recibe el nombre
	 */

	public void insertarDirector(String rutaFicheroDirectores, String nombre) {
		File ficheroDirectores = new File(rutaFicheroDirectores);
		try (FileWriter fw = new FileWriter(ficheroDirectores, true);
		BufferedWriter bw = new BufferedWriter(fw)) {
			ArrayList<Integer> ids = new ArrayList<>();
			String linea;
			try (FileReader fr = new FileReader(ficheroDirectores);
				BufferedReader br = new BufferedReader(fr)) {
				while ((linea = br.readLine()) != null) {
					String[] director = linea.split(";");
					int id = Integer.parseInt(director[0]);
					ids.add(id);
				}
			}
			int id;
			while (true) {
				id = (int) (Math.random()*Integer.MAX_VALUE);
				if (!ids.contains(id)) {
					break;
				}
			}
			bw.write(id + ";" + nombre);
			bw.newLine();
			System.out.println("Se ha insertado el director correctamente.");
		} catch (IOException e) {
			System.out.println("Error al leer los ficheros: " + e.getMessage());
		}
	}

}
