package ControllerModelDAO;



import java.sql.SQLException;
import java.util.List;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static ControllerModelDAO.HiberFilmDAO.buildSessionFactory;

/**
 * A data access  (DAO) class that provides CRUD (Create, Read, Update, Delete)
 * operations on a table in a database.
 */
 public class FilmDAO {


    /**
     * The method inserts a new row into the table
     *
     * @param film
     * @return
     * @throws SQLException
     */
    public void insert(Film film) throws SQLException {

        SessionFactory sessionFactory = buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(film);
        session.getTransaction().commit();
        session.close();


    }

    /**
     * For reading: listAllFilms() - retrieves all lines
     *
     * @return
     * @throws SQLException
     */
    public List<Film> listAllFilms() throws SQLException {
        List listFilm;
        SessionFactory sessionFactory = buildSessionFactory();
        Session session = sessionFactory.openSession();
        listFilm = session.createQuery("SELECT * FROM film").list();
        session.close();

        return listFilm;
    }

    /**
     * This method deletes an existing row in the database based on the value of the primary key (ID)
     *
     * @return
     * @throws SQLException
     * @param id
     */
    public void delete(int id) throws SQLException {
        SessionFactory sessionFactory = buildSessionFactory();
        Session session = sessionFactory.openSession();
        Film film = get(id);
        session.remove(film);
        session.close();

    }

    /**
     * This method updates an existing row in the database.
     *
     * @param film
     * @return
     * @throws SQLException
     */
    public void update(Film film) throws SQLException {
        SessionFactory sessionFactory = buildSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            Film filmUpdate = session.get(Film.class, film.getId());
            filmUpdate.setTitle(film.getTitle());
            filmUpdate.setGenre(film.getGenre());
            filmUpdate.setYear(film.getYear());
            filmUpdate.setPrice(film.getPrice());
            filmUpdate.setId(film.getId());
        } catch (Exception e) {

            session.getTransaction().rollback();

            e.printStackTrace();
        } finally {
            session.close();
        }

    }

    /**
     * This method returns a specific row based on the primary key (ID) value.
     * @param id
     * @return
     * @throws SQLException
     */

    public Film get(int id) throws SQLException {
        SessionFactory sessionFactory = buildSessionFactory();
        Session session = sessionFactory.openSession();
        Film film = session.get(Film.class, id);
        session.close();

        return film;
    }



}