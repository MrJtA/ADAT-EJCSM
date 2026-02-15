package Controlador;

import Modelo.*;
import Vista.Vista;
import java.util.HashMap;

public class Controlador {

    private final Vista vista = new Vista();
    private Funcionalidades funcionalidades;

    public static void main(String[] args) {
        Controlador app = new Controlador();
        app.menu();
    }

    public void menu() {
        vista.introduccion();
        while (true) {
            vista.menu();
            int opcion = vista.opcion();
            if (opcion == 0) {
                vista.despedida();
                break;
            }
            switch (opcion) {
                case 1 -> subMenuFichero();
                case 2 -> subMenuBBDD();
                default -> vista.opcionNoDisponible();
            }
        }
    }

    public void subMenuFichero() {
        while (true) {
            vista.menuFichero();
            int opcion = vista.opcion();
            if (opcion == 0) break;
            try {
                switch (opcion) {
                    case 1 -> funcionalidades = new FicheroTxt(vista.pedirFichero());
                    case 2 -> funcionalidades = new FicheroBin(vista.pedirFichero());
                    case 3 -> funcionalidades = new FicheroXML(vista.pedirFichero());
                    default -> vista.opcionNoDisponible();
                }
                subMenu();
            } catch (Exception e) {}
        }
    }

    public void subMenuBBDD() {
        while (true) {
            vista.menuBBDD();
            int opcion = vista.opcion();
            if (opcion == 0) break;
            try {
                switch (opcion) {
                    case 1 -> funcionalidades = new BBDDMySQL(vista.pedirBBDD());
                    case 2 -> funcionalidades = new BBDDMySQLHibernate();
                    case 3 -> funcionalidades = new BBDDObjetos();
                    case 4 -> funcionalidades = new BBDDMongo(vista.pedirBBDD());
                    case 5 -> funcionalidades = new BBDDPHP();
                    default -> vista.opcionNoDisponible();
                }
                subMenu();
            } catch (Exception e) {}
        }
    }
    
    public void subMenu() {
        while (true) {
            vista.subMenu();
            int opcion = vista.opcion();
            if (opcion == 0) break;
            try {      
                HashMap<Integer, Libro> biblioteca = funcionalidades.leer();
                switch (opcion) {
                    case 1 -> vista.buscar(biblioteca, vista.pedirLibro(biblioteca));
                    case 2 -> vista.mostrar(biblioteca);
                    case 3 -> funcionalidades.insertar(vista.crearLibro());
                    case 4 -> funcionalidades.borrar(vista.pedirLibro(biblioteca));
                    case 5 -> funcionalidades.modificar(vista.pedirLibro(biblioteca), vista.crearLibro());
                    case 6 -> funcionalidades.restablecer();
                    case 7 -> subMenuTraspasarDatos();
                    default -> vista.opcionNoDisponible();
                }
            } catch (Exception e) {}
        }
    }

    public void subMenuTraspasarDatos() {
        HashMap<Integer, Libro> biblioteca = funcionalidades.leer();
        if (biblioteca.isEmpty()) {
            vista.errorVacío();
            return;
        }
        while (true) {
            vista.subMenuTraspasarDatos();
            int opcion = vista.opcion();
            if (opcion == 0) break;
            try {       
                switch (opcion) {
                    case 1 -> new FicheroTxt(vista.pedirFichero()).guardar(biblioteca);
                    case 2 -> new FicheroBin(vista.pedirFichero()).guardar(biblioteca);
                    case 3 -> new FicheroXML(vista.pedirFichero()).guardar(biblioteca);
                    case 4 -> new BBDDMySQL(vista.pedirBBDD()).guardar(biblioteca);
                    case 5 -> new BBDDMySQLHibernate().guardar(biblioteca);
                    case 6 -> new BBDDObjetos().guardar(biblioteca);
                    case 7 -> new BBDDMongo(vista.pedirBBDD()).guardar(biblioteca);
                    default -> vista.opcionNoDisponible();
                }
            } catch (Exception e) {}
        }
    }

}