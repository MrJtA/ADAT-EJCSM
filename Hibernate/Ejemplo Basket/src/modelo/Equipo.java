package modelo;

import java.util.Set;

public class Equipo implements java.io.Serializable {

	private int id;
	private String nombre;
	private Set<Jugador> conjuntoJugadores;	

	public Equipo(){
		
	}

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

	public Set<Jugador> getConjuntoJugadores() {
		return conjuntoJugadores;
	}

	public void setConjuntoJugadores(Set<Jugador> conjuntoJugadores) {
		this.conjuntoJugadores = conjuntoJugadores;
	}
	
}
