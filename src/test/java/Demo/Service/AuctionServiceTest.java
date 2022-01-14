package Demo.Service;

import Demo.Model.Add;
import Demo.Model.Auction;
import Demo.Model.PlayCard;
import Demo.Model.User;
import Demo.Repository.AddRepository;
import Demo.Repository.AuctionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {

    @Mock
    private AddRepository addRepository;

    @Mock
    private AuctionRepository auctionRepository;

    @InjectMocks
    private AuctionService auctionService;

    @Test
    void promoteAddToAuction_null() {
        //stage
        Add add = new Add();
        add.setPlayCard( new PlayCard() );
        add.setUser( new User() );
        add.setPrice( 10 );
        add.setDescription("Test");
        when( addRepository.findById(any()) ).thenReturn( Optional.empty() );

        assertThrows( RuntimeException.class, () -> auctionService.promoteAddToAuction(add) );
        assertThrows( RuntimeException.class, () -> auctionService.promoteAddToAuction(null) );


        when( addRepository.findById(any()) ).thenReturn( Optional.of(add) );
        when( auctionRepository.save( any()) ).thenReturn( new Auction() );

        assertDoesNotThrow(() -> auctionService.promoteAddToAuction( add ));

    }
}