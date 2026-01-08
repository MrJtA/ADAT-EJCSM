package auxliar;

import java.util.Scanner;

public class Auxiliar {

	public static int leerEntero(String textoPeticion) {
		
		int var = 0;
		Scanner sc=new Scanner(System.in);
		
		boolean salir = false;
		
		while(!salir) {
			try {
				System.out.println(textoPeticion);
				var = Integer.parseInt(sc.nextLine());
				salir = true;
			}catch(Exception e) {
				System.out.println("No es un número entero");
			}

		}
		
		return var;	
		
	}
	
}
