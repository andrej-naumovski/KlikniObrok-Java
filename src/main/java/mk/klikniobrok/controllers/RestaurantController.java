package mk.klikniobrok.controllers;

import mk.klikniobrok.models.Restaurant;
import mk.klikniobrok.services.AuthenticationService;
import mk.klikniobrok.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by andrej on 12/10/16.
 */

@RestController
@RequestMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    private RestaurantService restaurantService;
    private AuthenticationService authenticationService;

    @Autowired
    public RestaurantController(
            RestaurantService restaurantService,
            AuthenticationService authenticationService
    ) {
        if(restaurantService == null) {
            throw new IllegalArgumentException(RestaurantService.class.getName() + " cannot be null.");
        }
        if(authenticationService == null) {
            throw new IllegalArgumentException(AuthenticationService.class.getName() + " cannot be null.");
        }
        this.restaurantService = restaurantService;
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity getAllRestaurants(@RequestHeader(name = "Authorization") String authorizationHeader) {
        if(!authenticationService.isUserValid(authorizationHeader.split(" ")[1])) {
            return new ResponseEntity<String>("Unauthorized, access denied.", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<List<Restaurant>>(restaurantService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity getRestaurantById(
            @RequestHeader String authorizationHeader,
            @PathVariable Long id
    ) {
        if(!authenticationService.isUserValid(authorizationHeader.split(" ")[1])) {
            return new ResponseEntity<String>("Unauthorized, access denied.", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Restaurant>(restaurantService.findById(id), HttpStatus.OK);
    }
}
