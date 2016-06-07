package model;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Yurii Krat
 * @version 1.0
 * @since 26.05.16.
 */

@Entity
@Table(name = "Messages")
public class Message {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Message() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "id: " + id + "\ntext: " + text + "\ndate: " + date +
                "\nuser: " + user + "\n=======================================";
    }
}
