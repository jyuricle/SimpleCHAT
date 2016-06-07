package servlets;

import controller.ManageUsers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 29.05.16.
 */
public class SignUp extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String text = new String(Files.readAllBytes(Paths.get(getServletContext().getRealPath("pages/registration.html"))));
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        out.println(text.replaceFirst("Check input", ""));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickname = req.getParameter("nickname");
        String password = req.getParameter("password");
        String same_password = req.getParameter("same-password");
        String text = new String(Files.readAllBytes(Paths.get(getServletContext().getRealPath("pages/registration.html"))));
        String result = null;
        boolean success = true;
        PrintWriter out = resp.getWriter();
        resp.setContentType("text/html");
        if (nickname.equals("") || password.equals("") || same_password.equals("")) {
            result = "<p class = \"warnings\">Empty fields! Please, fill required fields...</p>";
            success = false;
        }
        else if (!password.equals(same_password)) {
            result = "<p class = \"warnings\">Input passwords are different! It must be same...</p>";
            success = false;
        }
        else if (!ManageUsers.checkNickName(nickname)) {
            result = "<p class = \"warnings\">User with such nickname already exists! Please, enter another name...";
            success = false;
        }
        if (!success) {
            out.println(text.replaceFirst("Check input", result));
        }
        else {
            ManageUsers.addNewUser(nickname, password);
            resp.sendRedirect("../index.html");
        }
    }
}
