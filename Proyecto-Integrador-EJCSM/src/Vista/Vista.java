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
        System.out.println("2. Bases de datos");
        System.out.println("--------------------------------------------");
    }

    public void subMenuFichero() {
        System.out.println("--------------------------------------------");
        System.out.println("0. Volver al menú principal.");
        System.out.println("1. Ficheros de texto.");
        System.out.println("2. Ficheros binarios.");
        System.out.println("3. Ficheros XML.");
        System.out.println("--------------------------------------------");
    }

    public void subMenuBBDD() {
        System.out.println("--------------------------------------------");
        System.out.println("0. Volver al menú principal.");
        System.out.println("1. Bases de datos MySQL.");
        System.out.println("2. Bases de datos MySQL con Hibernate.");
        System.out.println("3. Bases de datos orientadas a objetos.");
        System.out.println("4. Bases de datos Mongo.");
        System.out.println("--------------------------------------------");
    }

    public void subMenu() {
        System.out.println("--------------------------------------------");
        System.out.println("0. Volver al menú principal.");
        System.out.println("1. Buscar un libro.");
        System.out.println("2. Mostrar libros.");
        System.out.println("3. Añadir un libro.");
        System.out.println("4. Borrar un libro.");
        System.out.println("5. Modificar un libro.");
        System.out.println("6. Traspasar datos a un fichero de texto.");
        System.out.println("7. Traspasar datos a un fichero binario.");
        System.out.println("8. Traspasar datos a un fichero XML.");
        System.out.println("9. Traspasar datos a una base de datos MySQL.");
        System.out.println("10. Traspasar datos a una base de datos MySQL con Hibernate.");
        System.out.println("11. Traspasar datos a una base de datos MySQL con Hibernate.");
        System.out.println("12. Traspasar datos a una base de datos Mongo.");
        System.out.println("--------------------------------------------");
    }

    public void opcionNoDisponible() {
        System.out.println("Error: Introduce una opción disponible.");
    }

    public void despedida() {
        System.out.println("Muchas gracias por su visita.");
    }

    public void falloBBDD(String nombreBBDD) {
        System.err.println("Error: No se ha podido establecer la conexión con la base de datos '" + nombreBBDD + "'.");
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
        System.out.print("Introduce el nombre fichero con el que quieres trabajar: ");
        String entrada = sc.nextLine().trim();
        if (entrada.isEmpty()) {
            System.out.println("Error: La ruta no puede estar vacía.");
            return null;
        }
        return entrada;
    }

    public String pedirBBDD() {
        System.out.println("Introduce el nombre de la base de datos: ");
        String nombre = sc.nextLine();
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
                String titulo = sc.nextLine();
                System.out.print("Autor: ");
                String autor = sc.nextLine();
                System.out.print("Editorial: ");
                String editorial = sc.nextLine();
                System.out.print("Género: ");
                String genero = sc.nextLine();
                libro = new Libro(isbn, titulo, autor, editorial, genero);
                entradaValida = true;
            } catch (NumberFormatException | InputMismatchException e) {
                System.err.println("Error: ISBN inválido. Debe ser un número entero.");
                System.out.println("Por favor, introduce un ISBN válido.");
            }
        } while (!entradaValida);
        return libro;
    }

    public int pedirLibro(Map<Integer, Libro> biblioteca) {
        String entrada;
        int isbn = -1;
        System.out.print("Introduce el ISBN o título del libro para la búsqueda: ");
        entrada = sc.nextLine();
        try {
            isbn = Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            System.out.println("Se buscará el libro por título.");
            for (Libro libro : biblioteca.values()) {
                if (entrada.equalsIgnoreCase(libro.getTitulo())) {
                    isbn = libro.getIsbn();
                    break;
                }
            }
        }
        return isbn;
    }

    public void buscar(Map<Integer, Libro> biblioteca, int isbn) {
        if (biblioteca.isEmpty()) {
            System.out.println("No hay ningún libro registrado.");
        } else {
            if (biblioteca.containsKey(isbn)) {
                System.out.println("Se ha encontrado el libro: " + biblioteca.get(isbn));
            } else {
                System.out.println("No se ha encontrado el libro.");
            }
        }
    }

    public void mostrar(Map<Integer, Libro> biblioteca) {
        if (biblioteca.isEmpty()) {
            System.out.println("No hay ningún libro registrado.");
        } else {
            System.out.println("Libros registrados: ");
            for (Libro libro : biblioteca.values()) {
                System.out.println(libro.toString());
            }
        }
    }

}