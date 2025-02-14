package dev.nikoo.recipes.models;

import java.util.List;

public record Recipe(
        Integer id,
        String title,
        String description,
        FoodType food_type,
        Integer servings,
        Integer cooking_time,
        List<String>ingredients,
        List<String>instructions,
        String image
){}


