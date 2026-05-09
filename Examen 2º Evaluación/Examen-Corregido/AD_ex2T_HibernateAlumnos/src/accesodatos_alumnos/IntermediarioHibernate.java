package accesodatos_alumnos;

import java.util.Iterator;
import java.util.List;
import javax.persistence.TypedQuery;
import org.hibernate.query.Query;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import modelo.Asignatura;
import modelo.Teacher;
import auxiliar.HibernateUtil;

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
		TypedQuery<Teacher> q = session.createQuery("from Teacher", Teacher.class);
		List<Teacher> results = q.getResultList();
		for (Teacher profe : results) {
            System.out.println(profe.toString());
        }
		System.out.println("Fin Mostrando profesores");

	}

//	System.out.println("2 - Inserta un profesor");

	public void e2_insertarProfesor(String nombre, int experiencia) {

		System.out.println("Insertando profesor - Hibernate");
        Teacher profe = new Teacher();
        profe.setName(nombre);
        profe.setWorkingYears(experiencia);
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(profe);
            tx.commit();
            System.out.println("Insertado profesor con éxito");
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.out.println("Error al acceder a la base de datos: " + e.getMessage());
        }

	}

//	System.out.println("3 - Modificar experienca de un profesor");

	public void e3_modificarProfesor(int id, int experiencia) {

		System.out.println("Modificando experiencia profesor - Hibernate");      
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Teacher profe = session.get(Teacher.class, id);       
            if (profe != null) {
                profe.setWorkingYears(experiencia);
                session.update(profe);
                tx.commit();
                System.out.println("Modificada experiencia");
            } else {
                System.out.println("Profesor no encontrado");
            }
        } catch (Exception e) {
            if (tx != null) tx.rollback();
			System.out.println("Error al acceder a la base de datos: " + e.getMessage());
        }

	}

//	System.out.println("4 - Muestra todas las asignaturas (debe aparecer el nombre del profesor)");
//  Para mostrar los datos se puede usar el toString de la clase asignatura

	public void e4_mostrarAsignaturas() {

		System.out.println("Salida de consulta Asignaturas (y profesor) - Hibernate");
		TypedQuery<Asignatura> q = session.createQuery("from Asignatura", Asignatura.class);
		List<Asignatura> results = q.getResultList();	
		for (Asignatura asig : results) {
			String nombreProfe = (asig.getProfesor() != null) ? asig.getProfesor().getName() : "Sin profesor";		
			System.out.println("Id: " + asig.getId() + 
							" - Nombre: " + asig.getNombre() + 
							" - Ciclo: " + asig.getCiclo() + 
							" - Profesor: " + nombreProfe);
		}
		System.out.println("Fin Salida de consulta Asignaturas");
		
	}

//	System.out.println("5 - Muestra todas los profesores (debe aparecer el nombre de las asignaturas que imparte)");

	public void e5_mostrarProfesoresAvanzado() {

		System.out.println("Salida de consulta Profesores (y sus asignaturas) - Hibernate");
		TypedQuery<Teacher> q = session.createQuery("from Teacher", Teacher.class);
		List<Teacher> results = q.getResultList();
		for (Teacher profe : results) {
			System.out.println("Profesor: " + profe.getName());		
			if (profe.getConjuntoAsignaturas() != null && !profe.getConjuntoAsignaturas().isEmpty()) {
				for (Asignatura asig : profe.getConjuntoAsignaturas()) {
					System.out.println("\t -> Imparte: " + asig.getNombre() + " (" + asig.getCiclo() + ")");
				}
			} else {
				System.out.println("\t -> No tiene asignaturas asignadas.");
			}
		}
		System.out.println("Fin Salida de consulta Profesores Avanzado");

	}
	
}
