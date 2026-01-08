package my_examples;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.Employee;

public class Recursivo_Obrero {

	int nivel;

	public Recursivo_Obrero() {
		nivel = 0;
	}

	public void recorreRecursivo(Element elementoActual) {

		String texto = "";

		// System.out.println("NIVEL " + nivel);
		nivel++;
		texto += elementoActual.getNodeName().toUpperCase();

		NodeList nodeList = elementoActual.getChildNodes();

		int tam1 = nodeList.getLength();

		// System.out.println("Tamaño: " + tam1);

		if (tam1 == 1) { // Solo tiene un hijo y es de texto??
			String valor = elementoActual.getFirstChild().getNodeValue();
			texto += ": " + valor;
			System.out.println(texto);
		} else {
			// System.out.println(texto);
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				// System.out.println("Tipo nodo " + node.getNodeType());
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) node;
					recorreRecursivo(elem);
				}
			}

		}

		nivel--;

		// Añadimos atributos (pero quitamos los del nivel 0 que sale el esquema y no
		// interesa
		if (nivel != 0) {
			NamedNodeMap atributos = elementoActual.getAttributes();

			for (int i = 0; i < atributos.getLength(); i++) {
				String textoAttr = "";
				Node atr = atributos.item(i);
				textoAttr += atr.getNodeName();
				textoAttr += ": " + atr.getNodeValue();
				
				textoAttr = textoAttr.replace("_", " ");
				textoAttr = textoAttr.toUpperCase();

				System.out.println(textoAttr);

			}
			
			if (nivel == 1) {	// Añadimos separador para primer nivel
				System.out.println("----------------------------------------------------------------------");
			}
			
		}

	}

}