package Demo.Controller;

import Demo.Model.Chat;
import Demo.Service.ChatService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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

@WebMvcTest( controllers = ChatController.class )
class ChatControllerTest {

    @MockBean
    private ChatService chatService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void getChats() throws Exception {
        when( chatService.findAll() ).thenReturn( new ArrayList<Chat>() );
        mockMvc
            .perform( get("/api/chat") )
            .andExpect( status().isNoContent() );

        List<Chat> chatList = new ArrayList<Chat>();
        chatList.add( new Chat() );
        when( chatService.findAll() ).thenReturn( chatList );
        mockMvc
                .perform( get("/api/chat") )
                .andExpect( status().isOk() );

    }

    @Test
    void addChat() throws Exception {
        when( chatService.createChat( any(Long.class), any(Long.class), any() ) ).thenReturn( Optional.empty() );
        mockMvc
            .perform( post("/api/chat")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content("{\n" +
                        "    \"senderId\" : 2,\n" +
                        "    \"receiverId\" : 11,\n" +
                        "    \"message\" : \"I love pizza\"\n" +
                        "}")
            )
            .andExpect( status().isBadRequest() );
        when( chatService.createChat( any(Long.class), any(Long.class), any() ) ).thenReturn(Optional.of(new Chat()));
        mockMvc
                .perform( post("/api/chat")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\n" +
                                "    \"senderId\" : 2,\n" +
                                "    \"receiverId\" : 11,\n" +
                                "    \"message\" : \"I love pizza\"\n" +
                                "}")
                )
                .andExpect( status().isOk() );
    }

    @Test
    void deleteChat() throws Exception {
        mockMvc
            .perform( delete("/api/chat/{id}",1) )
            .andExpect( status().isOk() );

        doThrow( RuntimeException.class ).when( chatService ).deleteById( any(Long.class) );
        mockMvc
                .perform( delete("/api/chat/{id}",1) )
                .andExpect( status().isBadRequest() );
    }
}