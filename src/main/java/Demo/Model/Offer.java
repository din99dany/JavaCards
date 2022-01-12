package Demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "offer")
public class Offer {

    public Offer(){}

    public Offer(long id, Double amount, User user) {
        this.id = id;
        this.amount = amount;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Double amount;

    @ManyToOne
    private User user;

    @JsonIgnore
    @ManyToOne
    private Auction sellingAdd;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Auction getSellingAdd() {
        return sellingAdd;
    }

    public void setSellingAdd(Auction sellingAdd) {
        this.sellingAdd = sellingAdd;
    }
}
