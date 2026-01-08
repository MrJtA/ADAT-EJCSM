package Controlador;

import java.io.*;
import java.sql.SQLException;
import java.util.Map;

import Modelo.*;
import Vista.Vista;

public class Controlador {

    private final Vista vista = new Vista();
    private Funcionalidades funcionalidades;

    public static void main(String[] args) throws IOException, SQLException {
        Controlador app = new Controlador();
        app.menu();
    }

    public void menu() throws IOException, SQLException {
        boolean seguir = true;
        while (seguir) {
            vista.menu();
            int opcion = vista.opcion();
            switch (opcion) {
                case 0 -> seguir = false;
                case 1 -> {
                    String fichero = vista.pedirFichero();
                    if (fichero == null) continue;
                    if (fichero.endsWith(".txt")) {
                        funcionalidades = new Texto(vista.crearFichero(fichero));
                    }
                    else if (fichero.endsWith(".bin")) {
                        funcionalidades = new Binario(vista.crearFichero(fichero));
                    }
                    else if (fichero.endsWith(".xml")) {
                        funcionalidades = new XML(vista.crearFichero(fichero));
                    }
                    subMenu();
                }
                case 2 -> {
                    funcionalidades = new Database(vista.pedirDatabase());
                    subMenu();
                }
                default -> seguir = true;
            }
        }
    }
    
    public void subMenu() throws IOException, SQLException {
        boolean seguir = true;
        while (seguir) {
            vista.subMenu();
            int opcion = vista.opcion();
            Map<Integer, Libro> biblioteca = funcionalidades.leer();
            switch (opcion) {
                case 0 -> seguir = false;
                case 1 -> vista.buscar(biblioteca, vista.pedirLibro(biblioteca));
                case 2 -> vista.mostrar(funcionalidades.leer());
                case 3 -> funcionalidades.insertar(vista.crearLibro());
                case 4 -> funcionalidades.borrar(vista.pedirLibro(biblioteca));
                case 5 -> funcionalidades.modificar(vista.pedirLibro(biblioteca), vista.crearLibro());
                case 6 -> {
                    String fichero = vista.pedirFichero();
                    if (fichero == null) continue;
                    funcionalidades.traspasarDatosFichero(vista.crearFichero(fichero));
                }
                case 7 -> funcionalidades.traspasarDatosDatabase(vista.pedirDatabase());
                default -> seguir = true;
            }
        }
    }

}