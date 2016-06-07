package servlets;

import controller.ManageMessages;
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
 * @since 07.06.16.
 */
public class NewMessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String text = request.getParameter("new-message");
        if (text != null) {
            if (!text.equals("")) {
                HttpSession httpSession = request.getSession();
                String nickname = (String) httpSession.getAttribute("user");
                User user = ManageUsers.getUserByName(nickname);
                ManageMessages.addMessage(user, text);
            }
        }
        response.sendRedirect("../index.html");
    }
}
