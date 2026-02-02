package Modelo;

import java.util.*;
import javax.persistence.*;

public class BBDDObjetos implements Funcionalidades{
    
    private EntityManagerFactory emf;
    private HashMap<Integer,Libro> biblioteca;

    public BBDDObjetos() {
        this.emf = Persistence.createEntityManagerFactory("db/Libro.odb");
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
                aux.put(isbn, libro);
		    }
        } catch (Exception e) {
            System.err.println("Error: No se han podido leer los datos la base de datos.");
            System.err.println(e.getMessage());
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
            for (Libro libro : datos.values()) {
                em.merge(libro);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error: No se ha podido establecer la conexión con la base de datos.");
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
			System.out.println("Error: Ya existe un libro con el mismo isbn: " + isbn);
			return;
		}
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
            this.biblioteca.put(isbn, libro);
        } catch (Exception e) {
            System.err.println("Error: No se ha podido insertar el libro con el ISBN '" + isbn + "' en la base de datos.");
            System.err.println(e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void borrar(int isbn) {
        if (!this.biblioteca.containsKey(isbn)) {
            System.out.println("Error: No existe ningún libro con el ISBN '" + isbn + "'.");
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
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Error: No se ha podido borrar el con el ISBN '" + isbn + "' en la base de datos.");
            System.err.println(e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void modificar(int isbn, Libro libro){
        if (!this.biblioteca.containsKey(isbn)) {
            System.out.println("Error: No existe ningún libro con el ISBN '" + isbn + "'.");
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
			System.out.println("El libro con el ISBN '" + isbn + "' se ha sustituido correctamente por el libro con el ISBN '" + libro.getIsbn() + "'.");
        } catch (Exception e) {
            System.err.println("Error: No se ha podido modificar el libro con el ISBN '" + isbn + "' en la base de datos.");
            System.err.println(e.getMessage());
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
                System.out.println("Error: No existe ningún libro con el ISBN '" + isbn + "'.");
            } else {
                System.out.println("Libro encontrado con ISBN '" + isbn + "':");
                System.out.println(libro);
            }
        } catch (Exception e) {
            System.err.println("Error: No se ha podido buscar el libro con el ISBN '" + isbn + "' en la base de datos.");
            System.err.println(e.getMessage());
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
                System.out.println("No hay libros en la base de datos.");
            } else {
                for (Libro l : libros) {
                    System.out.println(l);
                }
            }
        } catch (Exception e) {
            System.err.println("Error: No se han podido mostrar los libros en la base de datos.");
            System.err.println(e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    */

}