package accesodatos_alumnos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;


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
		
		File archivoDirectores = null;
		FileReader frDirectores = null;
		BufferedReader brDirectores = null;

		try {

			/*
			 * Apertura del fichero y creacion de BufferedReader
			 */

			archivoDirectores = new File(rutaFicheroDirectores);
			frDirectores = new FileReader(archivoDirectores);
			brDirectores = new BufferedReader(frDirectores);

			String delimitador = ";";
			int id;
			String name;
		    String texto = brDirectores.readLine();
		    while(texto != null) {
		    	String[] campos = texto.split(delimitador);
		    	id = Integer.parseInt(campos[0]);
		    	name = campos[1];
				System.out.println("--------------------------------------------------------------");
				System.out.println("ID: " + id + " - " + "Nombre: " + name);
				System.out.print("\t\tPeliculas: ");
				File archivoPeliculas = new File(rutaFicheroPeliculas);
				FileReader frPeliculas = new FileReader(archivoPeliculas);
				BufferedReader brPeliculas = new BufferedReader(frPeliculas);	
			    texto = brPeliculas.readLine();
			    while(texto != null) {
			    	String[] camposPeliculas = texto.split(delimitador);
			    	String titulo = camposPeliculas[1];
			    	int idDirector = Integer.parseInt(camposPeliculas[2]);
			    	if(idDirector == id) {
			    		System.out.print(titulo + ", ");
			    	}
			    	texto = brPeliculas.readLine();
			    }
			    System.out.print("\n");
				brPeliculas.close();
				frPeliculas.close();
				System.out.println("--------------------------------------------------------------");		    	
		        texto = brDirectores.readLine();
		    }
			brDirectores.close();
			frDirectores.close();
		} catch (Exception e) {
			System.err.println("Got an exception! " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (null != frDirectores) {
					frDirectores.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}		

	}

	/*
	 * Insertar un director. Recibe el nombre
	 */

	public void insertarDirector(String rutaFicheroDirectores, String nombre) {
		
		File archivoDirectores = null;
		FileReader frDirectores = null;
		BufferedReader brDirectores = null;

		try {

			/*
			 * Apertura del fichero y creacion de BufferedReader para averiguar id maximo. Parte "extra"
			 */

			archivoDirectores = new File(rutaFicheroDirectores);
			frDirectores = new FileReader(archivoDirectores);
			brDirectores = new BufferedReader(frDirectores);
			String delimitador = ";";
			int id;
			int idMaximo = 0;
		    String texto = brDirectores.readLine();
		    while(texto != null) {
		    	String[] campos = texto.split(delimitador);
		    	id = Integer.parseInt(campos[0]);
				if(id>idMaximo) {
					idMaximo = id;
				}
		        texto = brDirectores.readLine();
		    }
			brDirectores.close();
			frDirectores.close();
			idMaximo++;
			FileWriter output = new FileWriter(archivoDirectores,true);
			PrintWriter pw = new PrintWriter(output);
			pw.println(idMaximo+";"+nombre);
			pw.close();
		    
		} catch (Exception e) {
			System.err.println("Got an exception! " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if (null != frDirectores) {
					frDirectores.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}			
		

	}
}
