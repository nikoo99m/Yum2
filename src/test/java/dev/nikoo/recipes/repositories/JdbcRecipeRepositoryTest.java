//package dev.nikoo.recipes.repositories;
//
//import dev.nikoo.recipes.models.FoodType;
//import dev.nikoo.recipes.models.Recipe;
//import dev.nikoo.recipes.repositories.JdbcRecipeRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@JdbcTest
//@Import(JdbcRecipeRepository.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class JdbcRecipeRepositoryTest {
//
//    @Autowired
//    private JdbcRecipeRepository repository;
//
//    @BeforeEach
//    void setUp() {
//        repository.create(new Recipe(1, "Persian Kebab", "Delicious grilled kebab", FoodType.FAST_FOOD, 4, 30,
//                Arrays.asList("Beef", "Onion", "Tomato", "Bell Pepper"),
//                Arrays.asList("Mix ingredients.", "Grill.")));
//
//        repository.create(new Recipe(2, "Spaghetti Bolognese", "Italian pasta with meat sauce", FoodType.SPECIAL_CUISINE, 2, 45,
//                Arrays.asList("Spaghetti", "Beef", "Tomato Sauce", "Onion", "Garlic"),
//                Arrays.asList("Cook pasta.", "Prepare sauce.", "Combine.")));
//    }
//
//    @Test
//    void shouldFindAllRecipes() {
//        List<Recipe> recipes = repository.findAll();
//        assertEquals(2, recipes.size());
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
//        Optional<Recipe> recipe = repository.findById(99);
//        assertTrue(recipe.isEmpty());
//    }
//
//    @Test
//    void shouldCreateNewRecipe() {
//        repository.create(new Recipe(3, "Tacos", "Mexican tacos with beef and veggies", FoodType.FAST_FOOD, 3, 20,
//                Arrays.asList("Tortilla", "Beef", "Lettuce", "Cheese", "Tomato"),
//                Arrays.asList("Cook beef.", "Prepare toppings.", "Assemble tacos.")));
//        List<Recipe> recipes = repository.findAll();
//        assertEquals(3, recipes.size());
//    }
//
//    @Test
//    void shouldUpdateRecipe() {
//        repository.update(new Recipe(1, "Updated Persian Kebab", "Grilled kebab with spices", FoodType.FAST_FOOD, 5, 35,
//                Arrays.asList("Beef", "Onion", "Tomato", "Bell Pepper", "Spices"),
//                Arrays.asList("Mix ingredients.", "Grill with spices.")), 1);
//
//        Optional<Recipe> recipe = repository.findById(1);
//        assertTrue(recipe.isPresent());
//        assertEquals("Updated Persian Kebab", recipe.get().title());
//        assertEquals(5, recipe.get().servings());
//    }
//
//    @Test
//    void shouldDeleteRecipe() {
//        repository.delete(1);
//        List<Recipe> recipes = repository.findAll();
//        assertEquals(1, recipes.size());
//    }
//    @Test
//    void shouldFindByTitle() {
//        List<Recipe> recipes = repository.findByTitle("Kebab");
//        assertEquals(1, recipes.size());
//        assertEquals("Persian Kebab", recipes.get(0).title());
//    }
//
//    @Test
//    void shouldFindByCookingTimeLessThan() {
//        List<Recipe> recipes = repository.findByCookingTimeLessThan(40);
//        assertEquals(1, recipes.size());
//        assertEquals("Persian Kebab", recipes.get(0).title());
//    }
//
//    @Test
//    void shouldFindByFoodType() {
//        List<Recipe> recipes = repository.findByFoodType(FoodType.FAST_FOOD);
//        assertEquals(1, recipes.size());
//        assertEquals("Persian Kebab", recipes.get(0).title());
//    }
//
//    @Test
//    void shouldFindByIngredients() {
//        List<Recipe> recipes = repository.findByIngredient("Beef");
//        assertEquals(2, recipes.size());
//    }
//}
