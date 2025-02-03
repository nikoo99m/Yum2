package dev.nikoo.recipes.repositories;

import dev.nikoo.recipes.models.FoodType;
import dev.nikoo.recipes.models.Recipe;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import dev.nikoo.recipes.exceptions.RecipeNotFoundException;


import java.util.*;

@Repository
public class InMemoryRecipeRepository implements RecipeRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryRecipeRepository.class);
    private final List<Recipe> recipes = new ArrayList<>();

    public List<Recipe> findAll() {
        return recipes;
    }

    public Optional<Recipe> findById(Integer id) {
        return Optional.ofNullable(recipes.stream()
                .filter(recipe -> Objects.equals(recipe.id(), id))
                .findFirst()
                .orElseThrow(RecipeNotFoundException::new));
    }

    public void create(Recipe recipe) {
        Recipe newRecipe = new Recipe(recipe.id(),
                recipe.title(),
                recipe.description(),
                recipe.food_type(),
                recipe.servings(),
                recipe.cooking_time(),
                recipe.ingredients(),
                recipe.instructions());

        recipes.add(newRecipe);
    }

    public void update(Recipe newRecipe, Integer id) {
        Optional<Recipe> existingRecipe = findById(id);
        if (existingRecipe.isPresent()) {
            var r = existingRecipe.get();
            log.info("Updating Existing Recipe: " + existingRecipe.get());
            recipes.set(recipes.indexOf(r), newRecipe);
        }
    }

    public void delete(Integer id) {
        log.info("Deleting Recipe: " + id);
        recipes.removeIf(recipe -> recipe.id().equals(id));
    }

    public int count() {
        return recipes.size();
    }

    public void saveAll(List<Recipe> recipes) {
        recipes.stream().forEach(recipe -> create(recipe));
    }
    @PostConstruct
    private void init() {
        addSampleRecipes();
        log.info("In-Memory Storage initialised with {} sample recipes.", recipes.size());
    }

    private void addSampleRecipes() {
        recipes.add(new Recipe(1,
                "Persian Kebab",
                "Delicious grilled kebab",
                FoodType.FAST_FOOD,
                4,
                30,
                Arrays.asList("Beef", "Onion", "Tomato", "Bell Pepper"),
                Arrays.asList("Mix ingredients.", "Grill.")));

        recipes.add(new Recipe(2,
                "Spaghetti Bolognese",
                "Italian pasta with meat sauce",
                FoodType.SEPECIAL_CUISINE,
                2,
                45,
                Arrays.asList("Spaghetti", "Beef", "Tomato Sauce", "Onion", "Garlic"),
                Arrays.asList("Cook pasta.", "Prepare sauce.", "Combine.")));

        recipes.add(new Recipe(3,
                "Tacos",
                "Mexican tacos with beef and veggies",
                FoodType.FAST_FOOD,
                3,
                20,
                Arrays.asList("Tortilla", "Beef", "Lettuce", "Cheese", "Tomato"),
                Arrays.asList("Cook beef.", "Prepare toppings.", "Assemble tacos.")));
    }
}