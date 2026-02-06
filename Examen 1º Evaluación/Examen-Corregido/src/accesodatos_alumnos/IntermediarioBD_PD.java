package accesodatos_alumnos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
			e.printStackTrace();
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

		try {
			String query = "SELECT * FROM directores WHERE nombre = ?;";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, nombreDirector);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("nombre");
				System.out.println("--------------------------------------------------------------");
				System.out.printf("\tID: %d - Nombre: %s\n", id, name);
				System.out.println("--------------------------------------------------------------");
			}
			preparedStatement.close();
		} catch (Exception e) {
			System.err.println("Got an exception! " + e.getMessage());
			e.printStackTrace();
		}
	}

	/*
	 * Insertar. Recibe el nombre del director que hay que insertar
	 */

	public void insertarUnDirector(String nombreDirector) {
		try {
			String query = "INSERT INTO directores (nombre) VALUES(?)";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, nombreDirector);
			int filas = preparedStatement.executeUpdate();
			System.out.println("Filas afectadas: " + filas);		
			preparedStatement.close();
		} catch (Exception e) {
			System.err.println("Got an exception! " + e.getMessage());
			e.printStackTrace();
		}
	}

	/*
	 * Modificar. Recibe idDirector y nombre nuevo
	 */

	public void modificarUnDirector(int idDirector, String nombreNuevo) {

		try {
			String query = "UPDATE directores SET nombre= ? WHERE id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, nombreNuevo);
			preparedStatement.setInt(2, idDirector);
			int filas = preparedStatement.executeUpdate();
			System.out.println("Filas afectadas: " + filas);
			preparedStatement.close();
		} catch (Exception e) {
			System.err.println("Got an exception! " + e.getMessage());
			e.printStackTrace();
		}

	}

	/*
	 * Borrar. Recibe el id del director que hay que borrar
	 */

	public void borrarUnDirector(int idDirector) {

		try {
			String query = "DELETE FROM directores WHERE id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, idDirector);
			int filas = preparedStatement.executeUpdate();
			System.out.println("Filas afectadas: " + filas);
			preparedStatement.close();
		} catch (Exception e) {
			System.err.println("Got an exception! " + e.getMessage());
			e.printStackTrace();
		}

	}

	/*
	 * Consulta de películas. 
	 * Se valorará si se muestra el nombre del director en lugar del ID
	 * Varias opciones para hacerlo
	 * Se opta por el JOIN que es el más fácil pero se podría hacer con subconsultas 
	 * 		o incluso lanzando una nueva consulta: por cada película preguntamos por el nombre del director buscando por el id...
	 */

	public void consultaPeliculas() {
		try {
			String query = "SELECT peliculas.id AS idPelicula, peliculas.titulo AS titulo, directores.nombre AS nombre FROM peliculas JOIN directores ON peliculas.director=directores.id";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			int id;
			String name;
			String title;
			while (rs.next()) {
				id = rs.getInt("idPelicula");
				title = rs.getString("titulo");
				name = rs.getString("nombre");
				// print the results.
				System.out.println("--------------------------------------------------------------");
				System.out.printf("\tID: %d - Titulo: %s - Director: %s\n", id, title, name);
				System.out.println("--------------------------------------------------------------");
			}
			preparedStatement.close();
		} catch (Exception e) {
			System.err.println("Got an exception! " + e.getMessage());
			e.printStackTrace();
		}

	}
	
	/*
	 * Extra. No se pedía. Simplemente es para facilitar las pruebas y comprobar una cosa que se ha investigado 
	 */
	private void ultimoIdGenerado(int filasAfectadas, PreparedStatement pstmt) {
	    long idGenerado;
	    try {
			if (filasAfectadas > 0) {
		        // Recuperar las claves generadas (en este caso, el ID autoincremental)
		        try (ResultSet rs = pstmt.getGeneratedKeys()) {
		            if (rs.next()) {
		                // Obtener el valor del primer (y único) ID generado
		                idGenerado = rs.getLong(1);
		                System.out.println("ID de la fila insertada: " + idGenerado);		                
		            }
		        }
		    }
	    }catch (Exception e) { // Muestro el error completo para ver que pasa y poder solucionarlo
			System.err.println("Got an exception! " + e.getMessage());
			e.printStackTrace();
		}
	    
	}	

}
