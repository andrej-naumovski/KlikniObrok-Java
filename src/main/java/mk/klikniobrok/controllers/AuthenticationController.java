package mk.klikniobrok.controllers;

import mk.klikniobrok.models.Customer;
import mk.klikniobrok.models.JwtResponse;
import mk.klikniobrok.models.ResponseStatus;
import mk.klikniobrok.models.User;
import mk.klikniobrok.services.AuthenticationService;
import mk.klikniobrok.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by andrejnaumovski on 12/8/16.
 */

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {
    private AuthenticationService authenticationService;
    private UserService userService;

    @Autowired
    public AuthenticationController(
            AuthenticationService authenticationService,
            UserService userService
    ) {
        if(authenticationService == null) {
            throw new IllegalArgumentException(AuthenticationService.class.getName() + " cannot be null.");
        }
        if(userService == null) {
            throw new IllegalArgumentException(UserService.class.getName() + " cannot be null.");
        }
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<JwtResponse> login(@RequestParam String username, @RequestParam String password) {
        JwtResponse loginResponse = authenticationService.login(username, password);
        ResponseEntity response;
        if(loginResponse.getResponseStatus().equals(ResponseStatus.SUCCESS)) {
            response = new ResponseEntity<JwtResponse>(loginResponse, HttpStatus.OK);
        } else {
            response = new ResponseEntity<JwtResponse>(loginResponse, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody Customer user) {
        ResponseEntity responseEntity;
        User registeredUser = authenticationService.registerUser(user);
        if(registeredUser != null) {
            responseEntity = new ResponseEntity<User>(registeredUser, HttpStatus.CREATED);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/fb", method = RequestMethod.POST)
    public ResponseEntity facebookLogin(@RequestParam String email, @RequestParam String tag) {
        JwtResponse response;
        if(tag == null || email == null) {
            return new ResponseEntity<String>("Bad request.", HttpStatus.BAD_REQUEST);
        }
        if(!tag.equalsIgnoreCase("fb")) {
            return new ResponseEntity<String>("Invalid request.", HttpStatus.BAD_REQUEST);
        }
        User user = userService.findByEmail(email);
        if(user != null) {
            response = authenticationService.loginFacebook(email);
            return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);
        }
        response = new JwtResponse(null, ResponseStatus.FAILURE);
        return new ResponseEntity<JwtResponse>(response, HttpStatus.OK);
    }
}
