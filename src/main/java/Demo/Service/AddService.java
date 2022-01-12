package Demo.Service;

import Demo.Model.Add;
import Demo.Model.PlayCard;
import Demo.Model.User;
import Demo.Repository.AddRepository;
import Demo.Repository.PlayCardRepository;
import Demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AddService extends GeneralService<Add> {

    @Autowired
    private AddRepository addRepository;

    @Autowired
    private PlayCardRepository playCardRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<Add> createSellingAdd( long userId, long cardId, float price, String description ) {
        if ( description == null || price < 0.0 ){
            return Optional.empty();
        }

        Optional<User> user = userRepository.findById( userId );
        Optional<PlayCard> playCard = playCardRepository.findById( cardId );

        if ( user.isPresent() && playCard.isPresent() ) {

            Add add = new Add();

            add.setPrice( price );
            add.setDescription( description );
            add.setUser( user.get() );
            add.setPlayCard( playCard.get() );

            return Optional.of( add );
        }

        return Optional.empty();
    }

    public Boolean verify( Add add ) {
        if ( add == null ) {
            return false;
        }

        return userRepository.findById(add.getUser().getId()).isPresent() &&
                playCardRepository.findById(add.getPlayCard().getId()).isPresent() &&
                add.getDescription() != null  &&
                add.getPrice() > 0.0;
    }

    @Override
    public JpaRepository<Add, Long> getRepo() {
        return addRepository;
    }
}
