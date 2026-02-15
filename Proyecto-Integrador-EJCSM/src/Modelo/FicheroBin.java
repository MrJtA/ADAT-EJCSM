package Modelo;

import Vista.Vista;
import java.io.*;
import java.util.*;

public final class FicheroBin implements Serializable, Funcionalidades {

    private final String nombreFichero;
    private File fichero;
    private final HashMap<Integer,Libro> biblioteca;
    private final Vista vista;

    public FicheroBin(String nombreFichero) {
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
        File aux = new File("ficheros/" + nombreFichero + ".bin");
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
        try (FileInputStream fis = new FileInputStream(this.fichero);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                try {
                    Libro libro = (Libro) ois.readObject();
                    int isbn = libro.getIsbn();
                    if (aux.containsKey(isbn)) {
                        this.vista.errorLibroRepetido(isbn);
                    } else {
                        aux.put(isbn, libro);
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            this.vista.errorFicheroInexistente(this.nombreFichero);
        } catch (IOException | ClassNotFoundException e) {
            this.vista.errorLecturaFichero(this.nombreFichero);
        }
        return aux;
    }

    @Override
    public void guardar(HashMap<Integer,Libro> datos) {
        try (FileOutputStream fos = new FileOutputStream(this.fichero);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (Libro libro : datos.values()) {
                oos.writeObject(libro);
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