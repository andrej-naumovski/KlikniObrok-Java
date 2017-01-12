package mk.klikniobrok.services.impl;

import mk.klikniobrok.models.Customer;
import mk.klikniobrok.models.User;
import mk.klikniobrok.repositories.CustomerRepository;
import mk.klikniobrok.repositories.UserRepository;
import mk.klikniobrok.services.SecurityService;
import mk.klikniobrok.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by andrejnaumovski on 12/8/16.
 */

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private CustomerRepository customerRepository;
    private SecurityService securityService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CustomerRepository customerRepository, SecurityService securityService) {
        if(userRepository == null) {
            throw new IllegalArgumentException(UserRepository.class.getName() + " cannot be null.");
        }
        if(securityService == null) {
            throw new IllegalArgumentException(SecurityService.class.getName() + " cannot be null.");
        }
        if(customerRepository == null) {
            throw new IllegalArgumentException(CustomerRepository.class.getName() + " cannot be null.");
        }
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.securityService = securityService;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUserDetails(String username) {
        return customerRepository.findByUsername(username);
    }


    /*
     * Registers a new user, sets its dateCreated and lastUsed
     * properties to current time, hashes password using the securityService service,
     * and stores it in database.
     * @param user: User - The user to be registered.
     * @return User - The newly generated user, with proper dates and ID.
     */
    @Override
    public User registerUser(User user) {
        boolean alreadyExists = userRepository.findByUsername(user.getUsername()) != null;
        if(alreadyExists) {
            return null;
        }
        user.setDateCreated(new Timestamp(System.currentTimeMillis()));
        user.setLastUsed(new Timestamp(System.currentTimeMillis()));
        user.setPassword(securityService.hashPassword(user.getPassword(), securityService.generateRandomSalt()));
        user.setEnabled(1);
        customerRepository.save((Customer) user);
        return customerRepository.findByUsername(user.getUsername());
    }
}
