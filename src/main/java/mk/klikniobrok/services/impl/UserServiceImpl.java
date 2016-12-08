package mk.klikniobrok.services.impl;

import mk.klikniobrok.models.User;
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

    private SecurityService securityService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, SecurityService securityService) {
        if(userRepository == null) {
            throw new IllegalArgumentException(UserRepository.class.getName() + " cannot be null.");
        }
        if(securityService == null) {
            throw new IllegalArgumentException(SecurityService.class.getName() + " cannot be null.");
        }
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User registerUser(User user) {
        boolean alreadyExists = userRepository.findByUsername(user.getUsername()) != null;
        if(alreadyExists) {
            return null;
        }
        user.setDateCreated(new Timestamp(System.currentTimeMillis()));
        user.setLastUsed(new Timestamp(System.currentTimeMillis()));
        user.setPassword(securityService.hashPassword(user.getPassword(), securityService.generateRandomSalt()));
        userRepository.save(user);
        return userRepository.findByUsername(user.getUsername());
    }
}
