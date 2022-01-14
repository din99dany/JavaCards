package Demo.Controller;

import Demo.Model.User;
import Demo.Service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<User>> getUsers() {

        List<User> users = userService.findAll();

        if (!users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping( path = "/users", consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Long> addUser(@RequestBody ObjectNode json ) {

        Optional<User> newUser = userService.createUser(
                    json.get("name").asText(),
                    json.get("email").asText(),
                    json.get("password").asText()
        );

        if ( newUser.isPresent() ) {
            try {
                userService.saveObject( newUser.get() );
                return ResponseEntity.ok(newUser.get().getId());
            } catch ( Exception e ) {
                System.out.println( e.getMessage() );
                return ResponseEntity.internalServerError().build();
            }
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping( path = "/users/{id}")
    public ResponseEntity<Void> deleteUser( @PathVariable long id ) {
        try {
            if (userService.findById(id).isPresent()) {
                userService.deleteById(id);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e ) {
            System.out.println( e.getMessage() );
            return ResponseEntity.internalServerError().build();
        }
    }

}
