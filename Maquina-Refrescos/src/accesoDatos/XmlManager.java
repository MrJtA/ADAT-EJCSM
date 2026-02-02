package accesoDatos;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

/*
 * Todas los accesos a datos implementan la interfaz de Datos
 */

public class XmlManager implements I_Acceso_Datos {

	File fDis; // FicheroDispensadores
	File fDep; // FicheroDepositos

	public XmlManager() {
		System.out.println("ACCESO A DATOS - XML");

		// Cargamos los ficheros
		fDep = new File("Ficheros/datos/depositos.xml");
		fDis = new File("Ficheros/datos/dispensadores.xml");

	}

	@Override
	public HashMap<Integer, Deposito> obtenerDepositos() {

		HashMap<Integer, Deposito> depositosCreados = new HashMap<>();
		
		try {
			// Cargamos el arbol XML a partir del contenido del fichero
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(fDep);
			Element raizDepositos = document.getRootElement();
			
			
			// Rellenar a partir de aqu�
			List<Element> listaDepositos = raizDepositos.getChildren();
            for (int i = 0; i<listaDepositos.size(); i++) {
                Element elemento = listaDepositos.get(i);
                String nombre = elemento.getChild("nombre").getText();
                String valor = elemento.getChild("valor").getText();
                String cantidad = elemento.getChild("cantidad").getText();
                int valorInt = Integer.parseInt(valor);
                int cantidadInt = Integer.parseInt(cantidad);
                Deposito d = new Deposito(nombre, valorInt, cantidadInt);
                depositosCreados.put(valorInt, d);
            }
			
			
			
		} catch (Exception e) {
			System.out.println("ERROR: No se ha podido conectar con el XML de depositos");
			System.out.println(e.getMessage());
			//e.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		}

		return depositosCreados;
	}

	@Override
	public HashMap<String, Dispensador> obtenerDispensadores() {

		HashMap<String, Dispensador> dispensadoresCreados = new HashMap<>();
		
		try {
			// Cargamos el arbol XML a partir del contenido del fichero
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(fDis);
			Element raizDispensadores = document.getRootElement();
			
			// Rellenar a partir de aqu�
			List<Element> listaDispensadores = raizDispensadores.getChildren();
            for (int i = 0; i<listaDispensadores.size(); i++) {
                Element elemento = listaDispensadores.get(i);
                String clave = elemento.getChild("clave").getText();
                String nombre = elemento.getChild("nombreLargo").getText();
                String precio = elemento.getChild("precio").getText();
                String cantidad = elemento.getChild("cantidad").getText();
                int precioInt = Integer.parseInt(precio);
                int cantidadInt = Integer.parseInt(cantidad);
                Dispensador d = new Dispensador(clave, nombre, precioInt, cantidadInt);
                dispensadoresCreados.put(clave, d);
			}
			
		} catch (Exception e) {
			System.out.println("ERROR: No se ha podido conectar con el XML de dispensadores");
			System.out.println(e.getMessage());
			//e.printStackTrace();
			System.out.println("No se ha podido inicializar la maquina\n Finaliza la ejecucion");
			System.exit(1);
		}

		return dispensadoresCreados;
	}

	@Override
	public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {

		boolean todoOK = false;
		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(fDep);
			Element raizDepositos = document.getRootElement();
			raizDepositos.removeContent();
			for (Deposito d : depositos.values()) {
				Element depositoElemento = new Element("deposito");
				depositoElemento.addContent(new Element("nombre").setText(d.getNombreMoneda()));
				depositoElemento.addContent(new Element("valor").setText(String.valueOf(d.getValor())));
				depositoElemento.addContent(new Element("cantidad").setText(String.valueOf(d.getCantidad())));
				raizDepositos.addContent(depositoElemento);
			}
			XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
			xmlOutput.output(document, new FileOutputStream(fDep));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return todoOK;

	}

	@Override
	public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {

		boolean todoOK = false;
		try {
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(fDis);
			Element raizDispensadores = document.getRootElement();
			raizDispensadores.removeContent();
			for (Dispensador d : dispensadores.values()) {
				Element dispensadorElemento = new Element("dispensador");
				dispensadorElemento.addContent(new Element("clave").setText(d.getClave()));
				dispensadorElemento.addContent(new Element("nombreLargo").setText(d.getNombreProducto()));
				dispensadorElemento.addContent(new Element("precio").setText(String.valueOf(d.getPrecio())));
				dispensadorElemento.addContent(new Element("cantidad").setText(String.valueOf(d.getCantidad())));
				raizDispensadores.addContent(dispensadorElemento);
			}
			XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
			xmlOutput.output(document, new FileOutputStream(fDis));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return todoOK;
	}

} // Fin de la clase