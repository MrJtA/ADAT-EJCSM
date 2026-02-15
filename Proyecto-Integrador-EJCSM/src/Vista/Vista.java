package Vista;

import Modelo.Libro;
import java.util.*;

public class Vista {

    final Scanner sc = new Scanner(System.in);

    public void introduccion() {
        System.out.println("--------------------------------------------");
        System.out.println("Bienvenido al Proyecto Integrador de EJCSM.");
    }

    public void menu() {
        System.out.println("--------------------------------------------");
        System.out.println("0. Salir.");
        System.out.println("1. Ficheros.");
        System.out.println("2. Bases de datos.");
        System.out.println("--------------------------------------------");
    }

    public void menuFichero() {
        System.out.println("--------------------------------------------");
        System.out.println("0. Volver al menú principal.");
        System.out.println("1. Ficheros de texto.");
        System.out.println("2. Ficheros binarios.");
        System.out.println("3. Ficheros XML.");
        System.out.println("--------------------------------------------");
    }

    public void menuBBDD() {
        System.out.println("--------------------------------------------");
        System.out.println("0. Volver al menú principal.");
        System.out.println("1. Bases de datos MySQL.");
        System.out.println("2. Bases de datos MySQL con Hibernate.");
        System.out.println("3. Bases de datos orientadas a objetos.");
        System.out.println("4. Bases de datos Mongo.");
        System.out.println("5. Bases de datos con PHP.");
        System.out.println("--------------------------------------------");
    }

    public void subMenu() {
        System.out.println("--------------------------------------------");
        System.out.println("0. Volver al menú.");
        System.out.println("1. Buscar un libro.");
        System.out.println("2. Mostrar libros.");
        System.out.println("3. Añadir un libro.");
        System.out.println("4. Borrar un libro.");
        System.out.println("5. Modificar un libro.");
        System.out.println("6. Borrar todos los libros.");
        System.out.println("7. Traspasar datos.");
        System.out.println("--------------------------------------------");
    }

    public void subMenuTraspasarDatos() {
        System.out.println("--------------------------------------------");
        System.out.println("0. Volver al submenú.");
        System.out.println("1. Traspasar datos a un fichero de texto.");
        System.out.println("2. Traspasar datos a un fichero binario.");
        System.out.println("3. Traspasar datos a un fichero XML.");
        System.out.println("4. Traspasar datos a una base de datos MySQL.");
        System.out.println("5. Traspasar datos a una base de datos MySQL con Hibernate.");
        System.out.println("6. Traspasar datos a una base de datos de objetos.");
        System.out.println("7. Traspasar datos a una base de datos Mongo.");
        System.out.println("--------------------------------------------");
    }

    public void opcionNoDisponible() {
        System.out.println("Error: Introduce una opción disponible.");
    }

    public void despedida() {
        System.out.println("Muchas gracias por su visita.");
    }

    public int opcion() {
        int opcion = -1;
        boolean entradaValida = false;
        do { 
            try {
                System.out.print("Seleccione una opción: ");
                opcion = Integer.parseInt(sc.nextLine()); 
                entradaValida = true;
            } catch (NumberFormatException e) { 
                System.err.println("Error: Opción inválida.");
            }
        } while (!entradaValida);
        return opcion;
    }

    public String pedirFichero() {
        System.out.println("Introduce el nombre fichero: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            return "Nuevo Fichero";
        }
        return nombre;
    }

    public String pedirBBDD() {
        System.out.println("Introduce el nombre de la base de datos: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            return "Nueva BBDD";
        }
        return nombre;
    }

    public Libro crearLibro() {
        Libro libro = null;
        boolean entradaValida = false;
        do {
            try {
                System.out.println("Introduce a continuación los datos del nuevo libro.");
                System.out.print("ISBN: ");
                int isbn = Integer.parseInt(sc.nextLine());
                System.out.print("Titulo: ");
                String titulo = sc.nextLine().trim();
                System.out.print("Autor: ");
                String autor = sc.nextLine().trim();
                System.out.print("Editorial: ");
                String editorial = sc.nextLine().trim();
                System.out.print("Género: ");
                String genero = sc.nextLine().trim();
                libro = new Libro(isbn, titulo, autor, editorial, genero);
                entradaValida = true;
            } catch (NumberFormatException | InputMismatchException e) {
                System.err.println("Error: ISBN inválido. Debe ser un número entero.");
            }
        } while (!entradaValida);
        return libro;
    }

    public int pedirLibro(HashMap<Integer, Libro> biblioteca) {
        String entrada;
        int isbn = -1;
        System.out.print("Introduce el ISBN del libro: ");
        entrada = sc.nextLine();
        try {
            isbn = Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.err.println("Error: ISBN inválido. Debe ser un número entero.");
        }
        return isbn;
    }

