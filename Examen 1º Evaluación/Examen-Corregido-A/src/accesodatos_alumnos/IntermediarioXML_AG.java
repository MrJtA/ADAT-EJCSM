package accesodatos_alumnos;

import java.io.*;
import java.util.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class IntermediarioXML_AG {

    /*
     * Leer datos respetando el formato visual exigido
     */
    public void leerDatos(String rutaFicheroGrupos) {
        File ficheroGrupos = new File(rutaFicheroGrupos);
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document documento = saxBuilder.build(ficheroGrupos);
            Element raiz = documento.getRootElement();       
            List<Element> grupos = raiz.getChildren("Grupo");           
            for (Element grupo : grupos) {
                String id = grupo.getChildText("id");
                String nombreGrupo = grupo.getChildText("nombre"); 
                System.out.println("--------------------------------------------------------------");
                System.out.println("ID: " + id + " - Nombre: " + nombreGrupo);                             
                Element contenedorAlbumes = grupo.getChild("albumes");
                if (contenedorAlbumes != null && !contenedorAlbumes.getChildren().isEmpty()) {
                    System.out.print("\tAlbumes: ");
                    List<Element> albumes = contenedorAlbumes.getChildren("album");
                    for (int i = 0; i < albumes.size(); i++) {
                        String nombreAlbum = albumes.get(i).getChildText("nombre"); 
                        System.out.print(nombreAlbum);                                     
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
     * Insertar un grupo adaptándose a las mayúsculas del XML original
     */
    public void insertarGrupo(String rutaFicheroGrupos, String nombre) {
        File ficheroGrupos = new File(rutaFicheroGrupos);
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document documento = saxBuilder.build(ficheroGrupos);
            Element raiz = documento.getRootElement();     
            List<Element> grupos = raiz.getChildren();
            ArrayList<Integer> ids = new ArrayList<>();           
            for (Element grupo : grupos) {
                String id = grupo.getChildText("id");
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
            Element grupo = new Element("Grupo"); 
            grupo.addContent(new Element("id").setText(String.valueOf(id)));
            grupo.addContent(new Element("nombre").setText(nombre));
            grupo.addContent(new Element("albumes"));           
            raiz.addContent(grupo);           
            XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
            try (FileOutputStream fos = new FileOutputStream(ficheroGrupos)) {
                xmlOutput.output(documento, fos); 
            }
            System.out.println("Se ha insertado el grupo correctamente con ID: " + id);
            
        } catch (Exception e) {
            System.out.println("Error al insertar el grupo: " + e.getMessage());
        }
    }  
}