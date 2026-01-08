import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccesoBD {


	// Conexion con la BD. La crearemos en el constructor con el resto de datos
	// (que deber�an estar en un fichero config)
	Connection conn;

	public AccesoBD() {

		
		// Librer�a de MySQL
		String driver = "com.mysql.cj.jdbc.Driver";

		// Nombre de la base de datos
		String database = "bdpruebas";

		// Host
		String hostname = "localhost";

		// Puerto
		String port = "3306";

		// Ruta de nuestra base de datos (desactivamos el uso de SSL con
		// "?useSSL=false")
		String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database + "?useSSL=false";

		// Nombre de usuario
		String username = "root";

		// Clave de usuario
		String password = "root";
		
		try {
			Class.forName(driver);
			System.out.println(url);
			conn = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	public void pruebaRead() {

		try {

			// our SQL SELECT query.
			// if you only need a few columns, specify them by name instead of using "*"
			String query = "SELECT * FROM elementos";

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			ResultSet rs = st.executeQuery(query);

			// iterate through the java resultset
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("nombre");
				String description = rs.getString("descripcion");
				Date fechaIncorporacion = rs.getDate("fecha");

				// print the results
				System.out.println("--------------------------------------------------------------");
				System.out.printf("\tNombre: %s \n\tDescripcion: %s \n\tFecha de incorporacion: %s\n", name, description, fechaIncorporacion.toString());
				System.out.println("--------------------------------------------------------------");
			}
			
			st.close();
			
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}

	public void pruebaInsert(Jugador j){

		try{

			String query = "INSERT INTO elementos (nombre,descripcion,caracteristica,edad,fecha) VALUES (";
			query+= "'" + j.getNombre() + "',";
			query+= "'" + j.getDescripcion() + "',";
			query+= "'" + j.getCaracteristica() + "',";
			query+= j.getEdad() + ",";
			query+= "'" + j.getFechaIncorporacionLiga() + "')";

			System.out.println(query);

						// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			int isOk = st.executeUpdate(query);

			System.out.println(isOk);

		}catch(Exception e){
			e.printStackTrace();
		}
		

		


	}

	public void pruebaUpdate(Jugador j){

		try{

			String query = "UPDATE elementos SET ";
			query+= "nombre = '" + j.getNombre() + "',";
			query+= "descripcion = '" + j.getDescripcion() + "',";
			query+= "caracteristica = '" + j.getCaracteristica() + "',";
			query+= "edad = " + j.getEdad() + ",";
			query+= "fecha = '" + j.getFechaIncorporacionLiga() + "'";

			System.out.println(query);

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			int isOk = st.executeUpdate(query);

			System.out.println(isOk);

		}catch(Exception e){
			e.printStackTrace();
		}
		

		


	}

	public void pruebaDelete(Jugador j){

		try{

			String query = "DELETE FROM elementos WHERE id = " + j.getId(); 

			System.out.println(query);

			// create the java statement
			Statement st = conn.createStatement();

			// execute the query, and get a java resultset
			int isOk = st.executeUpdate(query);

			System.out.println(isOk);

		}catch(Exception e){
			e.printStackTrace();
		}
		

		


	}

}
