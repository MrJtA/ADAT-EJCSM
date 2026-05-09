package accesodatos_alumnos;

import java.io.*;
import java.util.*;
import java.text.Format;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class IntermediarioXML_PD {

	/*
	 * Utilizamos SAX
	 * 
	 */

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

	public void leerDatos(String rutaFicheroDirectores) {
		File ficheroDirectores = new File(rutaFicheroDirectores);
		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(ficheroDirectores);
			Element raiz = document.getRootElement();		
			List<Element> directores = raiz.getChildren();		
			for (Element director : directores) {
				String id = director.getChildText("id");
				String nombre = director.getChildText("nombre");
				System.out.println("--------------------------------------------------------------");
				System.out.println("ID: " + id + " - Nombre: " + nombre);
				Element peliculasContainer = director.getChild("peliculas");
				if (peliculasContainer != null) {
					List<Element> listaPelis = peliculasContainer.getChildren();
					System.out.print("\t\tPeliculas: ");			
					for (int j = 0; j < listaPelis.size(); j++) {
						String nombrePeli = listaPelis.get(j).getChildText("nombre");
						System.out.print(nombrePeli);					
						if (j < listaPelis.size() - 1) {
							System.out.print(", ");
						}
					}
					System.out.println();
				}
				System.out.println("--------------------------------------------------------------");
			}
		} catch (Exception e) {
			System.err.println("Error procesando XML: " + e.getMessage());
		}
	}

	/*
	 * Insertar un director. Recibe el nombre
	 */

	public void insertarDirector(String rutaFicheroDirectores, String nombre) {
		try {
			File ficheroDirectores = new File(rutaFicheroDirectores);
			SAXBuilder saxBuilder = new SAXBuilder();
			Document documento = saxBuilder.build(ficheroDirectores);
			Element raiz = documento.getRootElement();
			List<Element> listaDirectores = raiz.getChildren("director");
			ArrayList<Integer> ids = new ArrayList<>();
			for (Element director : listaDirectores) {
				int id = Integer.parseInt(director.getChildText("id"));
				ids.add(id);
			}
			int id;
			while (true) {
				id = (int) (Math.random()*Integer.MAX_VALUE);
				if (!ids.contains(id)) {
					break;
				}
			}
			Element director = new Element("director");
			director.addContent(new Element("id").setText(String.valueOf(id)));
			director.addContent(new Element("nombre").setText(nombre));
			raiz.addContent(director);
			XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
			try (FileOutputStream fos = new FileOutputStream(ficheroDirectores)) {
				xmlOutput.output(documento, fos);
			}
			System.out.println("Se ha insertado el director correctamente.");
		} catch (JDOMException | IOException e) {
			System.out.println("Error procesando XML: " + e.getMessage());
		}
	}

}