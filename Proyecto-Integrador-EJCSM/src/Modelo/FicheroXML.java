package Modelo;

import Vista.Vista;
import java.io.*;
import java.util.*;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public final class FicheroXML implements Funcionalidades {

    private final String nombreFichero;
    private File fichero;
    private final HashMap<Integer,Libro> biblioteca;
    private final Vista vista;

    public FicheroXML(String nombreFichero) {
        this.nombreFichero = nombreFichero;
        this.vista = new Vista();
        try {
            this.fichero = crearFichero(this.nombreFichero);
        } catch (IOException e) {
            this.vista.errorCreacionFichero(nombreFichero);
        }
        this.biblioteca = leer();
    }

    private File crearFichero(String nombreFichero) throws IOException {
        File aux = new File("ficheros/" + nombreFichero + ".xml");
        File directorioPadre = aux.getParentFile();
        if (directorioPadre != null && !directorioPadre.exists()) {
            directorioPadre.mkdirs();
        }
        if (aux.createNewFile()) {
            this.vista.creacionFichero(nombreFichero);
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
                if (aux.containsKey(isbn)) {
                    this.vista.errorLibroRepetido(isbn);
                } else {
                    aux.put(isbn, libro);
                }
            }
        } catch (FileNotFoundException e) {
            this.vista.errorFicheroInexistente(this.nombreFichero);
        } catch (JDOMException | IOException | NumberFormatException e) {
            this.vista.errorLecturaFichero(this.nombreFichero);
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
            this.vista.guardadoFichero(this.nombreFichero);
        } catch (IOException e) {
            this.vista.errorGuardadoFichero(this.nombreFichero);
        }
    }

    @Override
    public void insertar(Libro libro) {
        int isbn = libro.getIsbn();
        if (this.biblioteca.containsKey(isbn)) {
            this.vista.errorLibroExistente(isbn);
            return;
        }
        this.biblioteca.put(isbn, libro);
        this.vista.insercionLibro(isbn);
        this.guardar(this.biblioteca);
    }

    @Override
    public void borrar(int isbn) {
        if (!this.biblioteca.containsKey(isbn)) {
            this.vista.errorLibroInexistente(isbn);
            return;
        }
        this.biblioteca.remove(isbn);
        this.vista.borradoLibro(isbn);
        this.guardar(this.biblioteca);
    }

    @Override
    public void modificar(int isbn, Libro libro) {
        if (!this.biblioteca.containsKey(isbn)) {
            this.vista.errorLibroInexistente(isbn);
            return;
        }
        this.biblioteca.remove(isbn);
        this.biblioteca.put(libro.getIsbn(), libro);
        this.vista.modificacionLibro(isbn, libro.getIsbn());
        this.guardar(this.biblioteca);
    }

    @Override
    public void restablecer() {
        this.biblioteca.clear();
        this.vista.restablecerLibros();
        this.guardar(this.biblioteca);
    }

}