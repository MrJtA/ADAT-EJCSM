package accesodatos_alumnos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IntermediarioBD_AG {


	// Conexion con la BD. La crearemos en el constructor y se puede utilizar la misma en todos los métodos
	Connection conn;

	/*
	 * Constructor de la clase. Se establece la conexión
	 * Modificar si hay algún dato diferente (PUERTO, username o password)
	 */
	
	public IntermediarioBD_AG() { 
		String driver = "com.mysql.cj.jdbc.Driver";
		String database = "ad_ex_AG";
		String hostname = "localhost";
		String port = "3306"; 	// Modificar si es el 3307
		String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
		String username = "root";
		String password = "root";  // Modificar si es otra
		
		try {
			Class.forName(driver);
			//System.out.println(url);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	
	/*
	 * Recibe un texto. Busca todos los grupos cuyo nombre posea ese texto (podría haber más de uno)
	 * 
	 * Posible Formato salida
	 * 
	 	--------------------------------------------------------------
		ID: 5 - Nombre: Rosalía 
		--------------------------------------------------------------
		ID: 75 - Nombre: Rozalen 
		--------------------------------------------------------------		
	 * 
	 * 
	 */
	
	public void buscarUnGrupo(String nombreGrupo) {
		String query = "SELECT * FROM grupos WHERE nombre LIKE ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, "%" + nombreGrupo + "%");
			ResultSet rs = ps.executeQuery();
			boolean hayResultados = false;
			while (rs.next()) {
				hayResultados = true;
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				System.out.println("ID: " + id + " - Nombre: " + nombre);
			}
			if (hayResultados) {
				System.out.println("--------------------------------------------------------------");
			} else {
				System.out.println("No se han encontrado resultados.");
			}
		} catch (Exception e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
		}
	}

	/*
	 * Insertar. Recibe el nombre del grupo que hay que insertar
	 */
	
	public void insertarUnGrupo(String nombreGrupo) {	
		String query = "INSERT INTO grupos (nombre) VALUES(?)";
		try (PreparedStatement ps = conn.prepareStatement(query)) {  
			ps.setString(1, nombreGrupo);
			ps.executeUpdate();
			System.out.println("Se ha insertado el grupo correctamente.");
		} catch (Exception e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
		}	
	}
	
	/*
	 * Modificar. Recibe el id del grupo a modificar y el nuevo nombre
	 */
	
	public void modificarUnGrupo(int idModificado, String nombreNuevo) {
		String query = "UPDATE grupos SET nombre = ? WHERE id = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {  
			ps.setString(1, nombreNuevo);
			ps.setInt(2, idModificado);
			int filas = ps.executeUpdate();
			System.out.println("Se ha modificado el grupo correctamente.");
			System.out.println("Filas afectadas: " + filas);
		} catch (Exception e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
		}	
	}
	
	/*
	 * Borrar. Recibe el id del grupo a borrar
	 */
	
	public void borrarUnGrupo(int idGrupo) {
		String query = "DELETE FROM grupos WHERE id = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idGrupo);
			int filas = ps.executeUpdate();
			System.out.println("Se ha borrado el grupo correctamente.");
			System.out.println("Filas afectadas: " + filas);
		} catch (Exception e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
		}	
	}

	/*
	 * Consulta de albumes. Se valorará si se muestra el nombre del grupo en lugar del ID
	 * Varias opciones para hacerlo
	 */
	
	public void consultaAlbumes() {
		String query = "SELECT albumes.id AS idAlbum, albumes.titulo AS nombreAlbum, albumes.autor AS nombreAutor FROM albumes JOIN grupos ON albumes.autor = grupos.id";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();
			boolean hayResultados = false;
			while (rs.next()) {
				hayResultados = true;
				int id = rs.getInt("idAlbum");
				String titulo = rs.getString("nombreAlbum");
				String nombreDirector = rs.getString("nombreAutor");
				System.out.println("--------------------------------------------------------------");
				System.out.println("ID: " + id + " - Titulo: " + titulo + " - Director: " + nombreDirector);
			}
			if (hayResultados) {
				System.out.println("--------------------------------------------------------------");
			} else {
				System.out.println("No se han encontrado resultados.");
			}
		} catch (SQLException e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
		}
	}
	
	
	
}
