package deftstack_examples;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.xml.sax.SAXException;

public class Testing_3_SAX {
	
	public void recorrer(File inputFile) {

		System.out.println("RECORREMOS EMPLEADOS USANDO SAX (necesita librerias)");

		try {

			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(inputFile);
			System.out.println("Root element :" + document.getRootElement().getName());
			Element classElement = document.getRootElement();

			List<Element> studentList = classElement.getChildren();
			System.out.println("----------------------------");

			for (int temp = 0; temp < studentList.size(); temp++) {
				Element student = studentList.get(temp);
				System.out.println("\nCurrent Element :" + student.getName());
				System.out.println("First Name : " + student.getChild("firstname").getText());
				System.out.println("Last Name : " + student.getChild("lastname").getText());
				System.out.println("Salary : " + student.getChild("salary").getText());
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public void addOne(File outputFile)  {
		
		try {
			
			SAXBuilder saxBuilder = new SAXBuilder();
			Document document = saxBuilder.build(outputFile);
			System.out.println("Root element :" + document.getRootElement().getName());
			Element raiz = document.getRootElement();
			
			/*
			 * Creamos nodo empleado y sus hijos (apellido, nombre y salario)
			 * Como son elementos simples añadimos nodo de texto con valor
			 */
			
			Element empleado = new Element("employee");
			raiz.addContent(empleado);
			Element nombre = new Element("firstname");
			nombre.setText("Prueba Nombre");
			Element apellido = new Element("lastname");
			apellido.setText("Prueba Apellido");
			Element salario = new Element("salary");
			salario.setText("50000");
			
			/*
			 * Añadimos hijos al elemento empleado
			 */
			
			empleado.addContent(nombre);
			empleado.addContent(apellido);
			empleado.addContent(salario);
			
			/*
			 * Creamos y añadimos atributo
			 */
			
			Attribute attr = new Attribute("id","4001");
			empleado.setAttribute(attr);
			
			/*
			 * Volvemos a escribir el fichero
			 */
			
			
			Format f = Format.getPrettyFormat (); // Formato de visualización perfecto de xml
			// Format f = Format.getCompactFormat (); // El formato de visualización compacto de xml
			f.setEncoding("gbk");
			f.setOmitDeclaration(false);

			// Genera un archivo xml mediante transmisión; escribe un árbol DOM desde la memoria al disco duro.
			XMLOutputter xmlOut = new XMLOutputter(f);
			xmlOut.output(raiz, new FileOutputStream(outputFile));		
			
			System.out.println("Añadido un nuevo elemento");
			
		}catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
	}

		
	
}