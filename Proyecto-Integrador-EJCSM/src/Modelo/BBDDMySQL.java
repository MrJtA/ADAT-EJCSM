package Modelo;

import Vista.Vista;
import java.sql.*;
import java.util.*;

public final class BBDDMySQL implements Funcionalidades {
    
    private Connection conexion;
    private String nombreBBDD;
    private String url;
    private String usuario;
    private String contraseña;
    private final HashMap<Integer,Libro> biblioteca;
    private final Vista vista;
    
    public BBDDMySQL(String nombreBBDD) {
        this.url = "jdbc:mysql://localhost:3306/";
        this.nombreBBDD = nombreBBDD;
        this.usuario = "root";
        this.contraseña = "root";
        this.vista = new Vista();
        try {
            crearBBDD(this.nombreBBDD);
            this.conexion = DriverManager.getConnection(this.url + this.nombreBBDD, this.usuario, this.contraseña);
            this.vista.conexionBBDD(this.nombreBBDD);
        } catch (SQLException e) {
            this.vista.errorConexionBBDD(this.nombreBBDD);
        }
        this.biblioteca = leer();
    }

    private void crearBBDD(String nombreBBDD) throws SQLException {
        try (Connection caux = DriverManager.getConnection(this.url, this.usuario, this.contraseña)) {
            boolean existeBBDD = false;
            String queryVerificar = "SHOW DATABASES LIKE ?"; 
            try (PreparedStatement ps = caux.prepareStatement(queryVerificar)) {
                ps.setString(1, nombreBBDD);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        existeBBDD = true;
                    }
                }
            }
            if (!existeBBDD) {
                try (Statement stmt = caux.createStatement()) {
                    stmt.executeUpdate("CREATE DATABASE " + nombreBBDD);
                    this.vista.creacionBBDD(nombreBBDD);
                }
            }
            String queryCrearTabla = "CREATE TABLE IF NOT EXISTS " + nombreBBDD + ".libro (" +
                                    "isbn INT PRIMARY KEY, " +
                                    "titulo VARCHAR(100), " +
                                    "autor VARCHAR(100), " +
                                    "editorial VARCHAR(100), " +
                                    "genero VARCHAR(100))";
            try (Statement stmt = caux.createStatement()) {
                stmt.executeUpdate(queryCrearTabla);
                this.vista.creacionTabla(nombreBBDD);
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
                if (aux.containsKey(isbn)) {
                    this.vista.errorLibroRepetido(isbn);
                } else {
                    aux.put(isbn, libro);
                }
            }
        } catch (SQLException e) {
            this.vista.errorLecturaBBBDD(this.nombreBBDD);
        }
        return aux;
    }

    @Override
    public void guardar(HashMap<Integer, Libro> datos) {
        try (Connection conn = DriverManager.getConnection(this.url + this.nombreBBDD, this.usuario, this.contraseña)) {
            conn.setAutoCommit(false);
            try (Statement st = conn.createStatement()) {
                st.executeUpdate("TRUNCATE TABLE libro");
            }
            String query = "INSERT INTO libro (isbn, titulo, autor, editorial, genero) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
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
                this.vista.guardadoBBDD(this.nombreBBDD);
            } catch (SQLException e) {
                conn.rollback();
                this.vista.errorGuardadoBBDD(this.nombreBBDD);
            }
        } catch (SQLException e) {
            this.vista.errorGuardadoBBDD(this.nombreBBDD);
        }
    }

    @Override
    public void insertar(Libro libro) {
        int isbn = libro.getIsbn();
        if (this.biblioteca.containsKey(isbn)) {
            this.vista.errorLibroExistente(isbn);
            return;
        }
        String query = "INSERT INTO libro (isbn, titulo, autor, editorial, genero) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, isbn);
            ps.setString(2, libro.getTitulo());
            ps.setString(3, libro.getAutor());
            ps.setString(4, libro.getEditorial());
            ps.setString(5, libro.getGenero());
            ps.executeUpdate();
            this.vista.insercionLibro(isbn);
            this.biblioteca.put(libro.getIsbn(), libro);
        } catch (SQLException e) {
            this.vista.errorInsercionLibro(isbn);
        }
    }
    
    @Override
    public void borrar(int isbn) {
        if (!this.biblioteca.containsKey(isbn)) {
            this.vista.errorLibroInexistente(isbn);
            return;
        }
        String query = "DELETE FROM libro WHERE isbn = ?";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.setInt(1, isbn);
            ps.executeUpdate();
            this.vista.borradoLibro(isbn);
            this.biblioteca.remove(isbn);
        } catch (SQLException e) {
            this.vista.errorBorradoLibro(isbn);
        }
    }

    @Override
    public void modificar(int isbn, Libro libro) {
        if (!this.biblioteca.containsKey(isbn)) {
            this.vista.errorLibroInexistente(isbn);
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
            ps.executeUpdate();
            this.biblioteca.remove(isbn);
            this.biblioteca.put(libro.getIsbn(), libro);
            this.vista.modificacionLibro(isbn, libro.getIsbn());
        } catch (SQLException e){
            this.vista.errorModificacionLibro(isbn);
        }
    }

    @Override
    public void restablecer() {
        String query = "DELETE FROM libro";
        try (PreparedStatement ps = this.conexion.prepareStatement(query)) {
            ps.executeUpdate();
            this.biblioteca.clear();
            this.vista.restablecerLibros();
        } catch (SQLException e) {
            this.vista.errorRestablecerLibros();
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
                    this.vista.buscar(this.biblioteca, isbn);
                } else {
                    this.vista.errorLibroInexistente(isbn);
                }
            }
        } catch (SQLException e) {
            this.vista.errorBusquedaLibro(isbn);
        }
    }

    @Override
    public void mostrar() {
    String query = "SELECT * FROM libro";
        try (PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet rs = ps.executeQuery()) {
            HashMap<Integer,Libro> aux = new HashMap<>();
            while (rs.next()) {
                int isbn = rs.getInt("isbn");
                String nombre = rs.getString("titulo");
                String autor = rs.getString("autor");
                String editorial = rs.getString("editorial");
                String genero = rs.getString("genero");
                Libro libro = new Libro(isbn, nombre, autor, editorial, genero);
                if (aux.containsKey(isbn)) {
                    this.vista.errorLibroRepetido(isbn);
                    return;
                }
                aux.put(isbn, libro);
            }
            this.vista.mostrar(aux);
        } catch (SQLException e) {
            this.vista.errorMuestraLibros();
        }
    }

    */

}