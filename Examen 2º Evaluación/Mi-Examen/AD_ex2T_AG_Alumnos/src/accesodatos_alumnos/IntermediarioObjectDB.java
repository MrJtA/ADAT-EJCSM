package accesodatos_alumnos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import modelo.MusicBand;

public class IntermediarioObjectDB {
	
	/*
	 * Crear atributos si es necesario
	 */

	private EntityManagerFactory emf;

	
	/*
	 * Constructor. Se puede utilizar para crear la conexión e inicializar atributos de la clase
	 */
	
	public IntermediarioObjectDB() {
		this.emf = Persistence.createEntityManagerFactory("db/examen2T_AG.odb");
	}
	
	/*
	 * Insertar grupo
	 */
	
	public void insertarGrupo(String nombregrupo) {
		MusicBand grupo = new MusicBand(nombregrupo);
		int id = (int) (Math.random()*10000) + 67;
		grupo.setId(id);
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(grupo);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
	}
	
	/*
	 * Formato de salida para leerInfoGrupos
	 * 
	 	--------------------------------------------------------------
		ID: 1 - Extremoduro 
		ID: 2 - Mecano
		--------------------------------------------------------------
	 * 
	 * 
	 */
	
	public void leerInfoGrupos() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<MusicBand> query = em.createQuery("SELECT a FROM MusicBand a", MusicBand.class);
		    List<MusicBand> datos = query.getResultList();	
			System.out.println("--------------------------------------------------------------");
            for (MusicBand g : datos) {
				System.out.println("ID: " + g.getId() + " - " + g.getName());
			}
			System.out.println("--------------------------------------------------------------");
        } catch (Exception e) {
            System.err.println("Error: No se han podido leer los datos la base de datos.");
            System.err.println(e.getMessage());
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
	

}
