package accesodatos_alumnos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class IntermediarioXML_Castro {

	/*
	 * Utilizamos SAX
	 * 
	 */

	/*
	 * Formato de salida
	 * 
	 	--------------------------------------------------------------
		ID: 1 - Nombre: Almodovar 
			Peliculas: Volver, Julieta
		--------------------------------------------------------------
		ID: 2 - Nombre: Nolan
			Peliculas: Memento, Dunkerke
		--------------------------------------------------------------	
		
	 * Si se escribe nombre e id del director: 0.75 puntos
	 * Si se escribe además el nombre de las peliculas: 1,5 puntos
	 */

	public void leerDatos(String rutaFicheroDirectores) {
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
			Document documento = saxBuilder.build(new File(rutaFicheroDirectores));
            Element raiz = documento.getRootElement();
            List<Element> directores = raiz.getChildren();
            for (int i = 0; i<directores.size(); i++) {
                Element director = directores.get(i);
                String id = director.getChild("id").getText();
                String nombreDirector = director.getChild("nombre").getText();
				System.out.println("--------------------------------------------------------------");
				System.out.println("ID: " + id + " - " + "Nombre: " + nombreDirector);
            }
        } catch (JDOMException e) { 
            System.err.println("Error de parseo JDOM (XML mal formado, etc.): " + e.getMessage());
        } catch (IOException ex) {
            System.err.println("Error de E/S (archivo no encontrado/accesible): " + ex.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error: El ISBN no es un número válido. " + e.getMessage());
        }
	}
	
	/*
	 *  Insertar un director. Recibe el nombre
	 */
	
	public void insertarDirector(String rutaFicheroDirectores, String nombre) {
		try {
			File fichero = new File(rutaFicheroDirectores);
            SAXBuilder saxBuilder = new SAXBuilder();
            Document documento = saxBuilder.build(fichero);
            Element raiz = documento.getRootElement();
			Element elemento = new Element("director");
			elemento.addContent(new Element("id").setText(String.valueOf((int)(Math.random()*100))));
			elemento.addContent(new Element("nombre").setText(nombre));
			raiz.addContent(elemento);
            XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
            xmlOutput.output(documento, new FileOutputStream(fichero));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
	}

}