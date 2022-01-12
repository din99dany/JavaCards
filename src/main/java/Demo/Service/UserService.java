package Demo.Service;

import Demo.Model.User;
import Demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends GeneralService<User>{

    @Autowired
    private UserRepository userRepository;

    public Optional<User> createUser( String name, String email, String password ) {

        if ( name == null || email == null || password == null ) {
            return Optional.empty();
        }

        User newUser = new User();

        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);

        return Optional.of(newUser);
    }

    @Override
    public JpaRepository<User, Long> getRepo() {
        return userRepository;
    }
}

