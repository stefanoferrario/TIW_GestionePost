package controllers;

import beans.Post;
import beans.User;
import dao.PostDAO;

import javax.servlet.RequestDispatcher;
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
import java.util.List;
import java.util.TimeZone;

@WebServlet(name = "GetPost", urlPatterns ={"/getpost"})
public class GetPost extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    public GetPost() {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession s = request.getSession(true);
        User u = (User) s.getAttribute("sessionUser");

        if (u != null && u.isValid()) {

            PostDAO pDAO = new PostDAO(connection);
            List<Post> posts;
            try {
                posts = pDAO.findAllSorted();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                posts = null;
            }
            Integer pinnedId = null;

            String newPin = request.getParameter("pin");;
            if (newPin == null) {
                pinnedId = (Integer) s.getAttribute("pin");
            } else {
                pinnedId = Integer.parseInt(newPin);
                s.setAttribute("pin", pinnedId);
            }

            if (pinnedId != null) {
                Post p;
                for (int i=0; i<posts.size(); i++) {
                    p = posts.get(i);
                    if (p.getId() == pinnedId) {
                        posts.remove(p);
                        posts.add(0,p); //inserisce in testa
                        break;
                    }
                }
            }

            String path = "/WEB-INF/Posts.jsp";
            request.setAttribute("posts", posts);
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            dispatcher.forward(request, response);

        } else {
            response.sendRedirect("login");
        }
    }

}
