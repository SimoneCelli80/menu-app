package com.sogeti.menu.app.rest.requests;

import lombok.Builder;

import java.util.List;

@Builder
public record RecipeIdsRequest(
        List<Long> recipeIdList
) {
}
