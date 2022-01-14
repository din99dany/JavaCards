package Demo.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public abstract class GeneralService<T>{

    public List<T> findAll() {
        JpaRepository<T,Long> repo = getRepo();
        if ( repo == null ) {
            throw  new RuntimeException("Bad repo");
        }
        return repo.findAll();
    }

    public Optional<T> findById(long id ) {
        JpaRepository<T,Long> repo = getRepo();
        if ( repo == null ) {
            throw  new RuntimeException("Bad repo");
        }
        return repo.findById( id );
    }

    public void saveObject( T user ) {
        JpaRepository<T,Long> repo = getRepo();
        if ( repo == null ) {
            throw  new RuntimeException("Bad repo");
        }
        getRepo().save( user );
    }

    public void deleteById( long id ) {
        JpaRepository<T,Long> repo = getRepo();
        if ( repo == null ) {
            throw  new RuntimeException("Bad repo");
        }
        getRepo().deleteById( id );
    }

    public void updateObject( T object ) {
        JpaRepository<T,Long> repo = getRepo();
        if( object == null || repo == null ) {
            throw new RuntimeException("Null objects");
        }
        getRepo().save( object );
    }

    public abstract JpaRepository<T, Long> getRepo();


}
