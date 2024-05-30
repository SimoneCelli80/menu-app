package com.sogeti.menu.app.rest.requests;

import com.sogeti.menu.app.persistence.entities.Ingredient;

import java.util.List;

public record RecipeRequest(
        String recipeName,
        List<Ingredient> ingredientList
) {
}
