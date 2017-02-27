package mk.klikniobrok.controllers;

import io.jsonwebtoken.MalformedJwtException;
import mk.klikniobrok.models.Customer;
import mk.klikniobrok.models.User;
import mk.klikniobrok.services.AuthenticationService;
import mk.klikniobrok.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by andrejnaumovski on 12/9/16.
 */

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private UserService userService;
    private AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        if(userService == null) {
            throw new IllegalArgumentException(UserService.class.getName() + " cannot be null.");
        }
        if(authenticationService == null) {
            throw new IllegalArgumentException(AuthenticationService.class.getName() + " cannot be null.");
        }
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity getUserDetails(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @PathVariable String username
    ) {
        String jwtToken = authorizationHeader.split(" ")[1];
        if(!authenticationService.isUserValid(jwtToken)) {
            return new ResponseEntity<String>("Unauthorized, access denied.", HttpStatus.UNAUTHORIZED);
        }
        User foundUser = userService.getUserDetails(username);
        if(foundUser == null) {
            return new ResponseEntity<String>("User does not exist.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(foundUser, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity getUserDetailsByEmail(
        @RequestHeader(value = "Authorization") String authorizationHeader,
        @RequestParam String email
    ) {
        String jwtToken = authorizationHeader.split(" ")[1];
        User user;
        if(!authenticationService.isUserValid(jwtToken)) {
            return new ResponseEntity<>("Unauthorized, access denied.", HttpStatus.UNAUTHORIZED);
        }
        user = userService.findByEmail(email);
        if(user == null) {
            return new ResponseEntity<>("User does not exist.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
