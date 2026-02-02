package accesoDatos;

import java.sql.*;
import java.util.*;

import auxiliares.LeeProperties;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoJDBC implements I_Acceso_Datos {

	private String driver, urlbd, user, password; // Datos de la conexion
	private Connection conn1;

	public AccesoJDBC() {
		System.out.println("ACCESO A DATOS - Acceso JDBC");
		
		try {
			HashMap<String,String> datosConexion;
			
			LeeProperties properties = new LeeProperties("Ficheros/config/accesoJDBC.properties");
			datosConexion = properties.getHash();		
			
			driver = datosConexion.get("driver");
			urlbd = datosConexion.get("urlbd");
			user = datosConexion.get("user");
			password = datosConexion.get("password");
			conn1 = null;
			
			Class.forName(driver);
			conn1 = DriverManager.getConnection(urlbd, user, password);
			if (conn1 != null) {
				System.out.println("Conectado a la base de datos");
			} 

		} catch (ClassNotFoundException e1) {
			System.out.println("ERROR: No Conectado a la base de datos. No se ha encontrado el driver de conexion");
			//e1.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		} catch (SQLException e) {
			System.out.println("ERROR: No se ha podido conectar con la base de datos");
			System.out.println(e.getMessage());
			//e.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		}
	}

	public int cerrarConexion() {
		try {
			conn1.close();
			System.out.println("Cerrada conexion");
			return 0;
		} catch (Exception e) {
			System.out.println("ERROR: No se ha cerrado corretamente");
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> aux = new HashMap<>();
		String query = "SELECT * FROM depositos";
        try (Statement st = conn1.createStatement();
        ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
				int valor = rs.getInt("valor");
                String name = rs.getString("nombre");
                int cantidad = rs.getInt("cantidad");
                Deposito d = new Deposito(name, valor, cantidad);
                aux.put(valor, d);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
		return aux;

	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> aux = new HashMap<>();
		String query = "SELECT * FROM dispensadores";
        try (Statement st = conn1.createStatement();
        ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                String name = rs.getString("nombre");
				String clave = rs.getString("clave");
				int precio = rs.getInt("precio");
                int cantidad = rs.getInt("cantidad");
                Dispensador d = new Dispensador(clave, name, precio, cantidad);
                aux.put(clave, d);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
		return aux;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOK = false;
		try {
			String query = "UPDATE depositos SET cantidad = ? WHERE valor = ?";
			PreparedStatement ps = conn1.prepareStatement(query);
			for (Deposito d : depositos.values()) {
				ps.setInt(1, d.getCantidad());
				ps.setInt(2, d.getValor());
				int rows = ps.executeUpdate();
				System.out.println("DEPOSITO " + d.getValor() + ":" + rows);
				if (rows > 0) {
					todoOK = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return todoOK;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOK = false;
		try {
			String query = "UPDATE dispensadores SET precio = ?, cantidad = ? WHERE clave = ?";
        	PreparedStatement ps = conn1.prepareStatement(query);
			for (Dispensador d : dispensadores.values()) {
				ps.setInt(1, d.getPrecio());
				ps.setInt(2, d.getCantidad());
				ps.setString(3, d.getClave());
				int rows = ps.executeUpdate();
				System.out.println("DISPENSADOR " + d.getClave() + ":" + rows);
				if (rows > 0) {
					todoOK = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return todoOK;
	}

} // Fin de la clase