package controller;

import model.SessionModel;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.05.16.
 */
public class ManageUsers {

    public static boolean addNewUser(String nickname, String password) {
        boolean added = true;
        User newUser = new User();
        newUser.setNickname(nickname);
        newUser.setPassword(password);

        Session session = SessionModel.getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(newUser);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            added = false;
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return added;
    }

    public static boolean checkNickName(String nickname) {
        boolean unique = true;
        Session session = SessionModel.getSession();
        try {
           if (!session.createQuery("FROM User WHERE nickname = '" + nickname + "'").list().isEmpty()) {
               unique = false;
           }
        }
        catch (Exception e) {
            e.printStackTrace();
            unique = false;
        }
        finally {
            session.close();
        }
        System.out.println(unique);
        return unique;
    }

    public static boolean isCorrect(String nickname, String password) {
        boolean present = false;
        User user;
        Session session = SessionModel.getSession();
        try {
            for (Object object: session.createQuery("FROM User ").list()) {
                user = (User) object;
                if(user.getNickname().equals(nickname) && user.getPassword().equals(password)) {
                    present = true;
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return present;
    }

    public static User getUserByName(String name) {
        User user = null;
        Session session = SessionModel.getSession();
        try {
            for (Object object: session.createQuery("FROM User WHERE nickname = '" + name +"'").list()) {
                user = (User) object;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return user;
    }

}
