package Demo.Model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "play_card")
public class PlayCard {

    public PlayCard() {}

    public PlayCard(long id, String name, String role, String collection) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.collection = collection;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column( name = "name", nullable = false )
    private String name;

    @Column( name = "role", nullable = false )
    private String role;

    @Column( name = "collection", nullable = false )
    private String collection;

    @OneToMany( mappedBy = "playCard", fetch = FetchType.LAZY )
    private Set<Add> adds;


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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public Set<Add> getAdds() {
        return adds;
    }

    public void setAdds(Set<Add> adds) {
        this.adds = adds;
    }

}