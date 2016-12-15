package mk.klikniobrok.repositories;

import mk.klikniobrok.models.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by andrej on 12/10/16.
 */

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

}
