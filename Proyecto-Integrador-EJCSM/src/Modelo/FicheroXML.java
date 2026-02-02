package Modelo;

import java.io.*;
import java.util.*;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public final class FicheroXML implements Funcionalidades {

    private String nombreFichero;
    private File fichero;
    private HashMap<Integer,Libro> biblioteca;

    public FicheroXML(String nombreFichero) {
        this.nombreFichero = nombreFichero;
        this.fichero = crearFichero(this.nombreFichero);
        this.biblioteca = leer();
    }

    private File crearFichero(String nombreFichero) {
        File aux = new File("ficheros/" + nombreFichero + ".xml");
        File directorioPadre = aux.getParentFile();
        if (directorioPadre != null && !directorioPadre.exists()) {
            directorioPadre.mkdirs();
        }
        try {
            if (aux.createNewFile()) {
                System.out.println("El fichero no existe y se ha creaado correctamente.");
            }
        } catch (IOException e) {
            System.err.println("Error: No se ha podido encontrar o crear el fichero de texto.");
        }
        return aux;
    }

    @Override
    public HashMap<Integer,Libro> leer() {
        HashMap<Integer,Libro> aux = new HashMap<>();
        if (!this.fichero.exists() || this.fichero.length() == 0) {
            return aux;
        }
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document documento = saxBuilder.build(this.fichero);
            Element raiz = documento.getRootElement();
            List<Element> libros = raiz.getChildren("libro"); 
            for (int i = 0; i < libros.size(); i++) {
                Element elemento = libros.get(i);
                int isbn = Integer.parseInt(elemento.getAttributeValue("ISBN"));
                String titulo = elemento.getChildText("titulo");
                String autor = elemento.getChildText("autor");
                String editorial = elemento.getChildText("editorial");
                String genero = elemento.getChildText("genero");
                Libro libro = new Libro(isbn, titulo, autor, editorial, genero);
                aux.put(isbn, libro);
            }
        } catch (JDOMException | IOException | NumberFormatException e) {
            System.err.println("Error: No se han podido leer/parsear los libros del fichero XML.");
            System.err.println(e.getMessage());
        }
        return aux;
    }

    @Override
    public void guardar(HashMap<Integer,Libro> datos) {
        try {
            Element raiz = new Element("biblioteca"); 
            Document documento = new Document(raiz);
            for (Libro libro : datos.values()) {
                Element elemento = new Element("libro");
                elemento.setAttribute("ISBN", String.valueOf(libro.getIsbn()));
                elemento.addContent(new Element("titulo").setText(libro.getTitulo()));
                elemento.addContent(new Element("autor").setText(libro.getAutor()));
                elemento.addContent(new Element("editorial").setText(libro.getEditorial()));
                elemento.addContent(new Element("genero").setText(libro.getGenero()));
                raiz.addContent(elemento);
            }
            XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
            try (FileOutputStream fos = new FileOutputStream(this.fichero)) {
                xmlOutput.output(documento, fos);
            }
        } catch (IOException e) {
            System.err.println("Error: No se han podido escribir los libros en el fichero XML.");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void insertar(Libro libro) {
        int isbn = libro.getIsbn();
        if (this.biblioteca.containsKey(isbn)) {
            System.out.println("Error: Ya existe un libro con el ISBN '" + isbn + "'.");
            return;
        }
        this.biblioteca.put(isbn, libro);
        System.out.println("El libro con el ISBN '" + isbn + "' se ha registrado correctamente.");
        guardar(this.biblioteca);
    }

    @Override
    public void borrar(int isbn) {
        if (!this.biblioteca.containsKey(isbn)) {
            System.out.println("Error: No existe ningún libro con el ISBN '" + isbn + "'.");
            return;
        }
        this.biblioteca.remove(isbn);
        System.out.println("El libro con el ISBN '" + isbn + "' se ha borrado correctamente.");
        guardar(this.biblioteca);
    }

    @Override
    public void modificar(int isbn, Libro libro) {
        if (!this.biblioteca.containsKey(isbn)) {
            System.out.println("Error: No existe ningún libro con el ISBN '" + isbn + "'.");
            return;
        }
        this.biblioteca.remove(isbn);
        this.biblioteca.put(libro.getIsbn(), libro);
        System.out.println("El libro con el ISBN '" + isbn + "' se ha sustituido correctamente por el libro con el ISBN '" + isbn + "'.");
        guardar(this.biblioteca);
    }

}