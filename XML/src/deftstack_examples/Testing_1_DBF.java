package deftstack_examples;

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

public class Testing_1_DBF {

	public void recorrer(File inputFile) {

		System.out.println("Recorremos Empleados usando DocumentBuilderFactory y clase auxiliar");

		try {
			File file = new File("Ficheros/Empleados.xml");

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);

			document.getDocumentElement().normalize();

			System.out.println("Root Element :" + document.getDocumentElement().getNodeName());

			NodeList nList = document.getElementsByTagName("employee");
			System.out.println("----------------------------");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("Employee id : " + eElement.getAttribute("id"));
					System.out.println(
							"First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent() + " (obtenido con forma 1)" ); // Forma
																													// 1

					System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0)
							.getChildNodes().item(0).getNodeValue() + " (obtenido con forma 2)"); // Forma 2

					System.out.println("First Name : "
							+ eElement.getElementsByTagName("firstname").item(0).getFirstChild().getNodeValue() + " (obtenido con forma 3)"); // Forma
																													// 3

					System.out.println(
							"Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
					System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
}