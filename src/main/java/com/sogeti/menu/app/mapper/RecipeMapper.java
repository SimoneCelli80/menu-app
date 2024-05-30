package com.sogeti.menu.app.mapper;

import com.sogeti.menu.app.persistence.entities.RecipeEntity;
import com.sogeti.menu.app.rest.dtos.RecipeDto;
import com.sogeti.menu.app.rest.requests.RecipeRequest;
import com.sogeti.menu.app.rest.responses.RecipeResponse;

public class RecipeMapper {

    public static RecipeDto fromRequestToDto(RecipeRequest recipeRequest) {
        if (recipeRequest == null)  {
            return null;
        }

        return RecipeDto.builder()
                .recipeName(recipeRequest.recipeName())
                .ingredientList(recipeRequest.ingredientList())
                .build();
    }

    public static RecipeEntity fromDtoToEntity(RecipeDto recipeDto) {
        if (recipeDto == null) {
            return null;
        }

        return RecipeEntity.builder()
                .recipeName(recipeDto.getRecipeName())
                .ingredientList(recipeDto.getIngredientList())
                .build();
    }

    public static RecipeDto fromEntityToDto(RecipeEntity recipeEntity) {
        if (recipeEntity == null) {
            return null;
        }

        return RecipeDto.builder()
                .id(recipeEntity.getId())
                .recipeName(recipeEntity.getRecipeName())
                .ingredientList(recipeEntity.getIngredientList())
                .build();
    }

    public static RecipeResponse fromDtoToResponse(RecipeDto recipeDto) {
        if (recipeDto == null) {
            return null;
        }

        return RecipeResponse.builder()
                .id(recipeDto.getId())
                .recipeName(recipeDto.getRecipeName())
                .ingredientList(recipeDto.getIngredientList())
                .build();
    }

}
