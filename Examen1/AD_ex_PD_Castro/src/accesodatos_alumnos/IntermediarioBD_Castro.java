package accesodatos_alumnos;

import java.util.Iterator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class IntermediarioBD_Castro {


	// Conexion con la BD. La crearemos en el constructor
	Connection conn;

	/*
	 * Constructor de la clase. Se establece la conexión
	 * Modificar si hay algún dato diferente (PUERTO, username o password)
	 */
	
	public IntermediarioBD_Castro() { 
		String driver = "com.mysql.cj.jdbc.Driver";
		String database = "ad_ex_PD";
		String hostname = "localhost";
		String port = "3306"; 	// Modificar si es el 3307
		String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false&serverTimezone=UTC";
		String username = "root";
		String password = "root";
		
		try {
			Class.forName(driver);
			//System.out.println(url);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	
	/*
	 * Recibe un texto. Busca todos los directores cuyo nombre posea ese texto (podría haber más de una)
	 * 
	 * Posible formato salida
	 * 
	 	--------------------------------------------------------------
		ID: 1 - Nombre: Fernando Leon
		--------------------------------------------------------------
		ID: 2 - Nombre: Fernando Romero
		--------------------------------------------------------------
	 * 
	 * 
	 */
	
	public void buscarUnDirector(String nombreDirector) {
        String query1 = "SELECT*FROM directores WHERE nombre = " + nombreDirector;
		try (Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query1)) {
		while (rs.next()) {
			int id = rs.getInt("id");
			String nombre = rs.getString("nombre");
			System.out.println("ID: " + id + " - Nombre: " + nombre);
		}
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
	}

	/*
	 * Insertar. Recibe el nombre del director que hay que insertar
	 */
	
	public void insertarUnDirector(String nombreDirector) {
		String query = "INSERT INTO directores (id, nombre) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, (int)(Math.random()*100));
			ps.setString(2, nombreDirector);
            int filasInsertadas = ps.executeUpdate();
            System.out.println("Filas insertadas: " + filasInsertadas);
        } catch (SQLException e) {
            System.err.println("Error al insertar libro: " + e.getMessage());
        }
	}
	
	/*
	 * Modificar. Recibe idDirector y nombre nuevo
	 */
	
	public void modificarUnDirector(int idDirector, String nombreNuevo) {
		String query = "UPDATE INTO directores SET nombre = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, nombreNuevo);
            ps.setInt(2, idDirector);
            int filasAfectadas = ps.executeUpdate();
            System.out.println("Filas modificadas: " + filasAfectadas);
        } catch (SQLException e){
            System.err.println("Error al modificar libro: " + e.getMessage());
        }
	}
	
	/*
	 * Borrar. Recibe el id del director que hay que borrar
	 */
	
	public void borrarUnDirector(int idDirector) {
		String query = "DELETE FROM directores WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idDirector);
            int filasEliminadas = ps.executeUpdate();
            System.out.println("Filas eliminadas: " + filasEliminadas);
        } catch (SQLException e) {
            System.err.println("Error al borrar libro: " + e.getMessage());
        }
	}
	
	
	/*
	 * Consulta de películas. Se valorará si se muestra el nombre del director en lugar del ID
	 * Varias opciones para hacerlo
	 */
	
	public void consultaPeliculas() {
		String query = "SELECT * FROM peliculas";
        try (Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                String nombre = rs.getString("TITULO");
                String director = rs.getString("DIRECTOR");
				System.out.println("Director: " + director + " - Título: " + nombre);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
	}
	
}
