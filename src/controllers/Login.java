package controllers;

import beans.User;
import dao.UserDAO;

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
import java.util.TimeZone;

@WebServlet(name = "Login", urlPatterns = {"/login"})
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Connection connection;

    public Login() {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = "/WEB-INF/Login.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userid = request.getParameter("userid");
        String pw = request.getParameter("pw");
        HttpSession session = request.getSession(true);

        if (userid != null || !userid.isEmpty()) {
            session.setAttribute("username", userid);
        } else {
            session.removeAttribute("username");
        }

        if (userid == null || pw == null || userid.isEmpty() || pw.isEmpty()) {
            session.removeAttribute("loginfailed");
            session.setAttribute("loginempty", true);
            String path = "/WEB-INF/Login.jsp";
            RequestDispatcher dispatcher = request.getRequestDispatcher(path);
            dispatcher.forward(request, response);
        } else {

            User u = new User(userid, pw);
            UserDAO uDAO = new UserDAO(connection);
            try {
                u = uDAO.login(u);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                u.setValid(false);
            }

            if (u.isValid()) {
                session.removeAttribute("loginfailed");
                session.removeAttribute("loginempty");
                session.setAttribute("sessionUser", u);
                response.sendRedirect("getpost"); //logged-in page
            } else {
                session.removeAttribute("loginempty");
                session.setAttribute("loginfailed", true);

                //sistemare per evitare di ripetere continuamente queste tre righe
                String path = "/WEB-INF/Login.jsp";
                RequestDispatcher dispatcher = request.getRequestDispatcher(path);
                dispatcher.forward(request, response);
            }
        }

    }

}
