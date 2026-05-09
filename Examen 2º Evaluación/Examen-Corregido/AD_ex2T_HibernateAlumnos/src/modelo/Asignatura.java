package modelo;

public class Asignatura {

	private int id;
	private String nombre;
	private String ciclo;
	private Teacher profesor;

	public Asignatura() {
	}
	
    public Asignatura(int id) {
        this.id = id;
    }
	
	public Asignatura(int id, String nombre, String ciclo, Teacher profe) {
		this.id = id;
		this.nombre = nombre;
		this.ciclo = ciclo;
		this.profesor = profe;
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

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public Teacher getProfesor() {
		return profesor;
	}

	public void setProfesor(Teacher profesor) {
		this.profesor = profesor;
	}

	public String toString() {
		String aux = "";
		
		aux = "La asignatura con id " + this.id + " tiene de nombre " + this.nombre + ", es del ciclo " + this.ciclo + " y la imparte " + this.profesor.getName();
		
		return aux;
	}

	public String formatoFichero() {
		String aux = "";
		
		aux = this.id + ";" + this.nombre + ";" + this.ciclo + ";" + this.profesor.getName();
		
		return aux;
	}


}
