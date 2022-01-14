package Demo.Service;

import Demo.Model.User;
import Demo.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeneralServiceTest {

    private final UserRepository userRepository = null;

    @InjectMocks
    private UserService generalService;

    @Test
    void findAll_throw() {
        assertThrows( Exception.class, () -> generalService.findAll() );
    }

    @Test
    void findById_throw() {
        assertThrows( Exception.class, () -> generalService.findById(1) );
    }

    @Test
    void saveObject_throw() {
        assertThrows( Exception.class, () -> generalService.saveObject( new User() ) );
    }

    @Test
    void deleteById_throw() {
        assertThrows( Exception.class, () -> generalService.deleteById(1) );
    }

    @Test
    void updateObject_throw() {
        assertThrows( Exception.class, () -> generalService.updateObject( new User() ) );
    }

}