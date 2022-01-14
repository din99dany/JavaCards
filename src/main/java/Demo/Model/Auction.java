package Demo.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Auction {

    public Auction() {}

    public Auction(long id, float startingPrice, Date startDate, Date endDate) {
        this.id = id;
        this.startingPrice = startingPrice;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "starting_price", nullable = false)
    private float startingPrice;

    @Column( name = "start_date", nullable = false )
    private Date  startDate;

    @Column( name = "end_date", nullable = false )
    private Date endDate;

    @OneToMany( mappedBy = "sellingAdd", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<Offer> offerSet;

    @ManyToOne
    private Add sellingAdd;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getStartingPrice() {
        return startingPrice;
    }

    public void setStartingPrice(float startingPrice) {
        this.startingPrice = startingPrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Set<Offer> getOfferSet() {
        return offerSet;
    }

    public void setOfferSet(Set<Offer> offerSet) {
        this.offerSet = offerSet;
    }

    public Add getSellingAdd() {
        return sellingAdd;
    }

    public void setSellingAdd(Add sellingAdd) {
        this.sellingAdd = sellingAdd;
    }
}
