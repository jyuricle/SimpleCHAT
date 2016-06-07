package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Message;
import model.SessionModel;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 30.05.16.
 */
public class ManageMessages {

    public static boolean addMessage(User user, String text) {
        boolean added = true;
        Message message = new Message();
        message.setText(text);
        message.setUser(user);
        message.setDate(new Date(System.currentTimeMillis()));

        Session session = SessionModel.getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            added = false;
            e.printStackTrace();
        } finally {
            session.close();
        }
        return added;
    }

   public static List<Message> showMessages() {
       List<Message> messages = new LinkedList<Message>();
       Session session = SessionModel.getSession();
       try {
           for (Object message: session.createQuery("FROM Message GROUP BY date").list()) {
               messages.add((Message)message);
           }
       }
       catch (Exception e) {
           e.printStackTrace();
       }
       finally {
           session.close();
       }
       return messages;
   }

    public static void main(String[] args) {
        List<Message> messages = showMessages();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(System.out, messages);
        }
        catch (IOException e) {

        }
    }

}
