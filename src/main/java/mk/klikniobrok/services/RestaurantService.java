package mk.klikniobrok.services;

import mk.klikniobrok.models.Restaurant;

import java.util.List;

/**
 * Created by andrej on 12/10/16.
 */
public interface RestaurantService {
    Restaurant findById(Long id);
    List<Restaurant> findAll();
}
