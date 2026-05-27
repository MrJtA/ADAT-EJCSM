package accesodatos_alumnos;

import java.io.*;
import java.util.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class IntermediarioXML_PD {

    public void leerDatos(String rutaFicheroDirectores) {
        File ficheroDirectores = new File(rutaFicheroDirectores);
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(ficheroDirectores);
            Element raiz = document.getRootElement();       
            List<Element> directores = raiz.getChildren("director");
            for (Element director : directores) {
                String id = director.getChildText("id");
                String nombreDirector = director.getChildText("nombre");       
                System.out.println("--------------------------------------------------------------");
                System.out.println("ID: " + id + " - Nombre: " + nombreDirector);              
                Element contenedorPeliculas = director.getChild("peliculas");
                if (contenedorPeliculas != null && !contenedorPeliculas.getChildren().isEmpty()) {
                    List<Element> peliculas = contenedorPeliculas.getChildren("pelicula");
                    System.out.print("Peliculas: ");           
                    for (int i = 0; i < peliculas.size(); i++) {
                        String nombrePelicula = peliculas.get(i).getChildText("nombre");
                        System.out.print(nombrePelicula);                                                                  
                        if (i < peliculas.size() - 1) {
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

    public void insertarDirector(String rutaFicheroDirectores, String nombre) {
        File ficheroDirectores = new File(rutaFicheroDirectores);
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document documento = saxBuilder.build(ficheroDirectores);
            Element raiz = documento.getRootElement();    
            List<Element> directores = raiz.getChildren("director"); 
            ArrayList<Integer> ids = new ArrayList<>();         
            for (Element director : directores) {
                String id = director.getChildText("id");
                if (id != null) {
                    ids.add(Integer.parseInt(id.trim()));
                }
            }        
            int id;
            while (true) {
                id = (int) (Math.random() * Integer.MAX_VALUE);
                if (!ids.contains(id)) {
                    break;
                }
            }          
            Element director = new Element("director");
            director.addContent(new Element("id").setText(String.valueOf(id)));
            director.addContent(new Element("nombre").setText(nombre));
           	director.addContent(new Element("peliculas"));            
            raiz.addContent(director);         
            XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
            try (FileOutputStream fos = new FileOutputStream(ficheroDirectores)) {
                xmlOutput.output(documento, fos);
            }
            System.out.println("Se ha insertado el director correctamente con ID: " + id);
            
        } catch (JDOMException | IOException e) {
            System.out.println("Error procesando XML: " + e.getMessage());
        }
    }
}