package com.restopos.security.services;

import com.restopos.models.Food;
import com.restopos.models.Tables;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodService {

    Food addFood(Food food);

    List<Food> fetchFoods();

    Optional<Food> findByfId(Integer food_id);



}
