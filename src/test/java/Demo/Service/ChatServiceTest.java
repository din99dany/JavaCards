package Demo.Service;

import Demo.Model.Chat;
import Demo.Model.User;
import Demo.Repository.ChatRepository;
import Demo.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;

import static java.util.function.Predicate.not;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ChatService chatService;

    @Test
    void verifyChat() {
        //stage
        Chat chat = new Chat();
        when( userRepository.findById( any() ) ).thenReturn( Optional.empty() );

        //act
        boolean ret1 = chatService.verifyChat( chat );

        chat.setMessage("Test");
        chat.setReceiver( new User() );
        chat.setSender( new User());
        chat.setSendTime( new Date() );
        boolean ret2 = chatService.verifyChat( chat );

        when( userRepository.findById( any() ) ).thenReturn( Optional.of(new User()) );
        boolean ret3 = chatService.verifyChat( chat );

        //assert
        assertFalse( ret1 );
        assertFalse( ret2 );
        assertTrue( ret3 );
    }

    @Test
    void createChat() {
        //stage
        User user1 = new User( 1, "Daniel", "test@test.com", "test1");
        User user2 = new User( 2, "George", "test@test.com", "test2");

        when( userRepository.findById(1L) ).thenReturn( Optional.of(user1) );
        when( userRepository.findById(2L) ).thenReturn( Optional.of(user2) );

        Optional<Chat> chat1 = chatService.createChat( 1, 2, "Test");
        Optional<Chat> chat2 = chatService.createChat( 1, 2, null );

        when( userRepository.findById(any()) ).thenReturn( Optional.empty() );
        Optional<Chat> chat3 = chatService.createChat( 1, 2, "Test" );

        //assert
        assertTrue( chat1.isPresent() );
        assertEquals( chat1.get().getMessage(), "Test" );
        assertEquals( chat1.get().getSender().hashCode(), user1.hashCode() );

        assertTrue( chat2.isEmpty() );
        assertTrue( chat3.isEmpty() );
    }



}