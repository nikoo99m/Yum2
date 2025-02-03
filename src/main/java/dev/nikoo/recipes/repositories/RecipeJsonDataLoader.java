package dev.nikoo.recipes.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.nikoo.recipes.models.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class RecipeJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(RecipeJsonDataLoader.class);
    private final ObjectMapper objectMapper;
    private final JdbcRecipeRepository recipeRepository;

    public RecipeJsonDataLoader(ObjectMapper objectMapper, JdbcRecipeRepository recipeRepository) {
        this.objectMapper = objectMapper;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (recipeRepository.count() == 0) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/recipes.json")) {
                List<Recipe> recipes = objectMapper.readValue(inputStream, new TypeReference<List<Recipe>>() {});
                log.info("✅ Loaded {} recipes from JSON.", recipes.size());
                recipeRepository.saveAll(recipes);
            } catch (IOException e) {
                throw new RuntimeException("❌ Failed to read JSON data", e);
            }
        } else {
            log.info("📌 Recipes already loaded. Skipping JSON import.");
        }
    }
}
