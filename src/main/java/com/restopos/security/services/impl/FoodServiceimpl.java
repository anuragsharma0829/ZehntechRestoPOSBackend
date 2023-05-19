package com.restopos.security.services.impl;

import com.restopos.models.Food;
import com.restopos.models.Tables;
import com.restopos.repository.FoodRepository;
import com.restopos.security.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodServiceimpl implements FoodService {
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food addFood(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public List<Food> fetchFoods() {
        return (List<Food>) foodRepository.findAll();
    }

    @Override
    public Optional<Food> findByfId(Integer food_id) {
        return foodRepository.findById(food_id);

    }


//    fetch foods


}
