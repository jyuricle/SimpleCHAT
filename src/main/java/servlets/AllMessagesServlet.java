package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.ManageMessages;
import model.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 30.05.16.
 */
public class AllMessagesServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json");
        List<Message> messages = ManageMessages.showMessages();
        objectMapper.writeValue(response.getWriter(), messages);
    }
}
