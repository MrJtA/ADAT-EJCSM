package Modelo;

import Vista.Vista;
import java.util.*;
import javax.persistence.*;

public final class BBDDObjetos implements Funcionalidades{
    
    private final EntityManagerFactory emf;
    private final String nombreBBDD;
    private final HashMap<Integer,Libro> biblioteca;
    private final Vista vista;

    public BBDDObjetos() {
        this.nombreBBDD = "Libro.odb";
        this.emf = Persistence.createEntityManagerFactory("db/" + this.nombreBBDD);
        this.vista = new Vista();
        this.vista.conexionBBDD(this.nombreBBDD);
        this.biblioteca = leer();
    }

    @Override
    public HashMap<Integer,Libro> leer() {
        HashMap<Integer,Libro> aux = new HashMap<>();
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l", Libro.class);
		    List<Libro> datos = query.getResultList();	
            for (Libro libro : datos) {
                int isbn = libro.getIsbn();
                if (aux.containsKey(isbn)) {
					this.vista.errorLibroRepetido(isbn);
				} else {
					aux.put(isbn, libro);
				}
		    }
        } catch (Exception e) {
            this.vista.errorLecturaBBBDD(this.nombreBBDD);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return aux;
    }

    @Override
    public void guardar(HashMap<Integer, Libro> datos) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Libro").executeUpdate();
            for (Libro libro : datos.values()) {
                em.persist(libro);
            }
            em.getTransaction().commit();
            this.vista.guardadoBBDD(this.nombreBBDD);
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            this.vista.errorGuardadoBBDD(this.nombreBBDD);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void insertar(Libro libro) {
        int isbn = libro.getIsbn();
		if (this.biblioteca.containsKey(isbn)) {
			this.vista.errorLibroExistente(isbn);
			return;
		}
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
            this.biblioteca.put(isbn, libro);
            this.vista.insercionLibro(isbn);
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            this.vista.errorInsercionLibro(isbn);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void borrar(int isbn) {
        if (!this.biblioteca.containsKey(isbn)) {
            this.vista.errorLibroInexistente(isbn);
            return;
        }
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            Libro libro = em.find(Libro.class, isbn);
            if (libro != null) {
                em.remove(libro);
                this.biblioteca.remove(isbn);
                this.vista.borradoLibro(isbn);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            this.vista.errorBorradoLibro(isbn);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void modificar(int isbn, Libro libro) {
        if (!this.biblioteca.containsKey(isbn)) {
            this.vista.errorLibroInexistente(isbn);
            return;
        }
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            if (isbn != libro.getIsbn()) {
                Libro libroAntiguo = em.find(Libro.class, isbn);
                if (libroAntiguo != null) {
                    em.remove(libroAntiguo);
                }
                em.persist(libro);
            } else {
                em.merge(libro);
            }
            em.getTransaction().commit();
            this.biblioteca.remove(isbn);
            this.biblioteca.put(libro.getIsbn(), libro);
			this.vista.modificacionLibro(isbn, libro.getIsbn());
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            this.vista.errorModificacionLibro(isbn);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void restablecer() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Libro").executeUpdate();
            em.getTransaction().commit();
            this.biblioteca.clear();
            this.vista.restablecerLibros();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            this.vista.errorRestablecerLibros();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /*
    
    @Override
    public void buscar(int isbn) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            Libro libro = em.find(Libro.class, isbn);
            if (libro == null) {
                this.vista.errorLibroExistente(isbn);
            } else {
                this.vista.buscar(this.biblioteca, isbn);
            }
        } catch (Exception e) {
            this.vista.errorBusquedaLibro(isbn);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void mostrar() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Libro> query = em.createQuery("SELECT l FROM Libro l", Libro.class);
            List<Libro> libros = query.getResultList();
            if (libros.isEmpty()) {
                this.vista.errorVacío();
            } else {
                HashMap<Integer, Libro> aux = new HashMap<>();
                for (Libro libro : libros) {
                    aux.put(libro.getIsbn(), libro);
                }
                this.vista.mostrar(aux);
            }
        } catch (Exception e) {
            this.vista.errorMuestraLibros();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    */

}