package Demo.Model;


import javax.persistence.*;
import java.util.Date;

@Entity
public class Chat {

    public Chat(){};

    public Chat(Long id, String message, Date sendTime) {
        this.id = id;
        this.message = message;
        this.sendTime = sendTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column( name = "message", nullable = false )
    private String message;

    @Column( name = "send_time", nullable = false )
    private Date sendTime;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}
