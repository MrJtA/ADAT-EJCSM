import java.util.Scanner;

public class MainVuelos {

    final static Scanner sc=new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        
        boolean seguir=true;
        //Consultas c=new Consultas();
        Vuelos q=new Vuelos();

        while (seguir) {
            
            Menu();
            int opcion=PedirNumero();

            switch (opcion) {
                case 1:
                    q.Query1();
                    break;
                case 2:
                    q.Query2();
                    break;
                case 3:
                    q.Query3();
                    break;
                case 4:
                    q.Query4();
                    break;
                case 5:
                    q.Query5();
                    break;
                case 6:
                    q.Query6();
                    break;
                case 7:
                    q.QueryPrueba();
                    break;
                case 8:
                    System.out.println("\nHas salido del programa");
                default:
                    System.out.println("\nOpción no valida\n");
                    break;
            }
        }
    }

    public static void Menu() {
        System.out.println("----Menu----");
        System.out.println("1. Consulta 1");
        System.out.println("2. Consulta 2");
        System.out.println("3. Consulta 3");
        System.out.println("4. Consulta 4");
        System.out.println("5. Consulta 5");
        System.out.println("6. Consulta 6");
        System.out.println("7. Consulta de prueba");
        System.out.println("8. Salir");
    }

    public static int PedirNumero() {

        while (true) {
            try {
                int n=sc.nextInt();
                return n;
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("Tienes que introducir un número");
            }
        }
    }

}
