package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.ManageUsers;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 29.05.16.
 */
public class SessionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        ObjectMapper objectMapper = new ObjectMapper();
        User user = null;
        if (httpSession.getAttribute("user") != null) {
            user = ManageUsers.getUserByName((String)httpSession.getAttribute("user"));
            user.setPassword("XXXX");
        }
        resp.setContentType("application/json");
        objectMapper.writeValue(resp.getOutputStream(), user);
    }
}
