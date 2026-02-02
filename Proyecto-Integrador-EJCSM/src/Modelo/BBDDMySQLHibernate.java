package Modelo;

import java.util.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public class BBDDMySQLHibernate implements Funcionalidades{

	private HashMap<Integer, Libro> biblioteca;
	private SessionFactory sessionFactory;

	public BBDDMySQLHibernate() {
		try {
        	this.sessionFactory = new Configuration().configure("res/hibernate.cfg.xml").buildSessionFactory();
        	// this.session = sessionFactory.openSession();
        } catch (Throwable e) {
            System.err.println("Error: No se ha podido crear la SessionFactory.");
            System.err.println(e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
		this.biblioteca = leer();
	}

	@Override
    public HashMap<Integer,Libro> leer() {
		HashMap<Integer, Libro> aux = new HashMap<>();
		try (Session session = sessionFactory.openSession()) {
			List<Libro> datos = session.createQuery("from Libro",Libro.class).list();
			session.close();
			for (Libro libro : datos) {
				int isbn = libro.getIsbn();
				aux.put(isbn, libro);
			}
		} catch (HibernateException e) {
			System.err.println("Error: No se han podido leer los datos la base de datos.");
            System.err.println(e.getMessage());
		}
		return aux;
	}

	@Override
    public void guardar(HashMap<Integer,Libro> datos) {
		Transaction ts = null;
		try (Session session = sessionFactory.openSession()) {
			ts = session.beginTransaction();
			for (Libro libro : datos.values()) {
                session.saveOrUpdate(libro);
            }
			ts.commit();
		} catch (HibernateException e) {
			if (ts != null) {
				ts.rollback();
			}
			System.err.println("Error: No se ha podido establecer la conexión con la base de datos.");
		}
	}

	@Override
    public void insertar(Libro libro) {
		int isbn = libro.getIsbn();
		if (this.biblioteca.containsKey(isbn)) {
			System.out.println("Error: Ya existe un libro con el mismo isbn: " + isbn);
			return;
		}
		Transaction ts = null;
		try (Session session = sessionFactory.openSession()) {
			ts = session.beginTransaction();
			session.save(libro);
			ts.commit();
			this.biblioteca.put(libro.getIsbn(), libro);
			System.out.println("El libro con el isbn: " + isbn + " se ha registrado correctamente.");
		} catch (HibernateException e) {
			if (ts != null) {
				ts.rollback();
			}
			System.err.println("Error: No se ha podido insertar el libro con el ISBN '" + isbn + "' en la base de datos.");
            System.err.println(e.getMessage());
		}

		/*
		Session session = sessionFactory.openSession();
		Transaction ts = session.beginTransaction();
		Libro aux = session.get(Libro.class, libro.getISBN());
		if(aux == null){
			session.save(libro);
			this.biblioteca.add(libro.getISBN(), libro);
			System.out.println("El libro con el isbn: " + isbn + " se ha registrado correctamente.");
		} else {
			System.err.println("Error: No se ha podido insertar el libro con el ISBN '" + isbn + "' en la base de datos.");
		}
		ts.commit();
		session.close();
		*/
	
	}

	@Override
	public void borrar(int isbn) {
		if (!this.biblioteca.containsKey(isbn)) {
            System.out.println("Error: No existe ningún libro con el ISBN '" + isbn + "'.");
            return;
        }
		Transaction ts = null;
		try (Session session = sessionFactory.openSession()) {
			ts = session.beginTransaction();
			Libro aux = session.get(Libro.class, isbn);
			session.delete(aux);
			ts.commit();
			this.biblioteca.remove(isbn);
			System.out.println("El libro con el ISBN: " + isbn + " se ha borrado correctamente.");
		} catch (HibernateException e) {
			if (ts != null) {
				ts.rollback();
			}
			System.err.println("Error: No se ha podido borrar el con el ISBN '" + isbn + "' en la base de datos.");
            System.err.println(e.getMessage());
		}
	}

	@Override
    public void modificar(int isbn, Libro libroNuevo) {
        if (!this.biblioteca.containsKey(isbn)) {
            System.out.println("Error: No existe ningún libro con el ISBN '" + isbn + "'.");
            return;
        }
        Transaction ts = null;
        try (Session session = sessionFactory.openSession()) {
            ts = session.beginTransaction();
            Libro libroBD = session.get(Libro.class, isbn);
            if (isbn == libroNuevo.getIsbn()) {
                libroBD.setTitulo(libroNuevo.getTitulo());
                libroBD.setAutor(libroNuevo.getAutor());
                libroBD.setGenero(libroNuevo.getGenero());
                libroBD.setEditorial(libroNuevo.getEditorial());
                session.update(libroBD);
            } else {
                session.delete(libroBD);
                session.save(libroNuevo);
            }
            ts.commit();
            this.biblioteca.remove(isbn);
            this.biblioteca.put(libroNuevo.getIsbn(), libroNuevo);
            System.out.println("El libro con el ISBN '" + isbn + "' se ha sustituido correctamente por el libro con el ISBN '" + libroNuevo.getIsbn() + "'.");
        } catch (HibernateException e) {
            if (ts != null) ts.rollback();
            System.err.println("Error: No se ha podido modificar el libro con el ISBN '" + isbn + "' en la base de datos.");
            System.err.println(e.getMessage());
        }
    }

	/*
	
	@Override
	public void buscar(int isbn) {
		try (Session session = sessionFactory.openSession()) {
			Libro libro = session.get(Libro.class, isbn);
			if (libro == null) {
				System.out.println("Error: No existe ningún libro con el ISBN '" + isbn + "'.");
			} else {
				System.out.println("Libro encontrado con ISBN '" + isbn + "':");
				System.out.println(libro);
			}
		} catch (HibernateException e) {
			System.err.println("Error: No se ha podido buscar el libro con el ISBN '" + isbn + "' en la base de datos.");
            System.err.println(e.getMessage());
		}
	}

	@Override
	public void mostrar() {
		try (Session session = sessionFactory.openSession()) {
			List<Libro> libros = session.createQuery("from Libro", Libro.class).list();
			if (libros.isEmpty()) {
				System.out.println("No hay libros en la base de datos.");
			} else {
				libros.forEach(System.out::println);
			}
		} catch (HibernateException e) {
			System.err.println("Error: No se han podido mostrar los libros en la base de datos.");
            System.err.println(e.getMessage());
		}
	}

	
	*/

}