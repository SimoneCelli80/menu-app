package com.sogeti.menu.app.rest.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record RecipeIdsRequest(
        @NotNull(message = "Please enter a list of recipe ids.")
        @NotBlank(message = "Please enter a list of recipe ids.")
        List<Long> recipeIdList
) {
}
