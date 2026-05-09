package modelo;

public class Jugador {
	
	int id;
	String nombre;
	int numero;
	int equipo;
		
	/* Constructores */

	public Jugador() {

	}	
	
	public Jugador(int id, String nombre, int numero, int equipo) {
		this.id = id;
		this.nombre = nombre;
		this.numero = numero;
		this.equipo = equipo;
	}
	
	public Jugador(String nombre, int numero, int equipo) {
		this.nombre = nombre;
		this.numero = numero;
		this.equipo = equipo;
	}

	/* Getters & Setters*/
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getEquipo() {
		return equipo;
	}

	public void setEquipo(int equipo) {
		this.equipo = equipo;
	}
	
	public String toString(){
		String aux ="";
		
		aux += "------------------------------------------";
		aux += "\n	ID: " + this.id;
		aux += "\n	NOMBRE: " + this.nombre;
		aux += "\n	Nº: " + this.numero;
		aux += "\n	EQUIPO: " + this.equipo;
		aux += "\n------------------------------------------";
		
		return aux;
	}
	
}
