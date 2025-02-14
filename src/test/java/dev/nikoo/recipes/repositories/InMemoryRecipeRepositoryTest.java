//package dev.nikoo.recipes.repositories;
//
//import dev.nikoo.recipes.models.FoodType;
//import dev.nikoo.recipes.models.Recipe;
//import dev.nikoo.recipes.exceptions.RecipeNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//class InMemoryRecipeRepositoryTest {
//    private InMemoryRecipeRepository repository;
//
//    @BeforeEach
//    void setUp() {
//        repository = new InMemoryRecipeRepository();
////        repository.create(new Recipe(1, "Persian Kebab", "Delicious grilled kebab", FoodType.FAST_FOOD, 4, 30,
//                Arrays.asList("Beef", "Onion", "Tomato", "Bell Pepper"),
//                Arrays.asList("Mix ingredients.", "Grill.")));
//
//        repository.create(new Recipe(2, "Spaghetti Bolognese", "Italian pasta with meat sauce", FoodType.SPECIAL_CUISINE, 2, 45,
//                Arrays.asList("Spaghetti", "Beef", "Tomato Sauce", "Onion", "Garlic"),
//                Arrays.asList("Cook pasta.", "Prepare sauce.", "Combine.")));
//
//        repository.create(new Recipe(3, "Tacos", "Mexican tacos with beef and veggies", FoodType.FAST_FOOD, 3, 20,
//                Arrays.asList("Tortilla", "Beef", "Lettuce", "Cheese", "Tomato"),
//                Arrays.asList("Cook beef.", "Prepare toppings.", "Assemble tacos.")));
//    }
//
//    @Test
//    void shouldFindAllRecipes() {
//        List<Recipe> recipes = repository.findAll();
//        assertFalse(recipes.isEmpty());
//        assertEquals(3, recipes.size());
//    }
//
//    @Test
//    void shouldFindRecipeById() {
//        Optional<Recipe> recipe = repository.findById(1);
//        assertTrue(recipe.isPresent());
//        assertEquals("Persian Kebab", recipe.get().title());
//    }
//
//    @Test
//    void shouldNotFindRecipeWithInvalidId() {
//        assertThrows(RecipeNotFoundException.class, () -> repository.findById(10));
//    }
//
//    @Test
//    void shouldCreateNewRecipe() {
//        Recipe newRecipe = new Recipe(4, "Pasta Carbonara", "Classic Italian pasta", FoodType.SPECIAL_CUISINE, 2, 25,
//                Arrays.asList("Spaghetti", "Egg", "Cheese", "Pancetta"),
//                Arrays.asList("Cook pasta", "Mix ingredients", "Serve"));
//        repository.create(newRecipe);
//        assertEquals(4, repository.count());
//        assertTrue(repository.findById(4).isPresent());
//    }
//
//    @Test
//    void shouldUpdateRecipe() {
//        Recipe updatedRecipe = new Recipe(1, "Updated Kebab", "Updated Description", FoodType.FAST_FOOD, 4, 35,
//                Arrays.asList("Beef", "Onion"), Arrays.asList("Grill meat", "Serve"));
//        repository.update(updatedRecipe, 1);
//        assertEquals("Updated Kebab", repository.findById(1).get().title());
//    }
//
//    @Test
//    void testDeleteRecipe() {
//        repository.delete(1);
//        assertThrows(RecipeNotFoundException.class, () -> repository.findById(1));
//        assertEquals(2, repository.count());
//    }
//
//    @Test
//    void testFindByTitle() {
//        List<Recipe> recipes = repository.findByTitle("Spaghetti");
//        assertEquals(1, recipes.size());
//        assertEquals("Spaghetti Bolognese", recipes.get(0).title());
//    }
//
//    @Test
//    void testFindByIngredient() {
//        List<Recipe> recipes = repository.findByIngredient("Beef");
//        assertEquals(3, recipes.size());
//    }
//
//    @Test
//    void testFindByCookingTimeLessThan() {
//        List<Recipe> recipes = repository.findByCookingTimeLessThan(30);
//        assertEquals(2, recipes.size());
//    }
//
//    @Test
//    void testFindByFoodType() {
//        List<Recipe> fastFoodRecipes = repository.findByFoodType(FoodType.FAST_FOOD);
//        assertEquals(2, fastFoodRecipes.size());
//    }
//}
