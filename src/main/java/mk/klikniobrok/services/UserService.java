package mk.klikniobrok.services;

import mk.klikniobrok.models.Customer;
import mk.klikniobrok.models.User;

/**
 * Created by andrejnaumovski on 12/8/16.
 */
public interface UserService {
    User findByUsername(String username);
    User registerUser(User user);
    User getUserDetails(String username);
}
