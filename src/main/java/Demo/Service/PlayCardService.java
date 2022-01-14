package Demo.Service;

import Demo.Model.PlayCard;
import Demo.Repository.PlayCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PlayCardService extends GeneralService<PlayCard> {

    @Autowired
    private PlayCardRepository playCardRepository;

    public Optional<PlayCard> createCard( String name, String role, String collection ) {
        if ( name == null || role == null || collection == null ) {
            return Optional.empty();
        }

        PlayCard newPlayCard = new PlayCard();
        newPlayCard.setName( name );
        newPlayCard.setRole( role );
        newPlayCard.setCollection( collection);

        return Optional.of(newPlayCard);
    }

    public List<PlayCard> getSearch(String toSearch ) {
        List<PlayCard> cards = findAll();

        if ( toSearch == null ) {
            return cards;
        }

        return  cards.stream()
                    .filter( playCard -> playCard.getName().contains( toSearch ) ||
                            playCard.getCollection().contains( toSearch ) )
                    .collect( Collectors.toList() );
    }

    @Override
    public JpaRepository<PlayCard, Long> getRepo() {
        return  playCardRepository;
    }
}
