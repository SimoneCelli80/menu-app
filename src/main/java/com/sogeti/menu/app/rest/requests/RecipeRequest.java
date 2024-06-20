package com.sogeti.menu.app.rest.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

import java.util.List;

@Builder
public record RecipeRequest(
        @NotBlank(message = "Please enter the name fo the recipe.")
        String recipeName,
        @NotEmpty(message = "Please enter at least one ingredient for the recipe.")
        List<IngredientRequest> ingredientList
) {
}
