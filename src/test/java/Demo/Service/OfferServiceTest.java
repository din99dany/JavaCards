package Demo.Service;

import Demo.Model.Auction;
import Demo.Model.Offer;
import Demo.Model.User;
import Demo.Repository.AuctionRepository;
import Demo.Repository.OfferRepository;
import Demo.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuctionRepository auctionRepository;

    @InjectMocks
    private OfferService offerService;

    @Test
    void createOffer_noUsers() {
        //stage
        when( userRepository.findById( any() ) ).thenReturn( Optional.empty() );
        when( auctionRepository.findById( any() ) ).thenReturn( Optional.empty() );

        //act
        Optional<Offer> offer = offerService.createOffer( 1, 2 , 10 );
        Optional<Offer> offer_negative = offerService.createOffer( 1, 2 , -2 );

        //assert
        assertTrue( offer.isEmpty() );
        assertTrue( offer_negative.isEmpty() );
    }

    @Test
    void createOffer_offer() {
        //stage
        Auction auction = new Auction();
        auction.setOfferSet( new HashSet<Offer>() );

        when( userRepository.findById( any() ) ).thenReturn( Optional.of( new User() ) );
        when( auctionRepository.findById( any() ) ).thenReturn( Optional.of( auction ) );
        //act

        Optional<Offer> offer1 = offerService.createOffer( 1, 2, 10 );

        //assert
        assertTrue( offer1.isPresent() );
    }

    @Test
    void createOffer_offer_range() {
        //stage
        Auction auction = new Auction();
        Set<Offer> offerSet = new HashSet<Offer>();
        offerSet.add( new Offer(1L, 10.0, new User() ) );
        auction.setOfferSet( offerSet );

        when( userRepository.findById( any() ) ).thenReturn( Optional.of( new User() ) );
        when( auctionRepository.findById( any() ) ).thenReturn( Optional.of( auction ) );
        //act

        Optional<Offer> offer1 = offerService.createOffer( 1, 2, 10 );

        //assert
        assertTrue( offer1.isEmpty() );
    }

}