
package accesodatos_alumnos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;



public class IntermediarioFichero_AG {

	/*
	 * Formato de salida
	 * 
	 	--------------------------------------------------------------
		ID: 1 - Nombre: Extremoduro 
			Albumes: Agila, Ley Innata
		--------------------------------------------------------------
		ID: 2 - Nombre: Rosalia
			Albumes: Mal querer, Lux
		--------------------------------------------------------------	
		
	 * Si se escribe nombre e id del grupo: 0.75 puntos
	 * Si se escribe además el nombre de los albumes: 1,5 puntos
	 */

	public void leerDatos(String rutaFicheroGrupos, String rutaFicheroAlbumes) {

		File ficheroGrupos = new File(rutaFicheroGrupos);
		try (FileReader fr1 = new FileReader(ficheroGrupos);
		BufferedReader br1 = new BufferedReader(fr1)) {
			String lineaGrupo;
			while ((lineaGrupo = br1.readLine()) != null) {
				System.out.println("--------------------------------------------------------------");
				String[] grupo = lineaGrupo.split(",");
				int idGrupo = Integer.parseInt(grupo[0]);
				String nombreGrupo = grupo[1];
				System.out.println("ID: " + idGrupo + " - Nombre: " + nombreGrupo);
				ArrayList<String> albumes = new ArrayList<>();
				File ficheroAlbumes = new File(rutaFicheroAlbumes);
				try (FileReader fr2 = new FileReader(ficheroAlbumes);
					BufferedReader br2 = new BufferedReader(fr2)) {
					String lineaAlbum;
					while ((lineaAlbum = br2.readLine()) != null) {
						String[] album = lineaAlbum.split(",");
						String nombreAlbum = album[1];
						int idGrupoAlbum = Integer.parseInt(album[2]);
						if (idGrupoAlbum == idGrupo) {
							albumes.add(nombreAlbum);
						}
					}
				}
				if (!albumes.isEmpty()) {
					System.out.print("\tAlbumes: ");
					for (int i = 0; i<albumes.size(); i++) {
						System.out.print(albumes.get(i));
						if (i < albumes.size() - 1) {
							System.out.print(", ");
						}
					}
					System.out.println();
				}
				System.out.println("--------------------------------------------------------------");
			}
		} catch (Exception e) {
			System.out.println("Error al leer los ficheros: " + e.getMessage());
		}

	}
	
	/*
	 *  Insertar un grupo. Recibe el nombre del grupo. 1 punto
	 */
	
	public void insertarGrupo(String rutaFicheroGrupos, String nombre) {
        File ficheroGrupos = new File(rutaFicheroGrupos);
        ArrayList<Integer> ids = new ArrayList<>();
		try (FileReader fr = new FileReader(ficheroGrupos);
			BufferedReader br = new BufferedReader(fr)) {
			String linea;
			while ((linea = br.readLine()) != null) {
				if (!linea.trim().isEmpty()) {
					String[] grupo = linea.split(",");
					int id = Integer.parseInt(grupo[0].trim());
					ids.add(id);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        int id;
        while (true) {
            id = (int) (Math.random() * Integer.MAX_VALUE);
            if (!ids.contains(id)) {
                break;
            }
        }
        try (FileWriter fw = new FileWriter(ficheroGrupos, true);
             BufferedWriter bw = new BufferedWriter(fw)) {        
            bw.write(id + "," + nombre);
            bw.newLine();
            System.out.println("El grupo se ha insertado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al insertar el grupo: " + e.getMessage());
        }
    }


}
