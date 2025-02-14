package dev.nikoo.recipes.repositories;

import dev.nikoo.recipes.models.FoodType;
import dev.nikoo.recipes.models.Recipe;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcRecipeRepository implements RecipeRepository {

    private final JdbcClient jdbcClient;

    public JdbcRecipeRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Recipe> findAll() {
        return jdbcClient.sql("SELECT * FROM recipe")
                .query((rs, rowNum) -> new Recipe(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        FoodType.valueOf(rs.getString("food_type")),
                        rs.getInt("servings"),
                        rs.getInt("cooking_time"),
                        Arrays.asList(rs.getString("ingredients").split(", ")),
                        Arrays.asList(rs.getString("instructions").split(", ")),
                        rs.getString("image")
                ))
                .list();
    }

    public Optional<Recipe> findById(Integer id) {
        return jdbcClient.sql("SELECT * FROM Recipe WHERE id = :id")
                .param("id", id)
                .query((rs, rowNum) -> new Recipe(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        FoodType.valueOf(rs.getString("food_type")),
                        rs.getInt("servings"),
                        rs.getInt("cooking_time"),
                        Arrays.asList(rs.getString("ingredients").split(", ")),
                        Arrays.asList(rs.getString("instructions").split(", ")),
                        rs.getString("image")
                ))
                .optional();
    }

    public Recipe create(Recipe recipe) {
        String sql = "INSERT INTO Recipe (title, description, food_type, servings, cooking_time, ingredients, instructions, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
                .params(
                        recipe.title(),
                        recipe.description(),
                        recipe.food_type().name(),
                        recipe.servings(),
                        recipe.cooking_time(),
                        String.join(", ", recipe.ingredients()),
                        String.join(", ", recipe.instructions()),
                        recipe.image()
                )
                .update(keyHolder, new String[]{"id"}); // ✅ Explicitly tell Spring to return only "id"

        if (keyHolder.getKey() != null) {
            return new Recipe(
                    keyHolder.getKey().intValue(),  // Use auto-generated ID
                    recipe.title(),
                    recipe.description(),
                    recipe.food_type(),
                    recipe.servings(),
                    recipe.cooking_time(),
                    recipe.ingredients(),
                    recipe.instructions(),
                    recipe.image()
            );
        }

        throw new IllegalStateException("Failed to create recipe: " + recipe.title());
    }

    public void update(Recipe recipe, Integer id) {
        var updated = jdbcClient.sql("UPDATE recipe SET title = ?, description = ?, food_type = ?, servings = ?, cooking_time = ?, ingredients = ?, instructions = ?, image = ? WHERE id = ?")
                .params(
                        recipe.title(),
                        recipe.description(),
                        recipe.food_type().name(),
                        recipe.servings(),
                        recipe.cooking_time(),
                        String.join(", ", recipe.ingredients()),
                        String.join(", ", recipe.instructions()),
                        recipe.image(),
                        id
                )
                .update();

        Assert.state(updated == 1, "Failed to update recipe " + recipe.title());
    }

    public void delete(Integer id) {
        var updated = jdbcClient.sql("DELETE FROM recipe WHERE id = :id")
                .param("id", id)
                .update();

        Assert.state(updated == 1, "Failed to delete recipe " + id);
    }

    public int count() {
        return jdbcClient.sql("SELECT * FROM recipe").query().listOfRows().size();
    }

    public void saveAll(List<Recipe> recipes) {
        recipes.forEach(this::create);
    }

    public List<Recipe> findByTitle(String title) {
        return jdbcClient.sql("SELECT * FROM Recipe WHERE LOWER(title) LIKE LOWER(:title)")
                .param("title", "%" + title + "%")
                .query((rs, rowNum) -> new Recipe(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        FoodType.valueOf(rs.getString("food_type")),
                        rs.getInt("servings"),
                        rs.getInt("cooking_time"),
                        Arrays.asList(rs.getString("ingredients").split(", ")),
                        Arrays.asList(rs.getString("instructions").split(", ")),
                        rs.getString("image")

                ))
                .list();
    }

    public List<Recipe> findByIngredient(String ingredient) {
        return jdbcClient.sql("SELECT * FROM Recipe WHERE LOWER(ingredients) LIKE LOWER(:ingredient)")
                .param("ingredient", "%" + ingredient + "%")
                .query((rs, rowNum) -> new Recipe(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        FoodType.valueOf(rs.getString("food_type")),
                        rs.getInt("servings"),
                        rs.getInt("cooking_time"),
                        Arrays.asList(rs.getString("ingredients").split(", ")),
                        Arrays.asList(rs.getString("instructions").split(", ")),
                        rs.getString("image")
                ))
                .list();
    }

    public List<Recipe> findByCookingTimeLessThan(int maxCookingTime) {
        return jdbcClient.sql("SELECT * FROM Recipe WHERE cooking_time <= :maxCookingTime")
                .param("maxCookingTime", maxCookingTime)
                .query((rs, rowNum) -> new Recipe(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        FoodType.valueOf(rs.getString("food_type")),
                        rs.getInt("servings"),
                        rs.getInt("cooking_time"),
                        Arrays.asList(rs.getString("ingredients").split(", ")),
                        Arrays.asList(rs.getString("instructions").split(", ")),
                        rs.getString("image")
                ))
                .list();
    }

    public List<Recipe> findByFoodType(FoodType foodType) {
        return jdbcClient.sql("SELECT * FROM Recipe WHERE LOWER(food_type) = LOWER(:foodType)")
                .param("foodType", foodType.name())
                .query((rs, rowNum) -> new Recipe(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        FoodType.valueOf(rs.getString("food_type")),
                        rs.getInt("servings"),
                        rs.getInt("cooking_time"),
                        Arrays.asList(rs.getString("ingredients").split(", ")),
                        Arrays.asList(rs.getString("instructions").split(", ")),
                        rs.getString("image")
                ))
                .list();
    }

}
