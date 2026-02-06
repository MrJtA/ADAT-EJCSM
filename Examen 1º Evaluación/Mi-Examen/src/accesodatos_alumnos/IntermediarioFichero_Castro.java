package accesodatos_alumnos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IntermediarioFichero_Castro {

	/*
	 * Formato de salida
	 * 
	 	--------------------------------------------------------------
		ID: 1 - Nombre: Almodovar 
			Albumes: Volver, Julieta
		--------------------------------------------------------------
		ID: 2 - Nombre: Nolan
			Albumes: Memento, Dunkerke
		--------------------------------------------------------------	
		
	 * Si se escribe nombre e id del director: 0.75 puntos
	 * Si se escribe además el nombre de las peliculas: 1,5 puntos
	 */

	public void leerFichero(String rutaFicheroDirectores, String rutaFicheroPeliculas) {
		try (FileReader fr1 = new FileReader(new File(rutaFicheroDirectores));
             BufferedReader br1 = new BufferedReader(fr1);
             FileReader fr2 = new FileReader(new File(rutaFicheroDirectores));
             BufferedReader br2 = new BufferedReader(fr1)) {
            String linea1;
            while ((linea1 = br1.readLine()) != null) {
                String[] s = linea1.split(";"); 
                String id = s[0];
				String nombre = s[1];
				System.out.println("--------------------------------------------------------------");
                System.out.println("ID: " + id + " - Nombre: " + nombre);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer/parsear los libros del fichero: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al leer los libros del fichero: " + e.getMessage());
        }
	}
	
	/*
	 *  Insertar un director. Recibe el nombre
	 */
	
	public void insertarDirector(String rutaFicheroDirectores, String nombre) {
		try (FileWriter fw = new FileWriter(new File(rutaFicheroDirectores));
        BufferedWriter bw = new BufferedWriter(fw)) {
            String id = String.valueOf((int)(Math.random())*100);
            bw.write(id + ";" + nombre);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al escribir los libros del fichero: " + e.getMessage());
        }
	}


}
