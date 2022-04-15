package ControllerModelDAO;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HiberFilmDAO {


    private static SessionFactory sessionFactory;

    private HiberFilmDAO() {
    }

    public static SessionFactory buildSessionFactory() {

        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }

}

