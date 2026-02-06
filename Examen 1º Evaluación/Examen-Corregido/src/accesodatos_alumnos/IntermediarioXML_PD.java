package accesodatos_alumnos;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
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
		
		File inputFile = new File(rutaFicheroDirectores);
		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(inputFile);
			Element raiz = document.getRootElement();
			List<Element> list = raiz.getChildren();
			for (int i = 0; i < list.size(); i++) {
				Element elemento = list.get(i);
				int id = Integer.parseInt(elemento.getChild("id").getText()); 
				String name = elemento.getChild("nombre").getText();
				System.out.println("--------------------------------------------------------------");
				System.out.println("ID: " + id + " - " + "Nombre: " + name);
				Element peliculas = elemento.getChild("peliculas");
				if(peliculas!=null) {
					List<Element> listaPelis = peliculas.getChildren();
					System.out.print("\t\tPeliculas: ");
					for (int j = 0; j < listaPelis.size(); j++) {
						Element peli = listaPelis.get(j);
						System.out.print(peli.getChild("nombre").getText() + ", ");
					}
					System.out.print("\n");
				}
				System.out.println("--------------------------------------------------------------");			
			}
		} catch (Exception e) {
			System.err.println("Got an exception! " + e.getMessage());
			e.printStackTrace();
		}

	}

	/*
	 * Insertar un director. Recibe el nombre
	 */

	public void insertarDirector(String rutaFicheroDirectores, String nombre) {

		File inputFile = new File(rutaFicheroDirectores);
		System.out.println("INSERTANDO DIRECTOR");
		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(inputFile);
			Element raiz = document.getRootElement();
			List<Element> list = raiz.getChildren();
			int idMaximo = 0;
			for (int i = 0; i < list.size(); i++) {
				Element elemento = list.get(i);
				
				int id = Integer.parseInt(elemento.getChild("id").getText()); 
				if(id>idMaximo) {
					idMaximo = id;
				}
			}
			idMaximo++;
			Element elementoDirector = new Element("director");
			raiz.addContent(elementoDirector);
			Element elementoId = new Element("id");
			elementoId.setText(String.valueOf(idMaximo));
			Element elementoNombre = new Element("nombre");
			elementoNombre.setText(nombre);
			elementoDirector.addContent(elementoId);	
			elementoDirector.addContent(elementoNombre);
			Format f = Format.getPrettyFormat ();
			f.setEncoding("UTF-8");
			f.setOmitDeclaration(false);
			XMLOutputter xmlOut = new XMLOutputter(f);
			xmlOut.output(document, new FileOutputStream(inputFile));
			System.out.println("Añadido un nuevo elemento");
		} catch (Exception e) {
			System.err.println("Got an exception! " + e.getMessage());
			e.printStackTrace();
		}		
		

	}

}