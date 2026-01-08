package my_examples;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Principal_Recursivo {

	public static void main(String[] args) throws ParserConfigurationException, SAXException {
		try {
			
			File file = new File("Ficheros/Empleados.xml");
			Recursivo_Obrero testing = new Recursivo_Obrero();

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			
			Element elementoRaiz = document.getDocumentElement();
			
			String texto = elementoRaiz.getNodeName().toUpperCase();
			
			System.out.println("**********************************************************************");
			System.out.println("	" +texto);
			System.out.println("**********************************************************************");
			System.out.println("");
			
			testing.recorreRecursivo(elementoRaiz);

		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
