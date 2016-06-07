package servlets;

import controller.ManageUsers;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 28.05.16.
 */
public class LogInOut extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String text = new String(Files.readAllBytes(Paths.get(getServletContext().getRealPath("pages/authentication.html"))));
        String result = null;
        boolean success = true;
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        if (!nickname.equals("") && !password.equals("")) {

            if (ManageUsers.isCorrect(nickname, password)) {
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("user", nickname);
                httpSession.setMaxInactiveInterval(60 * 60 * 24);
            }
            else {
                result = "<p class = \"warnings\">Incorrect input! Please, try again...</p>";
                success = false;
            }
        }
        else {
            result = "<p class = \"warnings\">Empty fields! Please, fill required fields...</p>";
            success = false;
        }
        if (!success) {
            out.println(text.replaceFirst("Check input", result));
        }
        else {
            response.sendRedirect("../index.html");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute("user") != null) {
            httpSession.setAttribute("user", null);
            httpSession.setMaxInactiveInterval(0);
            response.sendRedirect("../index.html");
        }
        else {
            String text = new String(Files.readAllBytes(Paths.get(getServletContext().getRealPath("pages/authentication.html"))));
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.println(text.replaceFirst("Check input", ""));
        }
    }
}
