package Modelo;

import Vista.Vista;
import java.util.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;

public final class BBDDMySQLHibernate implements Funcionalidades{

	private SessionFactory sessionFactory;
	private final String nombreBBDD;
	private final HashMap<Integer, Libro> biblioteca;
	private final Vista vista;

	public BBDDMySQLHibernate() {
		this.nombreBBDD = "biblioteca";
		this.vista = new Vista();
		try {
        	this.sessionFactory = new Configuration().configure("res/hibernate.cfg.xml").buildSessionFactory();
			this.vista.conexionBBDD(this.nombreBBDD);
        	// this.session = sessionFactory.openSession();
        } catch (HibernateException e) {
            this.vista.errorConexionBBDD(this.nombreBBDD);
        }
		this.biblioteca = leer();
	}

	@Override
	public HashMap<Integer, Libro> leer() {
		HashMap<Integer, Libro> aux = new HashMap<>();
		try (Session session = sessionFactory.openSession()) {
			List<Libro> datos = session.createQuery("from Libro", Libro.class).list();		
			for (Libro libro : datos) {
				int isbn = libro.getIsbn();
				if (aux.containsKey(isbn)) {
					this.vista.errorLibroRepetido(isbn);
				} else {
					aux.put(isbn, libro);
				}
			}
		} catch (HibernateException e) {
			this.vista.errorLecturaBBBDD(this.nombreBBDD);
		}
		return aux;
	}

	@Override
	public void guardar(HashMap<Integer, Libro> datos) {
		Transaction ts = null;
		try (Session session = sessionFactory.openSession()) {
			ts = session.beginTransaction();
			session.createQuery("delete from Libro").executeUpdate();
			for (Libro libro : datos.values()) {
				session.save(libro);
			}
			ts.commit();
			this.vista.guardadoBBDD(this.nombreBBDD);
		} catch (HibernateException e) {
			if (ts != null) {
				ts.rollback();
			}
			this.vista.errorGuardadoBBDD(this.nombreBBDD);
		}
	}

	@Override
    public void insertar(Libro libro) {
		int isbn = libro.getIsbn();
		if (this.biblioteca.containsKey(isbn)) {
			this.vista.errorLibroExistente(isbn);
			return;
		}
		Transaction ts = null;
		try (Session session = sessionFactory.openSession()) {
			ts = session.beginTransaction();
			session.save(libro);
			ts.commit();
			this.biblioteca.put(libro.getIsbn(), libro);
			this.vista.insercionLibro(isbn);
		} catch (HibernateException e) {
			if (ts != null) {
				ts.rollback();
			}
			this.vista.errorInsercionLibro(isbn);
		}
	}

	@Override
	public void borrar(int isbn) {
		if (!this.biblioteca.containsKey(isbn)) {
            this.vista.errorLibroInexistente(isbn);
            return;
        }
		Transaction ts = null;
		try (Session session = sessionFactory.openSession()) {
			ts = session.beginTransaction();
			Libro aux = session.get(Libro.class, isbn);
			session.delete(aux);
			ts.commit();
			this.biblioteca.remove(isbn);
			this.vista.borradoLibro(isbn);
		} catch (HibernateException e) {
			if (ts != null) {
				ts.rollback();
			}
			this.vista.errorBorradoLibro(isbn);
		}
	}

	@Override
    public void modificar(int isbn, Libro libroNuevo) {
        if (!this.biblioteca.containsKey(isbn)) {
            this.vista.errorLibroInexistente(isbn);
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
            this.vista.modificacionLibro(isbn, libroNuevo.getIsbn());
        } catch (HibernateException e) {
            if (ts != null) {
				ts.rollback();
			}
            this.vista.errorModificacionLibro(isbn);
        }
    }

	@Override
	public void restablecer() {
		Transaction ts = null;
		try (Session session = sessionFactory.openSession()) {
			ts = session.beginTransaction();
			session.createQuery("delete from Libro").executeUpdate();
			ts.commit();
			this.biblioteca.clear();
			this.vista.restablecerLibros();
		} catch (HibernateException e) {
			if (ts != null) {
				ts.rollback();
			}
			this.vista.errorRestablecerLibros();
		}
	}

	/*
	
	@Override
	public void buscar(int isbn) {
		try (Session session = sessionFactory.openSession()) {
			Libro libro = session.get(Libro.class, isbn);
			if (libro == null) {
				this.vista.errorLibroInexistente(isbn);
			} else {
				this.vista.buscar(this.biblioteca, isbn);
			}
		} catch (HibernateException e) {
			this.vista.errorBusquedaLibro(isbn);
		}
	}

	@Override
	public void mostrar() {
		try (Session session = sessionFactory.openSession()) {
			List<Libro> libros = session.createQuery("from Libro", Libro.class).list();
			if (libros.isEmpty()) {
				this.vista.errorVacío();
			} else {
				this.vista.mostrar(this.biblioteca);
			}
		} catch (HibernateException e) {
			this.vista.errorMuestraLibros();
		}
	}

	*/

}