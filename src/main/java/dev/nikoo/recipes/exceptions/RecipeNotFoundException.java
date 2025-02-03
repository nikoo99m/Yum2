package dev.nikoo.recipes.exceptions;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException() {
        super("Recipe Not Found");

    }
}