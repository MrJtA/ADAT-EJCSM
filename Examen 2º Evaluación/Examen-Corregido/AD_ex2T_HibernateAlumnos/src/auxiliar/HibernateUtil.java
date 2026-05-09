package auxiliar;

import org.hibernate.cfg.Configuration;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateUtil {

    private SessionFactory sessionFactory;
    private Session session;
    
    public HibernateUtil(){

        try {

        	System.err.println("\n CARGANDO HIBERNATE.... \n");        	
        	LogManager.getLogManager().reset();
        	Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
        	globalLogger.setLevel(java.util.logging.Level.OFF);        	
        	
            // Create the SessionFactory from standard (hibernate.cfg.xml) config file.
        	sessionFactory = new Configuration().configure().buildSessionFactory();
                     
        } catch (Exception ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    	
    }
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public Session getSession() {
    	session = sessionFactory.openSession();
        return session;
    }
    
	public void closeSesion() {
		session.close();
	}
    
    
}