package Demo.Service;

import Demo.Model.User;
import Demo.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser() {
        //stage

        //act
        Optional<User> r1 = userService.createUser("Daniel", "test@test.com", "test1234");
        Optional<User> r2 = userService.createUser(null, "test@test.com", "test1234");
        Optional<User> r3 = userService.createUser("Daniel", null, "test1234");
        Optional<User> r4 = userService.createUser("Daniel", "test@test.com", null);

        //assert
        assertTrue( r2.isEmpty() );
        assertTrue( r3.isEmpty() );
        assertTrue( r4.isEmpty() );

        assertTrue( r1.isPresent() );
        assertEquals( r1.get().getEmail(), "test@test.com" );
        assertEquals( r1.get().getName(), "Daniel" );
        assertEquals( r1.get().getPassword(), "test1234" );
    }
}