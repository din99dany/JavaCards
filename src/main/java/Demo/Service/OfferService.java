package Demo.Service;

import Demo.Model.Auction;
import Demo.Model.Offer;
import Demo.Model.User;
import Demo.Repository.AuctionRepository;
import Demo.Repository.OfferRepository;
import Demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OfferService extends GeneralService<Offer>{

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    public Optional<Offer>  createOffer( long auctionId, long sellerId, double price ) {

        if ( price < 0 ) {
            return Optional.empty();
        }

        Optional<Auction> auction = auctionRepository.findById( auctionId );
        Optional<User> user = userRepository.findById(sellerId);

        if ( auction.isPresent() && user.isPresent() ) {
            Optional<Offer> last_price = auction.get().getOfferSet().stream().max((o1, o2) -> (int)Math.round(o1.getAmount() - o2.getAmount())  );

            if ( last_price.isPresent() ) {
                if ( price < last_price.get().getAmount() * 1.05 ) {
                    return  Optional.empty();
                }
            }

            Offer offer = new Offer();
            offer.setAmount(price);
            offer.setSellingAdd(auction.get());
            offer.setUser(user.get());
            return Optional.of(offer);
        }
        return Optional.empty();
    }

    @Override
    public JpaRepository<Offer, Long> getRepo() {
        return offerRepository;
    }
}
