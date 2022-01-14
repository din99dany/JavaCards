package Demo.Controller;

import Demo.Model.Add;
import Demo.Model.PlayCard;
import Demo.Service.PlayCardService;
import ch.qos.logback.core.encoder.EchoEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class PlayCardController {

    @Autowired
    private PlayCardService playCardService;

    @GetMapping("/play_card")
    public ResponseEntity<Iterable<PlayCard>> getSearch( @RequestParam( required = false ) String search) {
        return ResponseEntity.ok( playCardService.getSearch( search ) );
    }

    @GetMapping("/play_card/{id}")
    public ResponseEntity<Iterable<Add>> getAdds( @PathVariable long id ) {
        Optional<PlayCard> playCard = playCardService.findById( id );
        if ( playCard.isPresent() ) {
            return ResponseEntity.ok( playCard.get().getAdds() );
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/play_card")
    public ResponseEntity<Void> saveCard( @RequestParam String name,
                                          @RequestParam String role,
                                          @RequestParam String collection ) {

        Optional<PlayCard> playCard = playCardService.createCard( name, role, collection );
        if ( playCard.isPresent() ) {
            try {
                playCardService.saveObject( playCard.get() );
                return ResponseEntity.ok().build();
            } catch ( RuntimeException e ) {
                System.out.println( e.getMessage() );
                return ResponseEntity.internalServerError().build();
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/play_card/{id}")
    public ResponseEntity<Void> deleteCard( @PathVariable long id ) {

        if ( playCardService.findById( id ).isPresent() ) {
            try {
                playCardService.deleteById(id);
                return  ResponseEntity.ok().build();
            } catch ( Exception e ) {
                System.out.println( e.getMessage() );
                return ResponseEntity.internalServerError().build();
            }
        }

        return ResponseEntity.notFound().build();
    }


}
