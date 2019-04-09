package controllers;

import beans.Post;
import beans.User;
import dao.PostDAO;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.TimeZone;

@WebServlet(name = "CreatePost", urlPatterns ={"/createpost"})
public class CreatePost extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    public CreatePost() {
        super();
    }

    public void init() throws ServletException {
        try {
            ServletContext context = getServletContext();
            String driver = context.getInitParameter("dbDriver");
            String url = context.getInitParameter("dbUrl");
            String user = context.getInitParameter("dbUser");
            String password = context.getInitParameter("dbPassword");
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);

        } catch (ClassNotFoundException e) {
            throw new UnavailableException("Can't load database driver");
        } catch (SQLException e) {
            throw new UnavailableException("Couldn't get db connection");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        User u = (User) s.getAttribute("sessionUser");

        if (u != null && u.isValid()) {

            String text, userid, imgurl;
            Date date = new Date();
            text = request.getParameter("text");
            imgurl = request.getParameter("imgURL");
            userid = u.getUserId();

            Post p;
            if (imgurl == null) {
                p = new Post(text, date, userid);
            } else {
                p = new Post(text, date, userid, imgurl);
            }

            PostDAO pDAO = new PostDAO(connection);

            try {
                pDAO.createPost(p);
            } catch (SQLException e) {
                //TODO send error
            }

            response.sendRedirect("getpost");

        } else {
            response.sendRedirect("login");
        }
    }

}

