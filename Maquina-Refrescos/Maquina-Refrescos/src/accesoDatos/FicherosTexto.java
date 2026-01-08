package accesoDatos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

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
		try (FileReader fr = new FileReader(fDis);
		BufferedReader br = new BufferedReader(fr)) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] s = linea.split(";");
				for (int i = 0; i<s.length; i++) {
					Deposito d = new Deposito(s[0], Integer.parseInt(s[1]), Integer.parseInt(s[2]));
					depositosCreados.put(Integer.parseInt(s[1]), d);
				}
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {
		
		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<>();
		try (FileReader fr = new FileReader(fDep);
		BufferedReader br = new BufferedReader(fr)) {
			String linea;
			while ((linea = br.readLine()) != null) {
				String[] s = linea.split(";");
				for (int i = 0; i<s.length; i++) {
					Dispensador d = new Dispensador(s[0], s[1], Integer.parseInt(s[2]), Integer.parseInt(s[3]));
					dispensadoresCreados.put(s[1], d);
				}
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		return dispensadoresCreados;

	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {

		try (FileWriter fw = new FileWriter(fDis);
		BufferedWriter bw = new BufferedWriter(fw)) {
			for (Entry<Integer, Deposito> entry : depositos.entrySet()) {
			int clave = entry.getKey();
			Deposito valor = entry.getValue();
			bw.write(valor.getNombreMoneda() + ";" + clave + ";" + valor.getCantidad());
			bw.newLine();
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		boolean todoOK = true;
		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		try (FileWriter fw = new FileWriter(fDep);
		BufferedWriter bw = new BufferedWriter(fw)) {
			for (Entry<String, Dispensador> entry : dispensadores.entrySet()) {
			String clave = entry.getKey();
			Dispensador valor = entry.getValue();
			bw.write(valor.getClave() + ";" + clave + ";" + valor.getPrecio() + valor.getCantidad());
			bw.newLine();
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
		boolean todoOK = true;
		return todoOK;

	}

} // Fin de la clase