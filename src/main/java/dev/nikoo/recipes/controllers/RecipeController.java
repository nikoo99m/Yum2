package dev.nikoo.recipes.controllers;

import dev.nikoo.recipes.models.FoodType;
import dev.nikoo.recipes.models.Recipe;
import dev.nikoo.recipes.repositories.JdbcRecipeRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    private final JdbcRecipeRepository recipeRepository;
    private final ImageService imageService;


    public RecipeController(JdbcRecipeRepository recipeRepository, ImageService imageService) {
        this.recipeRepository = recipeRepository;
        this.imageService = imageService;
    }

    @GetMapping
    List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @GetMapping("/{id}")
    Recipe findById(@PathVariable Integer id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found.");
        }
        return recipe.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Recipe create(@Valid @RequestBody Recipe recipe) {
        return recipeRepository.create(recipe);
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Recipe> createRecipeJson(@Valid @RequestBody Recipe recipe) {
        try {
            Recipe savedRecipe = recipeRepository.create(recipe);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            String imageUrl = imageService.uploadImage(file);
            return ResponseEntity.ok(imageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createRecipe(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("foodType") String foodType,
            @RequestParam("servings") int servings,
            @RequestParam("cookingTime") int cookingTime,
            @RequestParam("ingredients") List<String> ingredients,
            @RequestParam("instructions") List<String> instructions,
            @RequestParam(value = "image", required = false) MultipartFile file
    ) {
        try {
            FoodType parsedFoodType;
            try {
                parsedFoodType = FoodType.valueOf(foodType.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap("error", "Invalid food type: " + foodType));
            }

            String imageUrl = (file != null && !file.isEmpty()) ? imageService.uploadImage(file) : null;

            Recipe recipe = new Recipe(null, title, description, parsedFoodType,
                    servings, cookingTime, ingredients, instructions, imageUrl);

            Recipe savedRecipe = recipeRepository.create(recipe);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedRecipe);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Error processing request: " + e.getMessage()));
        }
    }


}




