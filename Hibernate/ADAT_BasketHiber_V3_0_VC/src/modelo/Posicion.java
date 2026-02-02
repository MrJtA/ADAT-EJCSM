package modelo;

import java.util.HashSet;
import java.util.Set;

public class Posicion {
	
	int idPosicion;
	String posicion;
	String descripcion;
	private Set<JugadorPosicion> jugadores = new HashSet();	
	
	public Posicion () {
		
	}
	
	public int getIdPosicion() {
		return idPosicion;
	}
	
	public void setIdPosicion(int idPosicion) {
		this.idPosicion = idPosicion;
	}
	
	public String getPosicion() {
		return posicion;
	}
	
	public void setPosicion(String posicion) {
		this.posicion = posicion;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Set<JugadorPosicion> getJugadores() {
		return jugadores;
	}

	public void setJugadores(Set<JugadorPosicion> jugadores) {
		this.jugadores = jugadores;
	}
	
}
