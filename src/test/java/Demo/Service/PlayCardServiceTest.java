package Demo.Service;

import Demo.Model.PlayCard;
import Demo.Model.User;
import Demo.Repository.PlayCardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayCardServiceTest {

    @Mock
    private PlayCardRepository playCardRepository;

    @InjectMocks
    private PlayCardService playCardService;

    @Test
    void createCard() {
        //stage
        Optional<PlayCard> r1 = playCardService.createCard("Pikatchu", "attacker", "1998 starters");
        Optional<PlayCard> r2 = playCardService.createCard(null, "attacker", "1998 starters");
        Optional<PlayCard> r3 = playCardService.createCard("Pikatchu", null, "1998 starters");
        Optional<PlayCard> r4 = playCardService.createCard("Pikatchu", "attacker", null);

        //assert
        assertTrue( r2.isEmpty() );
        assertTrue( r3.isEmpty() );
        assertTrue( r4.isEmpty() );

        assertTrue( r1.isPresent() );
        assertEquals( r1.get().getCollection(), "1998 starters" );
        assertEquals( r1.get().getName(), "Pikatchu" );
        assertEquals( r1.get().getRole(), "attacker" );
    }

    @Test
    void getSearch() {
        List<PlayCard> playCards = new ArrayList<PlayCard>();
        playCards.add( new PlayCard( 1, "Pikatchu", "Attacker", "1998 starters" ) );
        playCards.add( new PlayCard( 2, "Pikatchu2", "Bttacker", "1997 starters" ) );
        playCards.add( new PlayCard( 3, "Pikatchu3", "Cttacker", "1996 starters" ) );

        //stage
        when( playCardRepository.findAll() ).thenReturn( playCards );

        //act
        List<PlayCard> newPlayCardsAll = playCardService.getSearch(null );
        List<PlayCard> newPlayCard98    = playCardService.getSearch( "98" );
        List<PlayCard> newPlayCard199    = playCardService.getSearch( "199" );

        //assert
        assertEquals( newPlayCardsAll.hashCode(), playCards.hashCode() );
        assertEquals( newPlayCard98.size(), 1 );
        assertEquals( newPlayCard199.size(), 3 );
    }
}