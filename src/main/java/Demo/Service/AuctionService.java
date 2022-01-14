package Demo.Service;

import Demo.Model.Add;
import Demo.Model.Auction;
import Demo.Repository.AddRepository;
import Demo.Repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class AuctionService extends GeneralService<Auction>{

    @Autowired
    private AddRepository addRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    public void promoteAddToAuction( Add add ) {

        if( add == null ) {
            throw new RuntimeException("Null add");
        }

        if ( addRepository.findById( add.getId() ).isEmpty() ) {
            throw new RuntimeException("Bad add");
        }

        Auction auction = new Auction();

        auction.setStartDate( new Date() );
        auction.setEndDate( new Date() );
        auction.setStartingPrice( add.getPrice() );
        auction.setSellingAdd( add );

        auctionRepository.save( auction );
    }

    @Override
    public JpaRepository<Auction, Long> getRepo() {
        return auctionRepository;
    }
}
