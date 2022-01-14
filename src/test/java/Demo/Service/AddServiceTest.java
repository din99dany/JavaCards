package Demo.Service;

import Demo.Model.Add;
import Demo.Model.PlayCard;
import Demo.Model.User;
import Demo.Repository.AddRepository;
import Demo.Repository.PlayCardRepository;
import Demo.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddServiceTest {

    @Mock
    private AddRepository addRepository;

    @Mock
    private PlayCardRepository playCardRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddService addService;


    @Test
    void createSellingAdd() {
        //arrange

        User user = new User( 1, "Daniel", "din99dany@gmail.com", "test1234" );
        PlayCard playCard = new PlayCard( 1, "Pikachu", "Attacker", "1998 Starters" );

        when( userRepository.findById( any() ) ).thenReturn( Optional.of(user) );
        when( playCardRepository.findById( any() ) ).thenReturn( Optional.of(playCard) );


        Add add = new Add( 0, "Test card", 10.0F );
        add.setUser( user );
        add.setPlayCard( playCard );

        //act
        Optional<Add> newAdd = addService.createSellingAdd( 1, 1, 10.0F, "Test card");


        //assert
        assertTrue( newAdd.isPresent() );
        assertNull( newAdd.get().getAuctions() );
        assertEquals( add.getPrice(), newAdd.get().getPrice() );
        assertEquals( add.getDescription(), newAdd.get().getDescription() );
        assertEquals( add.getId(), newAdd.get().getId() );

    }

    @Test
    void verify() {
        //arrange
        when( userRepository.findById( any() ) ).thenReturn( Optional.empty() );
        when( playCardRepository.findById( any() ) ).thenReturn( Optional.empty() );

        User user = new User( 1, "Daniel", "din99dany@gmail.com", "test1234" );
        PlayCard playCard = new PlayCard( 1, "Pikachu", "Attacker", "1998 Starters" );


        Add add1 = null;
        Add add2 = new Add( 0, "Test", 10.0F );
        Add add3 = new Add( 0, null, 100.F);
        Add add4 = new Add( 0, "Test", -10F );

        add2.setUser( user );
        add2.setPlayCard( playCard );

        add3.setUser( user );
        add3.setPlayCard( playCard );

        add4.setUser( user );
        add4.setPlayCard( playCard );

        //atc r1 r2
        Boolean r1 = addService.verify( add1 );
        Boolean r2 = addService.verify( add2 );

        //atc r3
        when( userRepository.findById( any() ) ).thenReturn( Optional.of( user ) );
        Boolean r3_1 = addService.verify( add3 );

        when( playCardRepository.findById( any() ) ).thenReturn( Optional.of(playCard) );
        Boolean r3_2 = addService.verify( add3 );

        //act r4
        Boolean r4 = addService.verify( add4 );

        //assert
        assertFalse( r1 );
        assertFalse( r2 );
        assertFalse( r3_1 );
        assertFalse( r3_2 );
        assertFalse( r4 );
    }
}