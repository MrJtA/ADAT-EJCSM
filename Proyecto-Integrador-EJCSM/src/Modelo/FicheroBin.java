package Modelo;

import java.io.*;
import java.util.*;

public final class FicheroBin implements Serializable, Funcionalidades {

    private String nombreFichero;
    private File fichero;
    private HashMap<Integer,Libro> biblioteca;

    public FicheroBin(String nombreFichero) {
        this.nombreFichero = nombreFichero;
        this.fichero = crearFichero(this.nombreFichero);
        this.biblioteca = leer();
    }

    private File crearFichero(String nombreFichero) {
        File aux = new File("ficheros/" + nombreFichero + ".bin");
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
        try (FileInputStream fis = new FileInputStream(this.fichero);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            while (true) {
                try {
                    Libro libro = (Libro) ois.readObject();
                    int isbn = libro.getIsbn();
                    if (!aux.containsKey(isbn)) {
                        aux.put(isbn, libro);
                    } else {
                        System.out.println("Error: Libro existente con ISBN '" + isbn + "'.");
                    }
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: El fichero binario no existe.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error: No se han podido leer/parsear los libros del fichero binario.");
            System.err.println(e.getMessage());
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
        } catch (IOException e) {
            System.err.println("Error: No se han podido escribir los libros en el fichero binario.");
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