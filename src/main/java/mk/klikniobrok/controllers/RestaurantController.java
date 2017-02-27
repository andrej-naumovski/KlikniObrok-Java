package mk.klikniobrok.controllers;

import mk.klikniobrok.models.Restaurant;
import mk.klikniobrok.services.AuthenticationService;
import mk.klikniobrok.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.xml.ws.Response;
import java.util.List;

/**
 * Created by andrej on 12/10/16.
 */

@RestController
@RequestMapping(value = "/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    private RestaurantService restaurantService;
    private AuthenticationService authenticationService;

    private RestTemplate restTemplate;

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
        this.restTemplate = new RestTemplate();
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
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @PathVariable Long id
    ) {
        if(!authenticationService.isUserValid(authorizationHeader.split(" ")[1])) {
            return new ResponseEntity<String>("Unauthorized, access denied.", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Restaurant>(restaurantService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/order", method = RequestMethod.GET)
    public ResponseEntity getOrder(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @PathVariable Long id,
            @RequestParam Integer orderId
    ) {
        if(!authenticationService.isUserValid(authorizationHeader.split(" ")[1])) {
            return new ResponseEntity<>("Unauthorized, access denied.", HttpStatus.UNAUTHORIZED);
        }
        Restaurant restaurant = restaurantService.findById(id);
        if(restaurant == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        String response = restTemplate.getForObject(restaurant.getEndpoint() + "/order/" + orderId, String.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/orders", method = RequestMethod.GET)
    public ResponseEntity getOrdersByUser(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @PathVariable Long id,
            @RequestParam String user
    ) {
        if(!authenticationService.isUserValid(authorizationHeader.split(" ")[1])) {
            return new ResponseEntity<>("Unauthorized, access denied.", HttpStatus.UNAUTHORIZED);
        }
        Restaurant restaurant = restaurantService.findById(id);
        if(restaurant == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        String response = restTemplate.getForObject(restaurant.getEndpoint() + "/order?user=" + user, String.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/entry_types", method = RequestMethod.GET)
    public ResponseEntity getEntryTypes(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @PathVariable Long id
    ) {
        if(!authenticationService.isUserValid(authorizationHeader.split(" ")[1])) {
            return new ResponseEntity<>("Unauthorized, access denied.", HttpStatus.UNAUTHORIZED);
        }
        Restaurant restaurant = restaurantService.findById(id);
        if(restaurant == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        String response = restTemplate.getForObject(restaurant.getEndpoint() + "/entry/types/", String.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}/entries", method = RequestMethod.GET)
    public ResponseEntity getEntriesByType(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @PathVariable Long id,
            @RequestParam String type
    ) {
        if(!authenticationService.isUserValid(authorizationHeader.split(" ")[1])) {
            return new ResponseEntity<>("Unauthorized, access denied.", HttpStatus.UNAUTHORIZED);
        }
        Restaurant restaurant = restaurantService.findById(id);
        if(restaurant == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        String response = restTemplate.getForObject(restaurant.getEndpoint() + "/entry/types/" + type, String.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
