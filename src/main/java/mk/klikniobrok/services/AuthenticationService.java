package mk.klikniobrok.services;

import mk.klikniobrok.models.Customer;
import mk.klikniobrok.models.JwtResponse;
import mk.klikniobrok.models.User;

/**
 * Created by andrejnaumovski on 12/8/16.
 */
public interface AuthenticationService {
    JwtResponse login(String username, String password);
    User registerUser(User user);
    boolean isUserValid(String token);
}
