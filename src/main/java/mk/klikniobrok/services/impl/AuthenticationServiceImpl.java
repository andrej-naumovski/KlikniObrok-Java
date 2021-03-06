package mk.klikniobrok.services.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.impl.crypto.MacProvider;
import mk.klikniobrok.models.Customer;
import mk.klikniobrok.models.JwtResponse;
import mk.klikniobrok.models.ResponseStatus;
import mk.klikniobrok.models.User;
import mk.klikniobrok.services.AuthenticationService;
import mk.klikniobrok.services.SecurityService;
import mk.klikniobrok.services.UserService;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
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
    private Key key = MacProvider.generateKey();

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


    /*
     * Checks if username and password are valid
     * and returns a JWT Token for the specific user.
     *
     * @return response: JwtResponse - A wrapper object for the
     * JWT token, contains the token and the operation's status.
     *
     * @note Should reimplement with Spring Security?
     */
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
                        .signWith(SignatureAlgorithm.HS256, key)
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

    /*
     * Tries to decode the JWT token and returns the
     * user associated with it.
     *
     * @param jwtToken: String - the request JWT token
     * @return user: User - returns the user if token is valid, else returns null
     */
    private User authenticateUser(String jwtToken) {
        User user;
        try {
            String username = Jwts.parser().setSigningKey(key).parseClaimsJws(jwtToken).getBody().getSubject();
            user = userService.getUserDetails(username);
        } catch(SignatureException e) {
            return null;
        }
        return user;
    }

    /*
     * Checks if the JWT token comes from a valid user.
     *
     * @param token: String - The request JWT token.
     * @return boolean - returns true if token
     * contains valid user info, false otherwise.
     */
    @Override
    public boolean isUserValid(String token) {
        User user;
        try {
            user = authenticateUser(token);
        } catch (MalformedJwtException exception) {
            return false;
        }
        return user != null;
    }

    @Override
    public JwtResponse loginFacebook(String email) {
        User user = userService.findByEmail(email);
        String token = Jwts
                .builder()
                .claim("user", user)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
        return new JwtResponse(token, ResponseStatus.SUCCESS);
    }
}

