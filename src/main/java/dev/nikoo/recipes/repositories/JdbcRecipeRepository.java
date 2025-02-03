package dev.nikoo.recipes.repositories;

import dev.nikoo.recipes.models.FoodType;
import dev.nikoo.recipes.models.Recipe;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class JdbcRecipeRepository implements RecipeRepository {

    private final JdbcClient jdbcClient;

    public JdbcRecipeRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Recipe> findAll() {
        return jdbcClient.sql("select * from recipe")
                .query(Recipe.class)
                .list();
    }

    public Optional<Recipe> findById(Integer id) {
        return jdbcClient.sql("SELECT id, title, description, food_type, servings, cooking_time, ingredients, instructions FROM Recipe WHERE id = :id")
                .param("id", id)
                .query(Recipe.class)
                .optional();
    }

    public void create(Recipe recipe) {
        var updated = jdbcClient.sql("INSERT INTO Recipe(id, title, description, food_type, servings, cooking_time, ingredients, instructions) values(?,?,?,?,?,?,?,?)")
                .params(List.of(recipe.id(), recipe.title(), recipe.description(), recipe.cooking_time(), recipe.servings(), recipe.ingredients(), recipe.instructions().toString()))
                .update();

        Assert.state(updated == 1, "Failed to create recipe " + recipe.title());
    }

    public void update(Recipe recipe, Integer id) {
        var updated = jdbcClient.sql(
                        "UPDATE recipe SET title = ?, description = ?,food_type = ?, servings = ?, cooking_time = ?, ingredients = ?, instructions = ? WHERE id = ?")
                .params(recipe.title(),
                        recipe.description(),
                        recipe.cooking_time(),
                        recipe.servings(),
                        recipe.ingredients(),
                        String.join(", ", recipe.instructions()),
                        id)
                .update();

        Assert.state(updated == 1, "Failed to update recipe " + recipe.title());
    }


    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from recipe where id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete recipe " + id);
    }

    public int count() {
        return jdbcClient.sql("select * from recipe").query().listOfRows().size();
    }

    public void saveAll(List<Recipe> recipes) {
        recipes.stream().forEach(this::create);
    }

    public List<Recipe> findByTitle(String title) {
        return jdbcClient.sql("SELECT id, title, description, food_type, servings, cooking_time, ingredients, instructions FROM Recipe WHERE LOWER(title) LIKE LOWER(:title)")
                .param("title", "%" + title + "%")
                .query(Recipe.class)
                .list();
    }

    public List<Recipe> findByIngredient(String ingredient) {
        return jdbcClient.sql("SELECT id, title, description, food_type, servings, cooking_time, ingredients, instructions FROM Recipe WHERE LOWER(ingredients) LIKE LOWER(:ingredient)")
                .param("ingredient", "%" + ingredient + "%")
                .query(Recipe.class)
                .list();
    }

    public List<Recipe> findByCookingTimeLessThan(int maxCookingTime) {
        return jdbcClient.sql("SELECT id, title, description, food_type, servings, cooking_time, ingredients, instructions FROM Recipe WHERE cooking_time <= :maxCookingTime")
                .param("maxCookingTime", maxCookingTime)
                .query(Recipe.class)
                .list();
    }

    public List<Recipe> findByFoodType(FoodType foodType) {
        return jdbcClient.sql("SELECT id, title, description, food_type, servings, cooking_time, ingredients, instructions FROM Recipe WHERE LOWER(food_type) = LOWER(:foodType)")
                .param("foodType", foodType.name())
                .query(Recipe.class)
                .list();
    }

}
