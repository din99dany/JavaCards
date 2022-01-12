package Demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sell_add")
public class Add {

    public Add(){}

    public Add(long id, String description, float price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private long id;

    @Column( name = "description", nullable = false )
    private String description;

    @Column( name = "price", nullable = false )
    private float price;

    @ManyToOne( fetch = FetchType.LAZY)
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private PlayCard playCard;

    @OneToMany( mappedBy = "sellingAdd",fetch = FetchType.LAZY )
    private Set<Auction> auctions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PlayCard getPlayCard() {
        return playCard;
    }

    public void setPlayCard(PlayCard playCard) {
        this.playCard = playCard;
    }

    public Set<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(Set<Auction> auctions) {
        this.auctions = auctions;
    }
}
