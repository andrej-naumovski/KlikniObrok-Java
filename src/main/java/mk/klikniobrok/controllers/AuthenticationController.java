package mk.klikniobrok.controllers;

import mk.klikniobrok.models.Customer;
import mk.klikniobrok.models.JwtResponse;
import mk.klikniobrok.models.ResponseStatus;
import mk.klikniobrok.models.User;
import mk.klikniobrok.services.AuthenticationService;
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

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        if(authenticationService == null) {
            throw new IllegalArgumentException(AuthenticationService.class.getName() + " cannot be null.");
        }
        this.authenticationService = authenticationService;
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
    public ResponseEntity register(@RequestBody User user) {
        ResponseEntity responseEntity;
        User registeredUser = authenticationService.registerUser(user);
        if(registeredUser != null) {
            responseEntity = new ResponseEntity<User>(registeredUser, HttpStatus.CREATED);
        } else {
            responseEntity = new ResponseEntity(HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
}
