package dev.nikoo.recipes.controllers;

import dev.nikoo.recipes.models.Recipe;
import dev.nikoo.recipes.repositories.InMemoryRecipeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    }


