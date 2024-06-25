package com.sogeti.menu.app.mapper;

import com.sogeti.menu.app.persistence.entities.IngredientEntity;
import com.sogeti.menu.app.persistence.entities.RecipeEntity;
import com.sogeti.menu.app.rest.dtos.IngredientDto;
import com.sogeti.menu.app.rest.dtos.RecipeDto;
import com.sogeti.menu.app.rest.requests.RecipeRequest;
import com.sogeti.menu.app.rest.responses.IngredientResponse;
import com.sogeti.menu.app.rest.responses.RecipeResponse;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeMapper {

    public static RecipeDto fromRequestToDto(RecipeRequest recipeRequest) {
        if (recipeRequest == null)  {
            return null;
        }

        List<IngredientDto> ingredientDtoList = recipeRequest.ingredientList()
                .stream()
                .map(IngredientMapper::fromRequestToDto)
                .collect(Collectors.toList());

        return RecipeDto.builder()
                .recipeName(recipeRequest.recipeName())
                .ingredientList(ingredientDtoList)
                .build();
    }

    public static RecipeEntity fromDtoToEntity(RecipeDto recipeDto) {
        if (recipeDto == null) {
            return null;
        }

        List<IngredientEntity> ingredientEntityList = recipeDto.getIngredientList()
                .stream()
                .map(IngredientMapper::fromDtoToEntity)
                .collect(Collectors.toList());

        return RecipeEntity.builder()
                .recipeName(recipeDto.getRecipeName())
                .ingredientList(ingredientEntityList)
                .build();
    }

    public static RecipeDto fromEntityToDto(RecipeEntity recipeEntity) {
        if (recipeEntity == null) {
            return null;
        }

        List<IngredientDto> ingredientDtoList = recipeEntity.getIngredientList()
                .stream()
                .map(IngredientMapper::fromEntityToDto)
                .collect(Collectors.toList());

        return RecipeDto.builder()
                .id(recipeEntity.getId())
                .recipeName(recipeEntity.getRecipeName())
                .ingredientList(ingredientDtoList)
                .build();
    }

    public static RecipeResponse fromDtoToResponse(RecipeDto recipeDto) {
        if (recipeDto == null) {
            return null;
        }

        List<IngredientResponse> ingredientResponseList = recipeDto.getIngredientList()
                .stream()
                .map(IngredientMapper::fromDtoToResponse)
                .collect(Collectors.toList());

        return RecipeResponse.builder()
                .id(recipeDto.getId())
                .recipeName(recipeDto.getRecipeName())
                .ingredientList(ingredientResponseList)
                .build();
    }

}
