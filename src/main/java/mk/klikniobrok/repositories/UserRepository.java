package mk.klikniobrok.repositories;

import mk.klikniobrok.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by andrejnaumovski on 12/8/16.
 */

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    User findByUsername(String username);
}
