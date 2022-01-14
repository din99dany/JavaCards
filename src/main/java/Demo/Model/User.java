package Demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    public User() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public User(long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Column( name = "name", nullable = false)
    @JsonIgnore
    private String name;

    @Column( name = "email", nullable = false)
    private String email;

    @Column( name = "password_hash", nullable = false)
    @JsonIgnore
    private String password;

    @OneToMany( mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Add> adds;

    @OneToMany( mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE )
    private Set<Chat> sendChats;

    @OneToMany( mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Chat> receiveChats;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Offer> offers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Add> getAdds() {
        return adds;
    }

    public void setAdds(Set<Add> adds) {
        this.adds = adds;
    }

    public Set<Chat> getSendChats() {
        return sendChats;
    }

    public void setSendChats(Set<Chat> sendChats) {
        this.sendChats = sendChats;
    }

    public Set<Chat> getReceiveChats() {
        return receiveChats;
    }

    public void setReceiveChats(Set<Chat> receiveChats) {
        this.receiveChats = receiveChats;
    }
}
