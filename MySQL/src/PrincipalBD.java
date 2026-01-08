import java.sql.Date;
import java.time.LocalDate;

public class PrincipalBD {

	public static void main(String[] args) {

		AccesoBD miAcceso = new AccesoBD();
		//miAcceso.pruebaRead();
		LocalDate fecha = LocalDate.of(2025,10,01);
		Date d = Date.valueOf(fecha);
		Jugador jugador = new Jugador("NUEVO","PROFE","POCO A POCO", 25, d);
		miAcceso.pruebaInsert(jugador);
		jugador.setCaracteristica("NUEVA CARACTERISTICA");
		jugador.setEdad(30);
		miAcceso.pruebaUpdate(jugador);
		

	}

}