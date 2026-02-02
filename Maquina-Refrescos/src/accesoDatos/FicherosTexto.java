package accesoDatos;

import java.io.*;
import java.util.HashMap;

import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class FicherosTexto implements I_Acceso_Datos {
	
	File fDis; // FicheroDispensadores
	File fDep; // FicheroDepositos
	
	public FicherosTexto(){
		System.out.println("ACCESO A DATOS - FICHEROS DE TEXTO");
		
	}
	
	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {
		HashMap<Integer, Deposito> depositosCreados = new HashMap<>();
		try (FileReader fr = new FileReader(this.fDis);
		BufferedReader br = new BufferedReader(fr)) {
			String linea;
			while ((linea = br.readLine()) != null) {
                String[] s = linea.split(";"); 
                int valor = Integer.parseInt(s[1]);
				int cantidad = Integer.parseInt(s[2]);
                Deposito d = new Deposito(s[0], valor, cantidad);
                depositosCreados.put(valor, d);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer/parsear los libros del fichero: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al leer los libros del fichero: " + e.getMessage());
        }
		return depositosCreados;
	}
	
	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<>();
		try (FileReader fr = new FileReader(this.fDis);
		BufferedReader br = new BufferedReader(fr)) {
			String linea;
			while ((linea = br.readLine()) != null) {
                String[] s = linea.split(";"); 
                int precio = Integer.parseInt(s[3]);
				int cantidad = Integer.parseInt(s[4]);
                Dispensador d = new Dispensador(s[0], s[1], precio, cantidad);
                dispensadoresCreados.put(s[0], d);
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al leer/parsear los libros del fichero: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al leer los libros del fichero: " + e.getMessage());
        }
		return dispensadoresCreados;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
		boolean todoOk = false;
			try (FileWriter fw = new FileWriter(this.fDis);
			BufferedWriter bw = new BufferedWriter(fw)) {
				for (Deposito d : depositos.values()) {
				bw.write(d.getNombreMoneda() + ";" + d.getValor() + ";" + d.getCantidad());
				bw.newLine();
				}
				todoOk = true;
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		return todoOk;
	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
		boolean todoOk = false;
			try (FileWriter fw = new FileWriter(this.fDis);
			BufferedWriter bw = new BufferedWriter(fw)) {
				for (Dispensador d : dispensadores.values()) {
				bw.write(d.getClave() + ";" + d.getNombreProducto() + ";" + d.getPrecio() + ";" + d.getCantidad());
				bw.newLine();
				}
				todoOk = true;
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		return todoOk;
	}


} // Fin de la clase