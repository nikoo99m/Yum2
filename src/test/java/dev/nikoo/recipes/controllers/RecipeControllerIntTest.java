//package dev.nikoo.recipes.controllers;
//
//import dev.nikoo.recipes.models.FoodType;
//import dev.nikoo.recipes.models.Recipe;
//import dev.nikoo.recipes.repositories.RecipeRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestClient;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class RecipeControllerIntTest {
//
//    @LocalServerPort
//    int randomServerPort;
//    @Autowired
//    @Qualifier("jdbcRecipeRepository")
//    RecipeRepository recipeRepository;
//    RestClient restClient;
//
//    @BeforeEach
//    void setUp() {
//        restClient = RestClient.create("http://localhost:" + randomServerPort);
//    }
//
//    @Test
//    void shouldFindAllRecipes() {
//        List<Recipe> recipes = restClient.get()
//                .uri("/api/recipes")
//                .retrieve()
//                .body(new ParameterizedTypeReference<>() {});
//
//        assertNotNull(recipes);
//        assertFalse(recipes.isEmpty());
//    }
//
//    @Test
//    void shouldFindRecipeById() {
//
//        recipeRepository.create(new Recipe(
//                102,
//                "Test Recipe",
//                "Test Description",
//                FoodType.FAST_FOOD,
//                2, 30,
//                Arrays.asList("Salt", "Pepper"),
//                Arrays.asList("Cook it")
//        ));
//
//        ResponseEntity<Recipe> response = restClient.get()
//                .uri("/api/recipes/102")
//                .retrieve()
//                .toEntity(Recipe.class);
//
//        assertEquals(200, response.getStatusCodeValue(), "Recipe should be found!");
//        Recipe retrievedRecipe = response.getBody();
//        assertNotNull(retrievedRecipe);
//        assertEquals(102, retrievedRecipe.id(), "Recipe ID should match!");
//    }
//
//
//    @Test
//    void shouldCreateNewRecipe() {
//        Recipe newRecipe = new Recipe(
//                11,
//                "Spaghetti Bolognese",
//                "Italian pasta with meat sauce",
//                FoodType.SPECIAL_CUISINE,
//                2,
//                45,
//                Arrays.asList("Spaghetti", "Beef", "Tomato Sauce", "Onion", "Garlic"),
//                Arrays.asList("Cook pasta.", "Prepare sauce.", "Combine.")
//        );
//
//        ResponseEntity<Void> response = restClient.post()
//                .uri("/api/recipes")
//                .body(newRecipe)
//                .retrieve()
//                .toBodilessEntity();
//
//        assertEquals(201, response.getStatusCodeValue());
//    }
//
//    @Test
//    void shouldUpdateExistingRecipe() {
//        Recipe recipe = restClient.get().uri("/api/recipes/1").retrieve().body(Recipe.class);
//        assertNotNull(recipe);
//
//        Recipe updatedRecipe = new Recipe(
//                recipe.id(),
//                "Updated Kebab",
//                "Grilled kebab with additional spices",
//                recipe.food_type(),
//                5,
//                40,
//                recipe.ingredients(),
//                recipe.instructions()
//        );
//
//        ResponseEntity<Void> response = restClient.put()
//                .uri("/api/recipes/1")
//                .body(updatedRecipe)
//                .retrieve()
//                .toBodilessEntity();
//
//        assertEquals(204, response.getStatusCodeValue());
//    }
//
//
//    @Test
//    void shouldFindRecipesByTitle() {
//
//        recipeRepository.create(new Recipe(
//                99,
//                "Unique Kebab",
//                "Test Description",
//                FoodType.FAST_FOOD,
//                2, 20,
//                Arrays.asList("Chicken", "Onion"),
//                Arrays.asList("Cook")
//        ));
//
//        List<Recipe> recipes = restClient.get()
//                .uri(uriBuilder -> uriBuilder.path("/api/recipes/filter/title")
//                        .queryParam("title", "Unique Kebab")
//                        .build())
//                .retrieve()
//                .body(new ParameterizedTypeReference<>() {});
//
//        assertNotNull(recipes);
//        assertEquals(1, recipes.size(), "Only 1 recipe should be returned!");
//    }
//
//
//    @Test
//    void shouldFindRecipesByCookingTime() {
//        int maxCookingTime = 30;
//        recipeRepository.create(new Recipe(
//                1, "Persian Kebab", "Delicious grilled kebab",
//                FoodType.FAST_FOOD, 4, 30,
//                Arrays.asList("Beef", "Onion", "Tomato"),
//                Arrays.asList("Mix the ingredients", "Grill the kebab")
//        ));
//
//        List<Recipe> recipes = restClient.get()
//                .uri(uriBuilder -> uriBuilder.path("/api/recipes/filter/cooking-time")
//                        .queryParam("maxCookingTime", maxCookingTime)
//                        .build())
//                .retrieve()
//                .body(new ParameterizedTypeReference<>() {});
//
//        assertNotNull(recipes);
//        assertFalse(recipes.isEmpty());
//        assertTrue(recipes.stream().allMatch(r -> r.cooking_time() <= maxCookingTime));
//    }
//
//    @Test
//    void shouldFilterRecipesByFoodType() {
//        FoodType foodType = FoodType.FAST_FOOD;
//        recipeRepository.create(new Recipe(
//                1, "Persian Kebab", "Delicious grilled kebab",
//                foodType, 4, 30,
//                Arrays.asList("Beef", "Onion", "Tomato"),
//                Arrays.asList("Mix the ingredients", "Grill the kebab")
//        ));
//
//        List<Recipe> recipes = restClient.get()
//                .uri(uriBuilder -> uriBuilder.path("/api/recipes/filter/type")
//                        .queryParam("type", foodType.name())
//                        .build())
//                .retrieve()
//                .body(new ParameterizedTypeReference<>() {});
//
//        assertNotNull(recipes);
//        assertFalse(recipes.isEmpty());
//        assertTrue(recipes.stream().allMatch(r -> r.food_type() == foodType));
//    }
//}
