package mk.klikniobrok.services.impl;

import io.jsonwebtoken.Jwts;
import mk.klikniobrok.models.JwtResponse;
import mk.klikniobrok.models.ResponseStatus;
import mk.klikniobrok.models.User;
import mk.klikniobrok.services.AuthenticationService;
import mk.klikniobrok.services.SecurityService;
import mk.klikniobrok.services.UserService;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by andrejnaumovski on 12/8/16.
 */

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private SecurityService securityService;
    private UserService userService;
    private Logger logger = Logger.getLogger("AuthenticationServiceImpl");

    @Autowired
    public AuthenticationServiceImpl(SecurityService securityService, UserService userService) {
        if(securityService == null) {
            throw new IllegalArgumentException(SecurityService.class.getName() + " cannot be null.");
        }
        if(userService == null) {
            throw new IllegalArgumentException(UserService.class.getName() + " cannot be null.");
        }
        this.securityService = securityService;
        this.userService = userService;
    }

    @Override
    public JwtResponse login(String username, String password) {
        User user = userService.findByUsername(username);
        JwtResponse response = new JwtResponse(null, ResponseStatus.FAILURE);
        if(user != null) {
            String[] storedPassword = user.getPassword().split("\\.");
            String hash = securityService.hashPassword(password, storedPassword[1]);
            String hashedPassword = hash.split("\\.")[0];
            if(hashedPassword.equals(storedPassword[0])) {
                String token = Jwts
                        .builder()
                        .claim("user", user)
                        .setSubject(user.getUsername())
                        .setIssuedAt(new Date())
                        .compact();
                response.setToken(token);
                response.setResponseStatus(ResponseStatus.SUCCESS);
            }
        }
        return response;
    }

    @Override
    public User registerUser(User user) {
        return userService.registerUser(user);
    }
}

