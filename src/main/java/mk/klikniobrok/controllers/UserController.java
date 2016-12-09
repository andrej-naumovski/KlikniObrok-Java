package mk.klikniobrok.controllers;

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
    public ResponseEntity getUserDetails(@PathVariable String username) {
//        String jwtToken = authorizationHeader.split(" ")[1];
//        User user = authenticationService.authenticateUser(jwtToken);
//        if(user == null) {
//            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//        }
        Customer customer = (Customer) userService.getUserDetails(username);
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }
}
