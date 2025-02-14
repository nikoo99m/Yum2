package dev.nikoo.recipes.repositories;

import dev.nikoo.recipes.models.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository {
    List<Recipe> findAll();

    Optional<Recipe> findById(Integer id);

    Recipe create(Recipe recipe);

    void update(Recipe recipe, Integer id);

    void delete(Integer id);

    int count();

    void saveAll(List<Recipe> runs);

}
