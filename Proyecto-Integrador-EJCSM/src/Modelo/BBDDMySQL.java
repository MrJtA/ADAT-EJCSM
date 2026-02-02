package Modelo;

import java.sql.*;
import java.util.*;

public final class BBDDMySQL implements Funcionalidades {
    
    private Connection conexion;
    private String nombreBBDD;
    private String url;
    private String usuario;
    private String contraseña;
    private final HashMap<Integer,Libro> biblioteca;
    
    public BBDDMySQL(String nombreBBDD) throws SQLException {
        this.url = "jdbc:mysql://localhost:3306/";
        this.nombreBBDD = nombreBBDD;
        this.usuario = "root";
        this.contraseña = "root";
        try {
            crearBBDD(this.nombreBBDD);
            this.conexion = DriverManager.getConnection(this.url + this.nombreBBDD, this.usuario, this.contraseña);
            System.out.println("Conexión principal a la BD '" + nombreBBDD + "' establecida.");
        } catch (SQLException e) {
            System.err.println("Error al establecer conexión inicial con la base de datos: " + e.getMessage());
            throw e;
        }
        this.biblioteca = leer();
    }

    private void crearBBDD(String nombreDatabase) throws SQLException {
        try (Connection caux = DriverManager.getConnection(this.url, this.usuario, this.contraseña)) {
            boolean existeDatabase = false;
            String queryVerificar = "SHOW SCHEMAS LIKE ?"; 
            try (PreparedStatement ps = caux.prepareStatement(queryVerificar)) {
                ps.setString(1, nombreDatabase);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        existeDatabase = true;
                    }
                }
            }
            if (!existeDatabase) {
                String queryCrearBBDD = "CREATE DATABASE " + nombreDatabase;
                try (Statement stmt = caux.createStatement()) {
                    stmt.executeUpdate(queryCrearBBDD);
                    System.out.println("Base de datos '" + nombreDatabase + "' creada correctamente.");
                }
            }
            String queryCrearTabla = "CREATE TABLE IF NOT EXISTS " + nombreDatabase + ".libro(isbn int primary key, titulo varchar (100), autor varchar (100), editorial varchar (100), genero varchar (100))";
            try (Statement stmt = caux.createStatement()) {
                stmt.executeUpdate(queryCrearTabla);
                System.out.println("Tabla 'libro' verificada/creada en '" + nombreDatabase + "'.");
            }
        }
    }
    
    @Override
    public HashMap<Integer,Libro> leer() {
        HashMap<Integer,Libro> aux = new HashMap<>();
        String query = "SELECT * FROM libro";
        try (Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                int isbn = rs.getInt("isbn");
                String nombre = rs.getString("titulo");
                String autor = rs.getString("autor");
                String editorial = rs.getString("editorial");
                String genero = rs.getString("genero");
                Libro libro = new Libro(isbn, nombre, autor, editorial, genero);
                aux.put(isbn, libro);
            }
        } catch (SQLException e) {
            System.out.println("Error: No se han podido leer los datos de la base de datos '" + this.nombreBBDD + "'.");
            System.err.println(e.getMessage());
        }
        return aux;
    }

    @Override
    public void guardar(HashMap<Integer,Libro> datos) {
        try (Connection conn = DriverManager.getConnection(this.url, this.usuario, this.contraseña)) {
            conn.setAutoCommit(false);
            try (Statement st = conn.createStatement()) {
                st.executeUpdate("TRUNCATE TABLE libro");
            }
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO libro (isbn, titulo, autor, editorial, genero) VALUES (?, ?, ?, ?, ?)")) {
                for (Libro libro : datos.values()) {
                    ps.setInt(1, libro.getIsbn());
                    ps.setString(2, libro.getTitulo());
                    ps.setString(3, libro.getAutor());
                    ps.setString(4, libro.getEditorial());
                    ps.setString(5, libro.getGenero());
                    ps.addBatch();
                }
                ps.executeBatch();
                conn.commit();
                System.out.println("Los datos de la base de datos se han traspasado correctamente a la base de datos '" + nombreBBDD + "'.");
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.err.println("Error: No se ha podido establecer la conexión con la base de datos '" + this.nombreBBDD + "'.");
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
        String query = "INSERT INTO libro (isbn, titulo, autor, editorial, genero) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, isbn);
            ps.setString(2, libro.getTitulo());
            ps.setString(3, libro.getAutor());
            ps.setString(4, libro.getEditorial());
            ps.setString(5, libro.getGenero());
            int filasInsertadas = ps.executeUpdate();
            System.out.println("El libro con el ISBN '" + isbn + "'' se ha registrado correctamente.");
            System.out.println("Filas insertadas: " + filasInsertadas);
            this.biblioteca.put(libro.getIsbn(), libro);
        } catch (SQLException e) {
            System.err.println("Error: No se ha podido insertar el libro con el ISBN '" + isbn + "' en la base de datos.");
            System.err.println(e.getMessage());
        }
    }
    
    @Override
    public void borrar(int isbn) {
        if (!this.biblioteca.containsKey(isbn)) {
            System.out.println("Error: No existe ningún libro con el ISBN '" + isbn + "'.");
            return;
        }
        String query = "DELETE FROM libro WHERE isbn = ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, isbn);
            int filasEliminadas = ps.executeUpdate();
            if (filasEliminadas <= 0) {
                System.out.println("Error: No se ha modificado ninguna fila.");
            }
            System.out.println("El libro con el ISBN: " + isbn + " se ha borrado correctamente.");
            System.out.println("Filas eliminadas: " + filasEliminadas);
            this.biblioteca.remove(isbn);
        } catch (SQLException e) {
            System.err.println("Error: No se ha podido borrar el con el ISBN '" + isbn + "' en la base de datos.");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void modificar(int isbn, Libro libro) {
        if (!this.biblioteca.containsKey(isbn)) {
            System.out.println("Error: No existe ningún libro con el ISBN '" + isbn + "'.");
            return;
        }
        String query = "UPDATE libro SET isbn = ?, titulo = ?, autor = ?, editorial = ?, genero = ? WHERE isbn = ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, libro.getIsbn());
            ps.setString(2, libro.getTitulo());
            ps.setString(3, libro.getAutor());
            ps.setString(4, libro.getEditorial());
            ps.setString(5, libro.getGenero());
            ps.setInt(6, isbn); 
            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas <= 0) {
                System.out.println("Error: No se ha modificado ninguna fila.");
            }
            this.biblioteca.remove(isbn);
            this.biblioteca.put(libro.getIsbn(), libro);
            System.out.println("El libro con el ISBN '" + isbn + "' se ha sustituido correctamente por el libro con el ISBN '" + libro.getIsbn() + "'.");
            System.out.println("Filas modificadas: " + filasAfectadas);
        } catch (SQLException e){
            System.err.println("Error: No se ha podido modificar el libro con el ISBN '" + isbn + "' en la base de datos.");
            System.err.println(e.getMessage());
        }
    }

    /*
    
    @Override
    public void buscar(int isbn) {
        String query = "SELECT * FROM libro WHERE isbn = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) { 
            ps.setInt(1, isbn);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Libro libro = this.biblioteca.get(isbn);
                    System.out.println("Libro encontrado con el ISBN '" + isbn + "':");
                    System.out.println(libro); 
                } else {
                    System.out.println("No se encontró el libro con el ISBN '" + isbn + "'.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error: No se ha podido buscar el libro con el ISBN '" + isbn + "' en la base de datos.");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void mostrar() {
    String query = "SELECT * FROM libro";
        int contador = 0;
        try (PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) { 
            System.out.println("Libros registrados: ");
            while (rs.next()) {
                Libro libro = new Libro(
                    rs.getInt("isbn"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("editorial"),
                    rs.getString("genero")
                );
                System.out.println(libro);
                contador++;
            }
            System.out.println("Total de libros mostrados: " + contador);
        } catch (SQLException e) {
            System.err.println("Error: No se han podido mostrar los libros en la base de datos.");
            System.err.println(e.getMessage());
        }
    }

    */

}