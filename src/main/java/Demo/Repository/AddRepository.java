package Demo.Repository;

import Demo.Model.Add;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddRepository extends JpaRepository<Add, Long> {

}
