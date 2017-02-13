package mk.klikniobrok.repositories;

import mk.klikniobrok.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by andrejnaumovski on 12/9/16.
 */

@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {
    Customer findByUsername(String username);
    Customer findByEmail(String email);
}
