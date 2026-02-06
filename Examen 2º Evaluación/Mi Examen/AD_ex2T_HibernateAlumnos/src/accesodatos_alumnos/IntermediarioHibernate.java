package accesodatos_alumnos;

import java.util.Iterator;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.query.Query;

import auxiliar.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import modelo.Asignatura;
import modelo.Teacher;

/*
 * Esta clase implementa el acceso a hibernate para las operaciones básicas (añadir, consultar, ...)
 * La base de datos debe estar ejecutándose para que funcione.
 * Es una mezcla de acceso y vista (se muestran resultados en esta misma clase)
 * MUY IMPORTANTE: Rellenad únicamente los  huecos indicados
 */

public class IntermediarioHibernate {

	HibernateUtil factoriaSesiones;
	Session session; // Sesion abierta en el constructor. Es la que se debe de usar. No cerrar por
						// parte del alumno!!!

	public IntermediarioHibernate() { // Constructor. NO TOCAR
		factoriaSesiones = new HibernateUtil();
		session = factoriaSesiones.getSession();
	}

	public void cerrarSesion() { // NO TOCAR. SE LLAMA DESDE PRINCIPAL
		session.close();
	}

//	System.out.println("1 - Muestra todos los profesores (solo los datos del profesor)");

	public void e1_mostrarProfesores() {

		System.out.println("Mostrando profesores - Hibernate");
		TypedQuery<Teacher> q = session.createQuery("from profesor");
		List<Teacher> results = q.getResultList();
		Iterator profesIterator = results.iterator();
		while (profesIterator.hasNext()) {
			Teacher profe = (Teacher) profesIterator.next();
			System.out.println("Id: " + profe.getId() + " - Nombre: " + profe.getName() + " - Experiencia: " + profe.getWorkingYears());
		}
		System.out.println("Mostrando profesores");

	}

//	System.out.println("2 - Inserta un profesor");

	public void e2_insertarProfesor(String nombre, int experiencia) {

		System.out.println("Insertando profesor - Hibernate");
		int id = (int) Math.random()*1000+67;
		Teacher profe = new Teacher();
		profe.setId(id);
		profe.setName(nombre);
		profe.setWorkingYears(experiencia);
		session.beginTransaction();
		session.save(profe);
		session.getTransaction().commit();
		System.out.println("Insertado profesor");

	}

//	System.out.println("3 - Modificar experienca de un profesor");

	public void e3_modificarProfesor(int id, int experiencia) {

		System.out.println("Modificando experiencia profesor - Hibernate");
		TypedQuery<Teacher> q = session.createQuery("from profesor where ID = " + id);
		List<Teacher> results = q.getResultList();
		Iterator profesIterator = results.iterator();
		Teacher profe = (Teacher) profesIterator.next();
		profe.setWorkingYears(experiencia);
		session.beginTransaction();
		session.save(profe);
		session.getTransaction().commit();
		System.out.println("Modificada experiencia profesor");

	}

//	System.out.println("4 - Muestra todas las asignaturas (debe aparecer el nombre del profesor)");
//  Para mostrar los datos se puede usar el toString de la clase asignatura

	public void e4_mostrarAsignaturas() {

		System.out.println("Salida de consulta Asignaturas (y profesor) - Hibernate");
		TypedQuery<Teacher> q = session.createQuery("from subject");
		List<Teacher> results = q.getResultList();
		Iterator asignaturasIterator = results.iterator();
		while (asignaturasIterator.hasNext()) {
			Asignatura asignatura = (Asignatura) asignaturasIterator.next();
			System.out.println("Id: " + asignatura.getId() + " - Nombre: " + asignatura.getNombre() + " - Ciclo: " + asignatura.getCiclo() + " - Profesor: " + asignatura.getProfesor().getName());
		}
		System.out.println("Fin Salida de consulta Asignaturas (y profesor) - Hibernate");

	}

//	System.out.println("5 - Muestra todas los profesores (debe aparecer el nombre de las asignaturas que imparte)");

	public void e5_mostrarProfesoresAvanzado() {

		System.out.println("Salida de consulta Profesores (y asignaturas) - Hibernate");
		// Rellenar por el alumno

		// Fin Rellenar por el alumno
		System.out.println("Fin Salida de consulta Profesores (y asignaturas) - Hibernate");

	}
	
}
