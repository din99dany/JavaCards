package Demo.Repository;

import Demo.Model.PlayCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayCardRepository extends JpaRepository<PlayCard, Long> {

}
