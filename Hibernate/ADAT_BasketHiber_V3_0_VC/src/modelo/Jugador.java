package modelo;

import java.util.HashSet;
import java.util.Set;

public class Jugador implements java.io.Serializable {

	private int idJugador;
	private String nombre;
	private int numero;
	private Equipo equipo;	// N a 1
	
	
	private Set<JugadorPosicion> posiciones = new HashSet();  // 1 a N

	public Jugador() {
		
	}

	public int getIdJugador() {
		return idJugador;
	}

	public void setIdJugador(int idJugador) {
		this.idJugador = idJugador;
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

	public Equipo getEquipo() {
		return equipo;
	}

	public void setEquipo(Equipo equipo) {
		this.equipo = equipo;
	}

	public Set<JugadorPosicion> getPosiciones() {
		return posiciones;
	}

	public void setPosiciones(Set<JugadorPosicion> posiciones) {
		this.posiciones = posiciones;
	}


}
