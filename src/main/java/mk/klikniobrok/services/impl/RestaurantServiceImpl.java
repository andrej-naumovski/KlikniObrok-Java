package mk.klikniobrok.services.impl;

import com.sun.javaws.exceptions.InvalidArgumentException;
import mk.klikniobrok.models.Restaurant;
import mk.klikniobrok.repositories.RestaurantRepository;
import mk.klikniobrok.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by andrej on 12/10/16.
 */

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        if(restaurantRepository == null) {
            throw new IllegalArgumentException(RestaurantRepository.class.getName() + " cannot be null.");
        }
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant findById(Long id) {
        return restaurantRepository.findOne(id);
    }

    @Override
    public List<Restaurant> findAll() {
        return (List<Restaurant>) restaurantRepository.findAll();
    }
}
