package modelo;

public class JugadorPosicion {
	
	private int id;
	private Jugador j;
	private Posicion p;
	private int numVeces;
	
	
	public JugadorPosicion () {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public Jugador getJ() {
		return j;
	}


	public void setJ(Jugador j) {
		this.j = j;
	}


	public Posicion getP() {
		return p;
	}


	public void setP(Posicion p) {
		this.p = p;
	}


	public int getNumVeces() {
		return numVeces;
	}


	public void setNumVeces(int numVeces) {
		this.numVeces = numVeces;
	}
	
	
	public String toString() {
		
		String s = "El jugador " + j.getNombre() + " ha jugado de " + p.getPosicion() + numVeces + " veces";
		return s;
		
	}

}
