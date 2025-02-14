//package dev.nikoo.recipes.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import dev.nikoo.recipes.models.FoodType;
//import dev.nikoo.recipes.models.Recipe;
//import dev.nikoo.recipes.repositories.JdbcRecipeRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
////import org.springframework.boot.test.mock.mockito.MockBean;
//
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.hamcrest.Matchers.is;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(RecipeController.class)
//class RecipeControllerTest {
//
//    @Autowired
//    MockMvc mvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @MockBean
//    JdbcRecipeRepository repository;
//
//    private final List<Recipe> recipes = new ArrayList<>();
//
//    @BeforeEach
//    void setUp() {
//        recipes.add(new Recipe(
//                1,
//                "Persian Kebab",
//                "Delicious grilled kebab",
//                FoodType.FAST_FOOD,
//                4,
//                30,
//                Arrays.asList("Beef", "Onion", "Tomato"),
//                Arrays.asList("Mix ingredients.", "Grill.")
//        ));
//
//    }
//
//    @Test
//    void shouldFindAllRecipes() throws Exception {
//        when(repository.findAll()).thenReturn(recipes);
//        mvc.perform(get("/api/recipes"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()", is(recipes.size())));
//    }
//
//    @Test
//    void shouldFindRecipeById() throws Exception {
//        Recipe recipe = recipes.get(0);
//        when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(recipe));
//        mvc.perform(get("/api/recipes/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(recipe.id())))
//                .andExpect(jsonPath("$.title", is(recipe.title())))
//                .andExpect(jsonPath("$.description", is(recipe.description())))
//                .andExpect(jsonPath("$.food_type", is(recipe.food_type().toString())))
//                .andExpect(jsonPath("$.servings", is(recipe.servings())))
//                .andExpect(jsonPath("$.cooking_time", is(recipe.cooking_time())));
//    }
//
//    @Test
//    void shouldReturnNotFoundWithInvalidId() throws Exception {
//        when(repository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.empty());
//        mvc.perform(get("/api/recipes/99"))
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void shouldCreateNewRecipe() throws Exception {
//        Recipe recipe = new Recipe(
//                null,
//                "Spaghetti Bolognese",
//                "Italian pasta with meat sauce",
//                FoodType.SPECIAL_CUISINE,
//                2,
//                45,
//                Arrays.asList("Spaghetti", "Beef", "Tomato Sauce", "Onion", "Garlic"),
//                Arrays.asList("Cook pasta.", "Prepare sauce.", "Combine.")
//        );
//
//        doNothing().when(repository).create(ArgumentMatchers.any(Recipe.class));
//
//        mvc.perform(post("/api/recipes")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(recipe)))
//                .andExpect(status().isCreated());
//    }
//
//    @Test
//    void shouldUpdateRecipe() throws Exception {
//        Recipe updatedRecipe = new Recipe(
//                1,
//                "Updated Kebab",
//                "Grilled kebab with additional spices",
//                FoodType.FAST_FOOD,
//                5,
//                40,
//                Arrays.asList("Beef", "Onion", "Tomato", "Spices"),
//                Arrays.asList("Mix ingredients.", "Grill with spices.")
//        );
//
//        mvc.perform(put("/api/recipes/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedRecipe)))
//                .andExpect(status().isNoContent());
//    }
//
//    @Test
//    void shouldDeleteRecipe() throws Exception {
//        mvc.perform(delete("/api/recipes/1"))
//                .andExpect(status().isNoContent());
//    }
//
//
//    @Test
//    void shouldFindRecipesByTitle() throws Exception {
//        String title = "Persian Kebab";
//        when(repository.findByTitle(title.toLowerCase())).thenReturn(recipes);
//
//        mvc.perform(get("/api/recipes/filter/title")
//                        .queryParam("title", title))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()", is(recipes.size())))
//                .andExpect(jsonPath("$[0].title", is(title)));
//    }
//    @Test
//    void shouldFindRecipesByIngredients() throws Exception {
//        String ingredient = "Chicken";
//        when(repository.findByIngredient(ingredient.toLowerCase())).thenReturn(recipes);
//
//        mvc.perform(get("/api/recipes/filter/ingredient")
//                        .queryParam("ingredient", ingredient))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()", is(recipes.size())))
//                .andExpect(jsonPath("$[0].ingredients", is(recipes.get(0).ingredients())));
//    }
//    @Test
//    void shouldFindRecipesByCookingTime() throws Exception {
//        int maxCookingTime = 30;
//        when(repository.findByCookingTimeLessThan(maxCookingTime)).thenReturn(recipes);
//
//        mvc.perform(get("/api/recipes/filter/cooking-time")
//                        .queryParam("maxCookingTime", String.valueOf(maxCookingTime)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()", is(recipes.size())))
//                .andExpect(jsonPath("$[0].cooking_time", is(recipes.get(0).cooking_time())));
//    }
//    @Test
//    void shouldFilterRecipesByFoodType() throws Exception {
//        String foodType = "FAST_FOOD";
//        when(repository.findByFoodType(FoodType.valueOf(foodType))).thenReturn(recipes);
//
//        mvc.perform(get("/api/recipes/filter/type")
//                        .queryParam("type", foodType))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()", is(recipes.size())))
//                .andExpect(jsonPath("$[0].food_type", is(foodType)));
//    }
//
//}
