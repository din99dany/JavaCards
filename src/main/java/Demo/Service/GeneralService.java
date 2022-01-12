package Demo.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public abstract class GeneralService<T>{

    public List<T> findAll() {
        JpaRepository<T,Long> repo = getRepo();

        return repo.findAll();
    }

    public Optional<T> findById(long id ) {
        JpaRepository<T,Long> repo = getRepo();

        return repo.findById( id );
    }

    public void saveObject( T user ) {
        JpaRepository<T,Long> repo = getRepo();

        getRepo().save( user );
    }

    public void deleteById( long id ) {
        JpaRepository<T,Long> repo = getRepo();

        getRepo().deleteById( id );
    }

    public void updateObject( T user ) {
        JpaRepository<T,Long> repo = getRepo();

        getRepo().save( user );
    }

    public abstract JpaRepository<T, Long> getRepo();


}