    public void buscar(HashMap<Integer, Libro> biblioteca, int isbn) {
        if (biblioteca.containsKey(isbn)) {
            System.out.println("Libro encontrado con el ISBN '" + isbn + "':");
            System.out.println("- " + biblioteca.get(isbn).toString());
        } else {
            this.errorLibroInexistente(isbn);
        }
    }

    public void mostrar(HashMap<Integer, Libro> biblioteca) {
        if (biblioteca.isEmpty()) {
            this.errorVacío();
        } else {
            System.out.println("A continuación se muestran todos los libros registrados: ");
            List<Libro> listaOrdenada = new ArrayList<>(biblioteca.values());
            listaOrdenada.sort(Comparator.comparingInt(Libro::getIsbn));
            for (Libro libro : listaOrdenada) {
                System.out.println("- " + libro.toString());
            }
        }
    }

    public void creacionFichero(String nombreFichero) {
        System.out.println("El fichero '" + nombreFichero + "' no existe, y se ha creado correctamente.");
    }

    public void errorCreacionFichero(String nombreFichero) {
        System.err.println("Error: No se ha podido encontrar o crear el fichero '" + nombreFichero + "'.");
    }

    public void errorFicheroInexistente(String nombreFichero) {
        System.err.println("Error: El fichero '" + nombreFichero + "' no existe.");
    }

    public void errorLecturaFichero(String nombreFichero) {
        System.err.println("Error: No se han podido leer los datos del fichero '" + nombreFichero + "'.");
    }

    public void guardadoFichero(String nombreFichero) {
        System.out.println("Los datos del fichero '" + nombreFichero + "' se han guardado correctamente.");
    }
    
    public void errorGuardadoFichero(String nombreFichero) {
        System.err.println("Error: No se han podido guardar los datos en el fichero '" + nombreFichero + "'.");
    }

    public void conexionBBDD(String nombreBBDD) {
        System.out.println("Conexión principal a la base de datos '" + nombreBBDD + "' establecida correctamente.");
    }

    public void errorConexionBBDD(String nombreBBDD) {
        System.out.println("Error: No se ha podido establecer la conexión a la base de datos '" + nombreBBDD + "'.");
    }

    public void creacionBBDD(String nombreBBDD) {
        System.out.println("La base de datos '" + nombreBBDD + "' no existe y se ha creado correctamente.");
    }

    public void creacionTabla(String nombreBBDD) {
        System.out.println("La tabla 'libro' se ha verificado/creado en la base de datos '" + nombreBBDD + "' correctamente.");
    }

    public void errorLecturaBBBDD(String nombreBBDD) {
        System.out.println("Error: No se han podido leer los datos de la base de datos '" + nombreBBDD + "'.");
    }

    public void guardadoBBDD(String nombreBBDD) {
        System.out.println("Los datos de la base de datos se han guardado a la nueva base de datos '" + nombreBBDD + "' correctamente.");
    }

    public void errorGuardadoBBDD(String nombreBBDD) {
        System.out.println("Error: No se han podido guardar los datos en la base de datos '" + nombreBBDD + "'.");
    }

    public void errorLibroRepetido(int isbn) {
        System.out.println("Error: Hay un libro repetido con ISBN '" + isbn + "'.");
    }

    public void errorLibroExistente(int isbn) {
        System.out.println("Error: Ya existe un libro con ISBN '" + isbn + "'.");
    }

    public void errorLibroInexistente(int isbn) {
        System.out.println("Error: No existe ningún libro con ISBN '" + isbn + "'.");
    }

    public void insercionLibro(int isbn) {
        System.out.println("El libro con ISBN '" + isbn + "' se ha insertado correctamente.");
    }

    public void errorInsercionLibro(int isbn) {
        System.err.println("Error: No se ha podido insertar el libro con el ISBN '" + isbn + "'.");
    }

    public void borradoLibro(int isbn) {
        System.out.println("El libro con ISBN '" + isbn + "' se ha borrado correctamente.");
    }

    public void errorBorradoLibro(int isbn) {
        System.err.println("Error: No se ha podido borrar el libro con el ISBN '" + isbn + "'.");
    }

    public void modificacionLibro(int isbnAntiguo, int isbnNuevo) {
        System.out.println("El libro con ISBN '" + isbnAntiguo + "' se ha modificado correctamente por el libro con ISBN '" + isbnNuevo + "'.");
    }

    public void errorModificacionLibro(int isbn) {
        System.err.println("Error: No se ha podido modificar el libro con el ISBN '" + isbn + "'.");
    }

    public void restablecerLibros() {
        System.out.println("Se han eliminado todos los datos correctamente.");
    }

    public void errorRestablecerLibros() {
        System.err.println("Error: No se han podido eliminar todos los datos.");
    }

    public void errorBusquedaLibro(int isbn) {
        System.err.println("Error: No se ha podido buscar el libro con el ISBN '" + isbn + "'.");
    }

    public void errorMuestraLibros() {
        System.err.println("Error: No se han podido mostrar los libros registrados.");
    }

    public void errorVacío() {
        System.out.println("No hay ningún libro registrado.");
    }
    
}