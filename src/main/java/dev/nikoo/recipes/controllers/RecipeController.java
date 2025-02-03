package dev.nikoo.recipes.controllers;

import dev.nikoo.recipes.models.FoodType;
import dev.nikoo.recipes.models.Recipe;
import dev.nikoo.recipes.repositories.InMemoryRecipeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
    @RestController
    @RequestMapping("/api/recipes")
    public class RecipeController {
        private final InMemoryRecipeRepository recipeRepository;


        public RecipeController(InMemoryRecipeRepository recipeRepository) {
            this.recipeRepository = recipeRepository;
        }

        @GetMapping
        List<Recipe> findAll() {
            return recipeRepository.findAll();
        }

        @GetMapping("/{id}")
        Recipe findById(@PathVariable Integer id) {
            Optional<Recipe> recipe = recipeRepository.findById(id);
            if(recipe.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found.");
            }
            return recipe.get();
        }

        @ResponseStatus(HttpStatus.CREATED)
        @PostMapping
        void create(@Valid @RequestBody Recipe recipe) {
            recipeRepository.create(recipe);
        }

        @ResponseStatus(HttpStatus.NO_CONTENT)
        @PutMapping("/{id}")
        void update(@Valid @RequestBody Recipe recipe, @PathVariable Integer id) {
            recipeRepository.update(recipe, id);
        }

        @ResponseStatus(HttpStatus.NO_CONTENT)
        @DeleteMapping("/{id}")
        void delete(@PathVariable Integer id) {
            recipeRepository.delete(id);
        }
        @GetMapping("/filter/title")
        List<Recipe> findByTitle(@RequestParam String title) {
            return recipeRepository.findByTitle(title.toLowerCase());
        }
        //http://localhost:8080/api/recipes/filter/title?title=persian%20kebab

        // Filter by ingredient
        @GetMapping("/filter/ingredient")
        List<Recipe> findByIngredient(@RequestParam String ingredient) {
            return recipeRepository.findByIngredient(ingredient.toLowerCase());
        }
        //http://localhost:8080/api/recipes/filter/ingredient?ingredient=chicken

        // Filter by cooking time
        @GetMapping("/filter/cooking-time")
        List<Recipe> findByCookingTimeLessThan(@RequestParam int maxCookingTime) {
            return recipeRepository.findByCookingTimeLessThan(maxCookingTime);
        }
        //http://localhost:8080/api/recipes/filter/cooking-time?maxCookingTime=30

        // Filter by food type
        @GetMapping("/filter/type")
        List<Recipe> findByFoodType(@RequestParam String type) {
            try {
                FoodType foodType = FoodType.valueOf(type.toUpperCase().replace(" ", "_"));
                return recipeRepository.findByFoodType(foodType);
            } catch (IllegalArgumentException e) {
                return Collections.emptyList();
            }
        }
        //http://localhost:8080/api/recipes/filter/type?type=fast%20food
    }




