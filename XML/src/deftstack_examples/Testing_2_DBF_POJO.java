package deftstack_examples;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.Employee;

public class Testing_2_DBF_POJO {

	public void recorrer(File inputFile) {

		try {
			System.out.println("Recorremos Empleados usando DocumentBuilderFactory y clase auxiliar");

			List<Employee> employees = new ArrayList<>();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(inputFile);

			NodeList nodeList = document.getDocumentElement().getChildNodes();

			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element) node;
					String firstname = elem.getElementsByTagName("firstname").item(0).getChildNodes().item(0)
							.getNodeValue();
					String lastname = elem.getElementsByTagName("lastname").item(0).getChildNodes().item(0)
							.getNodeValue();
					Double salary = Double.parseDouble(
							elem.getElementsByTagName("salary").item(0).getChildNodes().item(0).getNodeValue());
					employees.add(new Employee(firstname, lastname, salary));
				}
			}
			for (Employee empl : employees) {
				System.out.println(empl.toString());
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	} // Fin método

}
