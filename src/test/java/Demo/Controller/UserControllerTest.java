package Demo.Controller;

import Demo.Model.User;
import Demo.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest( controllers = UserController.class )
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUsersEmpty() throws Exception {
        when( userService.findAll() ).thenReturn( new ArrayList<User>() );

        mockMvc
                .perform( get("/api/users") )
                .andExpect( status().isNoContent() );
    }

    @Test
    void getUsersUsers() throws Exception {
        List<User> users = new ArrayList<User>();
        users.add( new User() );

        when( userService.findAll() ).thenReturn( users );

        mockMvc
                .perform( get("/api/users") )
                .andExpect( status().isOk() )
                .andReturn();
    }

    @Test
    void addUserOk() throws Exception {
        when( userService.createUser( any(), any(), any() ) )
                .thenReturn(
                        Optional.of(
                                new User(0, "Daniel", "test@teset", "test")
                        )
                );

        mockMvc
            .perform(
                    post("/api/users")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content("{\n" +
                                    "    \"name\" : \"Matei George\",\n" +
                                    "    \"email\": \"din33dany@gmail.com\",\n" +
                                    "    \"password\": \"test1234\"\n" +
                                    "}")
            )
            .andExpect( status().isOk() );
    }

    @Test
    void addUserNotOk() throws Exception {
        when(userService.createUser( any(), any(), any() ) ).thenReturn( Optional.empty() );

        mockMvc
            .perform(
                    post("/api/users")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content("{\n" +
                                    "    \"name\" : \"Matei George\",\n" +
                                    "    \"email\": \"din33dany@gmail.com\",\n" +
                                    "    \"password\": \"test1234\"\n" +
                                    "}")
            )
            .andExpect( status().isNoContent() );
    }

    @Test
    void addUserNotOkRepo() throws Exception {
        when( userService.createUser( any(), any(), any() ) )
                .thenReturn(
                        Optional.of(
                                new User(0, "Daniel", "test@teset", "test")
                        )
                );

        doThrow( RuntimeException.class ).when( userService ).saveObject( any() );

        mockMvc
            .perform(
                    post("/api/users")
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content("{\n" +
                                    "    \"name\" : \"Matei George\",\n" +
                                    "    \"email\": \"din33dany@gmail.com\",\n" +
                                    "    \"password\": \"test1234\"\n" +
                                    "}")
            )
            .andExpect( status().isInternalServerError() );
    }

    @Test
    void deleteUser() throws Exception {
        when( userService.findById( any(Long.class) ) ).thenReturn( Optional.empty() );

        mockMvc
            .perform( delete("/api/users/{id}",1) )
            .andExpect( status().isNotFound() );

    }

    @Test
    void deleteUserOk() throws Exception {
        when( userService.findById( any(Long.class) ) ).thenReturn( Optional.of(new User()) );

        mockMvc
            .perform(delete("/api/users/{id}",1) )
            .andExpect( status().isOk() );
    }

}