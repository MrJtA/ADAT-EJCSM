package deftstack_examples;

import java.io.File;

import auxliar.Auxiliar;

public class Inicio {
		
	public static void main(String[] arg) {
	
		boolean salir = false;
		int opcion;
		File inputFile = new File("Ficheros/Empleados.xml");
		
		while(!salir) {
			opcion = opcionMenu();
			switch(opcion) {
				case 0:
					salir = true;
					System.out.println("HASTA LA PROXIMA!!!");
					break;
				case 1:
					Testing_1_DBF  prueba = new Testing_1_DBF();
					prueba.recorrer(inputFile);
					break;
				case 2:
					Testing_2_DBF_POJO  prueba2 = new Testing_2_DBF_POJO();
					prueba2.recorrer(inputFile);
					break;
				case 3:
					Testing_3_SAX  prueba3 = new Testing_3_SAX();
					prueba3.recorrer(inputFile);
					break;
				case 4:
					Testing_4_Xpath prueba4 = new Testing_4_Xpath();
					prueba4.recorrer(inputFile);
					break;
				case 5:
					Testing_3_SAX  prueba5 = new Testing_3_SAX();
					prueba5.addOne(inputFile);
					break;
				default:
					System.out.println("OPCIÓN NO VÁLIDA O NO IMPLEMENTADA");
					break;
			}
		}
		
	}
	
	public static int opcionMenu() {
		
		int opcion = 0;
		System.out.println();
		System.out.println("MENU DE OPCIONES");
		System.out.println("0 - Salir");
		System.out.println("1 - Leer con DBF");
		System.out.println("2 - Leer con DBF y POJO");
		System.out.println("3 - Leer con SAX");
		System.out.println("4 - Leer con XPATH");
		System.out.println("5 - Prueba inserción");
		
		
		Auxiliar aux = new Auxiliar();
		
		opcion = aux.leerEntero("Selecciona una opción");
		
		return opcion;	
		
	}

}
