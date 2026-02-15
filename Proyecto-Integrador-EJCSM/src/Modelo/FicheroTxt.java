package Modelo;

import Vista.Vista;
import java.io.*;
import java.util.*;

public final class FicheroTxt implements Funcionalidades {

    private final String nombreFichero;
    private File fichero;
    private final HashMap<Integer,Libro> biblioteca;
    private final Vista vista;

    public FicheroTxt(String nombreFichero) {
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
        File aux = new File("ficheros/" + nombreFichero + ".txt");
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
        try (FileReader fr = new FileReader(this.fichero);
             BufferedReader br = new BufferedReader(fr)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] s = linea.split(";"); 
                int isbn = Integer.parseInt(s[0]);
                Libro libro = new Libro(isbn, s[1].trim(), s[2].trim(), s[3].trim(), s[4].trim());
                if (aux.containsKey(isbn)) {
                    this.vista.errorLibroRepetido(isbn);
                } else {
                    aux.put(isbn, libro);
                }
            }
        } catch (FileNotFoundException e) {
            this.vista.errorFicheroInexistente(this.nombreFichero);
        } catch (IOException | NumberFormatException e) {
            this.vista.errorLecturaFichero(this.nombreFichero);
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
                bw.write(libro.getIsbn() + ";" + libro.getTitulo() + ";" + libro.getAutor() + ";" + libro.getEditorial() + ";" + libro.getGenero());
                comienza = false;
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
            this.vista.errorLibroExistente(isbn);
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