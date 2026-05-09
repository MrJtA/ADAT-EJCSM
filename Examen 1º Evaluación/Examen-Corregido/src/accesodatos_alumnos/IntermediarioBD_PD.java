package accesodatos_alumnos;

import java.sql.*;

public class IntermediarioBD_PD {

	// Conexion con la BD. La crearemos en el constructor
	Connection conn;

	/*
	 * Constructor de la clase. Se establece la conexión
	 * Modificar si hay algún dato diferente (PUERTO, username o password)
	 */

	public IntermediarioBD_PD() {
		String driver = "com.mysql.cj.jdbc.Driver";
		String database = "ad_ex_PD";
		String hostname = "localhost";
		String port = "3306"; // Modificar si es el 3307
		String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";
		String username = "root";
		String password = "";
		try {
			Class.forName(driver);
			// System.out.println(url);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
		}
	}

	/*
	 * Recibe un texto. Busca todos los directores cuyo nombre posea ese texto
	 * (podría haber más de uno)
	 * 
	 * Posible formato salida
	 * 
	 * --------------------------------------------------------------
	 * ID: 1 - Nombre: Fernando Leon
	 * --------------------------------------------------------------
	 * ID: 2 - Nombre: Fernando Romero
	 * --------------------------------------------------------------
	 * 
	 * 
	 */

	public void buscarUnDirector(String nombreDirector) {
		String query = "SELECT * FROM directores WHERE nombre LIKE ?;";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, "%" + nombreDirector + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("nombre");
				System.out.println("--------------------------------------------------------------");
				System.out.println("ID: " + id + " - Nombre: " + name);
			}
			System.out.println("--------------------------------------------------------------");
		} catch (SQLException e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
		}
	}

	/*
	 * Insertar. Recibe el nombre del director que hay que insertar
	 */

	public void insertarUnDirector(String nombreDirector) {
		String query = "INSERT INTO directores (nombre) VALUES(?)";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, nombreDirector);
			ps.executeUpdate();
			System.out.println("Se ha insertado el director correctamente.");
		} catch (SQLException e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
		}
	}

	/*
	 * Modificar. Recibe idDirector y nombre nuevo
	 */

	public void modificarUnDirector(int idDirector, String nombreNuevo) {
		String query = "UPDATE directores SET nombre = ? WHERE id = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setString(1, nombreNuevo);
			ps.setInt(2, idDirector);
			int filas = ps.executeUpdate();
			System.out.println("Se ha modificado el director correctamente.");
			System.out.println("Filas afectadas: " + filas);
		} catch (SQLException e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
		}
	}

	/*
	 * Borrar. Recibe el id del director que hay que borrar
	 */

	public void borrarUnDirector(int idDirector) {
		String query = "DELETE FROM directores WHERE id = ?";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ps.setInt(1, idDirector);
			int filas = ps.executeUpdate();
			System.out.println("Se ha borrado el director correctamente.");
			System.out.println("Filas afectadas: " + filas);         
		} catch (SQLException e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
		}
	}

	/*
	 * Consulta de películas. 
	 * Se valorará si se muestra el nombre del director en lugar del ID
	 * Varias opciones para hacerlo
	 * Se opta por el JOIN que es el más fácil pero se podría hacer con subconsultas 
	 * o incluso lanzando una nueva consulta: por cada película preguntamos por el nombre del director buscando por el id...
	 */

	public void consultaPeliculas() {
		String query = "SELECT peliculas.id AS idPelicula, peliculas.titulo AS titulo, directores.nombre AS nombre FROM peliculas JOIN directores ON peliculas.director = directores.id";
		try (PreparedStatement ps = conn.prepareStatement(query)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("idPelicula");
				String titulo = rs.getString("titulo");
				String nombreDirector = rs.getString("nombre");
				System.out.println("--------------------------------------------------------------");
				System.out.println("ID: " + id + " - Titulo: " + titulo + " - Director: " + nombreDirector);
			}
			System.out.println("--------------------------------------------------------------");
		} catch (SQLException e) {
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
		}
	}	

}
