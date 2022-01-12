package Demo.Controller;

import Demo.Model.Add;
import Demo.Model.Auction;
import Demo.Model.Offer;
import Demo.Service.AddService;
import Demo.Service.AuctionService;
import Demo.Service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TradeController {

    @Autowired
    private AddService addService;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private OfferService offerService;

    @GetMapping("/sell_add")
    public ResponseEntity<Iterable<Add>> getAdds() {
        return ResponseEntity.ok( addService.findAll() );
    }

    @PostMapping("/sell_add")
    public ResponseEntity<Long> addSellingAdd(
            @RequestParam long sellerId,
            @RequestParam long cardId,
            @RequestParam float price,
            @RequestParam String description
            ) {
        Optional<Add> add = addService.createSellingAdd( sellerId, cardId, price, description );

        try {
            if (add.isPresent()) {
                addService.saveObject(add.get());
                return ResponseEntity.ok(add.get().getId());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e ) {
            System.out.println( e.getMessage() );
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/sell_add/{id}")
    public  ResponseEntity<Void> deleteSellAdd( @PathVariable long id ) {

        if ( addService.findById( id ).isPresent() ) {
            addService.deleteById( id );
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }


    @GetMapping("/auction")
    public ResponseEntity<Iterable<Auction>> getAuctions() {
        return ResponseEntity.ok( auctionService.findAll() );
    }

    @PostMapping("/auction")
    public ResponseEntity<Void> createAuction( @RequestHeader Long xAddId ) {
        Optional<Add> add = addService.findById( xAddId );

        if ( add.isPresent() ) {
            auctionService.promoteAddToAuction( add.get() );
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/auction/{id}")
    public ResponseEntity<Void> deleteById( @PathVariable long id ) {

        if ( auctionService.findById( id ).isPresent() ) {
            auctionService.deleteById(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/auction/{id}")
    public ResponseEntity<Iterable<Offer>> getOffers( @PathVariable long id ) {
        if ( auctionService.findById(id).isPresent() ) {
            return ResponseEntity.ok( auctionService.findById(id).get().getOfferSet() );
        }
        return  ResponseEntity.notFound().build();
    }

    @PostMapping("/auction/{id}")
    public ResponseEntity<Void> createOffer( @RequestParam long offererId,
                                             @RequestParam double price,
                                             @PathVariable long id ) {

        if ( auctionService.findById(id).isPresent() ) {
            Optional<Offer> offer =  offerService.createOffer( id, offererId, price );
            if ( offer.isPresent() ) {
                offerService.saveObject( offer.get() );
                return ResponseEntity.ok().build();
            }
        }

        return ResponseEntity.notFound().build();
    }

}
