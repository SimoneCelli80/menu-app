package com.sogeti.menu.app.rest.requests;

import java.util.List;

public record RecipeIdsRequest(
        List<Long> recipeIdList
) {
}
