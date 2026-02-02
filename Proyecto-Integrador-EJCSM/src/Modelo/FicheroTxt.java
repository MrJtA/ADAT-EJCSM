package Modelo;

import java.io.*;
import java.util.*;

public final class FicheroTxt implements Funcionalidades {

    private String nombreFichero;
    private File fichero;
    private HashMap<Integer,Libro> biblioteca;

    public FicheroTxt(String nombreFichero) {
        this.nombreFichero = nombreFichero;
        this.fichero = crearFichero(this.nombreFichero);
        this.biblioteca = leer();
    }

    private File crearFichero(String nombreFichero) {
        File aux = new File("ficheros/" + nombreFichero + ".txt");
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
        try (FileReader fr = new FileReader(this.fichero);
             BufferedReader br = new BufferedReader(fr)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] s = linea.split(", "); 
                int isbn = Integer.parseInt(s[0]);
                Libro libro = new Libro(isbn, s[1], s[2], s[3], s[4]);
                aux.put(isbn, libro);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: El fichero de texto no existe.");
            System.err.println(e.getMessage());
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error: No se han podido leer/parsear los libros del fichero de texto.");
            System.err.println(e.getMessage());
        }
        return aux;
    }

    @Override
    public void guardar(HashMap<Integer,Libro> datos) {
        try (FileWriter fw = new FileWriter(this.fichero);
        BufferedWriter bw = new BufferedWriter(fw)) {
            boolean comienza = true;
            for (Libro libro : datos.values()) {
                if (!comienza) {
                    bw.newLine();
                }
                bw.write(libro.toString());
                comienza = false;
            }
        } catch (IOException e) {
            System.err.println("Error: No se han podido escribir los libros en el fichero de texto.");
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