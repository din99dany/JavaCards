package Demo.Controller;

import Demo.Model.Chat;
import Demo.Service.ChatService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping(path = "/chat", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Chat>> getChats() {
        List<Chat> chats = chatService.findAll();

        if( !chats.isEmpty() ) {
            return ResponseEntity.ok(chats);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(path = "/chat")
    public ResponseEntity<Void> addChat( @RequestBody ObjectNode json) {

        Optional<Chat> chat = chatService.createChat( json.get("senderId").asLong(),
                json.get("receiverId").asLong(),
                    json.get("message").asText()
        );

        if ( chat.isPresent() ) {
            chatService.saveObject( chat.get() );
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(path = "/chat/{id}")
    public ResponseEntity<Void> deleteChat( @PathVariable long id ) {
        try {
            chatService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch ( Exception e ) {
            System.out.println( e.getMessage() );
            return ResponseEntity.badRequest().build();
        }
    }

}
