package modelo;

import java.util.Set;

public class Teacher  {
	
	private int id;
	private String name;
	private int workingYears;
	private Set<Asignatura> conjuntoAsignaturas;	
	
	public Teacher(){
		
	}
	
	public Teacher(int nuevoId, String nombre, int experiencia){
		this.id = nuevoId;
		this.name = nombre;
		this.workingYears = experiencia;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getWorkingYears() {
		return workingYears;
	}

	public void setWorkingYears(int workingYears) {
		this.workingYears = workingYears;
	}

	public String toString() {
		String aux = "";
		
		aux = "El profesor con id " + this.id + " se llama " + this.name + " y lleva trabajando " + this.workingYears + " largos cursos";
		
		return aux;
	}

	public Set<Asignatura> getConjuntoAsignaturas() {
		return conjuntoAsignaturas;
	}

	public void setConjuntoAsignaturas(Set<Asignatura> conjuntoAsignaturas) {
		this.conjuntoAsignaturas = conjuntoAsignaturas;
	}
	
	
	
}
