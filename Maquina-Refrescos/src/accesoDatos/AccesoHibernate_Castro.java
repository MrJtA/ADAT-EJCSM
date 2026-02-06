package accesoDatos;

import java.util.HashMap;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import logicaRefrescos.Deposito;
import logicaRefrescos.Dispensador;

public class AccesoHibernate_Castro implements I_Acceso_Datos {

    private SessionFactory sf;

    public AccesoHibernate() {
        try {
            // Carga la configuración de hibernate.cfg.xml
            this.sf = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Error al inicializar Hibernate: " + e.getMessage());
        }
    }

    @Override
    public HashMap<Integer, Deposito> obtenerDepositos() {
        HashMap<Integer, Deposito> listaDepositos = new HashMap<>();
        try (Session session = sf.openSession()) {
            // HQL: Consulta sobre la clase Deposito
            List<Deposito> resultado = session.createQuery("from Deposito", Deposito.class).list();
            for (Deposito dep : resultado) {
                // Usamos getValor() como clave del HashMap según tu código original
                listaDepositos.put(dep.getValor(), dep);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener depósitos: " + e.getMessage());
            return null;
        }
        return listaDepositos;
    }

    @Override
    public HashMap<String, Dispensador> obtenerDispensadores() {
        HashMap<String, Dispensador> listaDispensadores = new HashMap<>();
        try (Session session = sf.openSession()) {
            List<Dispensador> resultado = session.createQuery("from Dispensador", Dispensador.class).list();
            for (Dispensador disp : resultado) {
                listaDispensadores.put(disp.getClave(), disp);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener dispensadores: " + e.getMessage());
            return null;
        }
        return listaDispensadores;
    }

    @Override
    public boolean guardarDepositos(HashMap<Integer, Deposito> depositos) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            for (Deposito dep : depositos.values()) {
                // saveOrUpdate gestiona tanto la inserción como la actualización
                session.saveOrUpdate(dep);
            }
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error al guardar depósitos: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean guardarDispensadores(HashMap<String, Dispensador> dispensadores) {
        Transaction tx = null;
        try (Session session = sf.openSession()) {
            tx = session.beginTransaction();
            for (Dispensador disp : dispensadores.values()) {
                session.saveOrUpdate(disp);
            }
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            System.err.println("Error al guardar dispensadores: " + e.getMessage());
            return false;
        }
    }
}