package Controlador;

import java.sql.SQLException;
import java.util.Map;
import com.mongodb.MongoException;

import Modelo.*;
import Vista.Vista;

public class Controlador {

    private final Vista vista = new Vista();
    private Funcionalidades funcionalidades;

    public static void main(String[] args) {
        Controlador app = new Controlador();
        app.menu();
    }

    public void menu() {
        vista.introduccion();
        boolean seguir = true;
        while (seguir) {
            vista.menu();
            int opcion = vista.opcion();
            switch (opcion) {
                case 0 -> {
                    vista.despedida();
                    seguir = false;
                }
                case 1 -> subMenuFichero();
                case 2 -> subMenuBBDD();
                default -> vista.opcionNoDisponible();
            }
        }
    }

    public void subMenuFichero() {
        boolean seguir = true;
        while (seguir) {
            vista.subMenuFichero();
            int opcion = vista.opcion();
            switch (opcion) {
                case 0 -> seguir = false;
                case 1 -> {
                    String nombreFichero = vista.pedirFichero();
                    if (nombreFichero == null) continue;
                    funcionalidades = new FicheroTxt(nombreFichero);
                    subMenu();
                }
                case 2 -> {
                    String nombreFichero = vista.pedirFichero();
                    if (nombreFichero == null) continue;
                    funcionalidades = new FicheroBin(nombreFichero);
                    subMenu();
                }
                case 3 -> {
                    String nombreFichero = vista.pedirFichero();
                    if (nombreFichero == null) continue;
                    funcionalidades = new FicheroXML(nombreFichero);
                    subMenu();
                }
                default -> vista.opcionNoDisponible();
            }
        }
    }

    public void subMenuBBDD() {
        boolean seguir = true;
        while (seguir) {
            vista.subMenuBBDD();
            int opcion = vista.opcion();
            switch (opcion) {
                case 0 -> seguir = false;
                case 1 -> {
                    String nombreBBDD = vista.pedirBBDD();
                    try {
                        funcionalidades = new BBDDMySQL(nombreBBDD);
                    } catch (SQLException e) {
                        vista.falloBBDD(nombreBBDD);
                    }
                    subMenu();
                }
                case 2 -> {
                    funcionalidades = new BBDDMySQLHibernate();
                    subMenu();
                }
                case 3 -> {
                    funcionalidades = new BBDDObjetos();
                    subMenu();
                }
                case 4 -> {
                    funcionalidades = new BBDDMongo(vista.pedirBBDD());
                    subMenu();
                }
                default -> vista.opcionNoDisponible();
            }
        }
    }
    
    public void subMenu() {
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
                    String nombreFichero = vista.pedirFichero();
                    if (nombreFichero == null) continue;
                    FicheroTxt fichero = new FicheroTxt(nombreFichero);
                    fichero.guardar(funcionalidades.leer());
                }
                case 7 -> {
                    String nombreFichero = vista.pedirFichero();
                    if (nombreFichero == null) continue;
                    FicheroBin fichero = new FicheroBin(nombreFichero);
                    fichero.guardar(funcionalidades.leer());
                }
                case 8 -> {
                    String nombreFichero = vista.pedirFichero();
                    if (nombreFichero == null) continue;
                    FicheroXML fichero = new FicheroXML(nombreFichero);
                    fichero.guardar(funcionalidades.leer());
                }
                case 9 -> {
                    String nombreBBDD = vista.pedirBBDD();
                    try {
                        BBDDMySQL bbdd = new BBDDMySQL(nombreBBDD);
                        bbdd.guardar(funcionalidades.leer());
                    } catch (SQLException e) {
                        vista.falloBBDD(nombreBBDD);
                    }
                }
                case 10 -> {
                    try {
                        BBDDMySQLHibernate bbdd = new BBDDMySQLHibernate();
                        bbdd.guardar(funcionalidades.leer());
                    } catch (Exception e) {
                        vista.falloBBDD("biblioteca");
                    }
                }
                case 11 -> {
                    BBDDObjetos bbdd = new BBDDObjetos();
                    bbdd.guardar(funcionalidades.leer());
                }
                case 12 -> {
                    String nombreBBDD = vista.pedirBBDD();
                    try {
                        BBDDMongo bbdd = new BBDDMongo(nombreBBDD);
                        bbdd.guardar(funcionalidades.leer());
                    } catch (MongoException e) {
                        vista.falloBBDD(nombreBBDD);
                    }
                }
                default -> vista.opcionNoDisponible();
            }
        }
    }

}