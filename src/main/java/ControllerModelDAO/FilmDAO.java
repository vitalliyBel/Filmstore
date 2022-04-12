package ControllerModelDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class FilmDAO {
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;
    private Connection jdbcConnection;

    public FilmDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    protected void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(
                    jdbcURL,jdbcUsername, jdbcPassword);
        }
    }

    protected void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public boolean insert(Film film) throws SQLException {
        String sql = "INSERT INTO film (title, genre, year, price ) VALUES (?, ?, ?)";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, film.getTitle());
        statement.setString(2, film.getGenre());
        statement.setInt(3, film.getYear());
        statement.setInt(4, film.getPrice());

        boolean inserted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return inserted;
    }

    public List<Film> listAllFilms() throws SQLException {
        List<Film> listFilm = new ArrayList<>();

        String sql = "SELECT * FROM film";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resSet = statement.executeQuery(sql);

        while (resSet.next()) {
            int id = resSet.getInt("film_id");
            String title = resSet.getString("title");
            String genre = resSet.getString("genre");
            int year = resSet.getInt("year");
            int price = resSet.getInt("price");

            Film film = new Film(id, title, genre, year, price);
            listFilm.add(film);
        }

        resSet.close();
        statement.close();

        disconnect();

        return listFilm;
    }

    public boolean delete(Film film) throws SQLException {
        String sql = "DELETE FROM film where film_id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, film.getId());

        boolean deleted = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return deleted;
    }

    public boolean update(Film film) throws SQLException {
        String sql = "UPDATE film SET title = ?, genre = ?, year = ?, price = ?";
        sql += " WHERE film_id = ?";
        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setString(1, film.getTitle());
        statement.setString(2, film.getGenre());
        statement.setInt(3, film.getYear());
        statement.setInt(4, film.getPrice());
        statement.setInt(5, film.getId());

        boolean updated = statement.executeUpdate() > 0;
        statement.close();
        disconnect();
        return updated;
    }

    public Film get(int id) throws SQLException {
        Film film = null;
        String sql = "SELECT * FROM film WHERE film_id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resSet = statement.executeQuery();

        if (resSet.next()) {
            String title = resSet.getString("title");
            String genre = resSet.getString("genre");
            int year = resSet.getInt("year");
            int price = resSet.getInt("price");

            film = new Film(id, title, genre, year, price);
        }

        resSet.close();
        statement.close();

        return film;
    }
}
