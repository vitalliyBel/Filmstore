package ControllerModelDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * /**
 *  * ControllerServlet
 *  * This servlet acts as a page controller for the application, handling all
 *  * requests from the user.
 */

@WebServlet({"/", "/new","/insert" , "/delete",  "/edit", "/update"})

public class ControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FilmDAO filmDAO;

    /**
     * This method instantiates the FilmDAO class the first time the servlet is instantiated.
     * The JDBC connection information will be read from the servlet context parameters.
     * This method is called only once during the servlet's life cycle
     */
    public void init() {

        filmDAO = new FilmDAO();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Based on the request URL (begins with /edit, /list, /new, etc.),
     * the servlet invokes the appropriate methods.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insert(request, response);
                    break;
                case "/delete":
                    delete(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    update(request, response);
                    break;
                default:
                    listFilm(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    /**
     * This method uses the DAO class to retrieve all the film from the database
     * and then redirects them to the ListFilm.jsp page to display the result.
     * Similar logic is implemented for other methods!!!
     * @param request
     * @param response
     * @throws SQLException
     * @throws IOException
     * @throws ServletException
     */

    private void listFilm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Film> listFilm = filmDAO.listAllFilms();
        request.setAttribute("listFilm", listFilm);
        RequestDispatcher dispatcher = request.getRequestDispatcher("ListFilm.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("FormForFilm.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Film existingFilm = filmDAO.get(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("FormForFilm.jsp");
        request.setAttribute("film", existingFilm);
        dispatcher.forward(request, response);

    }

    private void insert(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String title = request.getParameter("title");
        String genre = request.getParameter("genre");
        int year = Integer.parseInt(request.getParameter("year"));
        int price = Integer.parseInt(request.getParameter("price"));

        Film newFilm = new Film(title, genre, year, price);
        filmDAO.insert(newFilm);
        response.sendRedirect("list");
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String genre = request.getParameter("genre");
        int year = Integer.parseInt(request.getParameter("year"));
        int price = Integer.parseInt(request.getParameter("price"));

        Film film = new Film(id, title, genre,year, price);
        filmDAO.update(film);
        response.sendRedirect("list");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        new Film(id);
        filmDAO.delete(id);
        response.sendRedirect("list");

    }
}