package ejemploHibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import modelo.Employee;

public class HibernateDemo {


    public static void main(String[] args){

        Employee e= new Employee();
        e.setEmpId(1);
        e.setEmpName("Ana");
        SessionFactory sf = new Configuration().configure().buildSessionFactory();
        Session s=sf.openSession();
        s.beginTransaction();
        s.save(e);
        s.getTransaction().commit();
        s.close();
        System.exit(0);
    }

}