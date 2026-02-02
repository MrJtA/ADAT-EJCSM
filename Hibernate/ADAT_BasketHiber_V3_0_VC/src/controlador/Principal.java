package controlador;

import utils.AccesoHibernate;

public class Principal {
	
    public static void main(String[] args) {
    	
    	AccesoHibernate accesoh;
    
    	try{
    		System.out.println("INICIO PRUEBAS BASKET HIBERNATE II ");
    		
    		accesoh = new AccesoHibernate();
    		
            //accesoh.borrarDatos();
    		//accesoh.insertarDatosPrueba();
    		accesoh.consultaEquipo();
            //accesoh.consultaJugadorPosicion();
        
    		System.out.println("FIN PRUEBAS BASKET HIBERNATE II");
    	} catch (Exception ex){
    		System.out.println("EXCEPCION!!!!");
    		ex.printStackTrace();
    	} finally{
    		System.out.println("REQUETEFIN");
    		System.exit(0);
    	} 

		//Llenar un set de una clase :
		/*	
			Equipo e = session.get(Equipo.class, 1);

			Set<Jugador> jugadores = e.getConjuntoJugadores();

			for (Jugador j : jugadores) {
    			System.out.println(j.getNombre());
			}
 		*/
    	
		/*public void eliminarPelicula(int id) {

    	Transaction tx = null;

    	try (Session session = sessionFactory.openSession()) {

        	tx = session.beginTransaction();

        	int filas = session.createQuery(
                "delete from Pelicula where id = :id")
                .setParameter("id", id)
                .executeUpdate();

        	tx.commit();

        	if (filas > 0) {
            	System.out.println("Película eliminada correctamente");
        	} else {
            System.out.println("No existe la película");
        	}

    	} catch (Exception e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
    	}
	}
 	*/

	/*public void eliminarPelicula(int id) {

    Transaction tx = null;

    try (Session session = sessionFactory.openSession()) {

        tx = session.beginTransaction();

        Pelicula p = session.get(Pelicula.class, id);

        if (p != null) {
            session.delete(p);   // 👈 delete() método
            System.out.println("Película eliminada");
        }

        tx.commit();

    } catch (Exception e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
    }
}
 */
		/*public void actualizarPelicula(Pelicula pelicula) {

    Transaction tx = null;

    try (Session session = sessionFactory.openSession()) {

        tx = session.beginTransaction();

        session.update(pelicula);   // 👈 AQUÍ ESTÁ EL UPDATE

        tx.commit();
        System.out.println("Película actualizada correctamente");

    } catch (Exception e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
    }
}
 */
    }
        

}
