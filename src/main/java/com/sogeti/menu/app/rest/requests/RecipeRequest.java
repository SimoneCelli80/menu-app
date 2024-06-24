package com.sogeti.menu.app.rest.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record RecipeRequest(
        @NotNull(message = "Please enter the name of the recipe.")
        @NotBlank(message = "Please enter the name of the recipe.")
        String recipeName,
        @NotNull(message = "Please enter the name of the ingredient.")
        @NotEmpty(message = "Please enter at least one ingredient for the recipe.")
        List<IngredientRequest> ingredientList
) {
}
