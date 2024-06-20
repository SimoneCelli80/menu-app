package com.sogeti.menu.app.factories;

import com.sogeti.menu.app.persistence.entities.IngredientEntity;
import com.sogeti.menu.app.persistence.entities.RecipeEntity;
import com.sogeti.menu.app.persistence.enums.UnitsOfMeasureEnum;
import com.sogeti.menu.app.rest.dtos.IngredientDto;
import com.sogeti.menu.app.rest.dtos.RecipeDto;
import com.sogeti.menu.app.rest.requests.IngredientRequest;
import com.sogeti.menu.app.rest.requests.RecipeRequest;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class RecipeFactory {

    public static RecipeEntity.RecipeEntityBuilder aRecipe() {
        List<IngredientEntity> ingredientList = new ArrayList<>();
        ingredientList.add(IngredientEntity.builder().name("Pasta").quantity(500).unit(UnitsOfMeasureEnum.GR).build());
        ingredientList.add(IngredientEntity.builder().name("Tomato sauce").quantity(1).unit(UnitsOfMeasureEnum.L).build());

        return RecipeEntity.builder()
                .id(1L)
                .recipeName("Pasta with tomato sauce")
                .ingredientList(ingredientList);
    }

    public static RecipeDto.RecipeDtoBuilder aRecipeDto() {
        List<IngredientDto> ingredientDtoList = new ArrayList<>();
        ingredientDtoList.add(IngredientDto.builder().name("Pasta").quantity(500).unit(UnitsOfMeasureEnum.GR).build());
        ingredientDtoList.add(IngredientDto.builder().name("Tomato sauce").quantity(1).unit(UnitsOfMeasureEnum.L).build());

        return RecipeDto.builder()
                .id(1L)
                .recipeName("Pasta with tomato sauce")
                .ingredientList(ingredientDtoList);
    }

    public static RecipeRequest.RecipeRequestBuilder aRecipeRequest() {
        List<IngredientRequest> ingredientRequestList = new ArrayList<>();
        ingredientRequestList.add(IngredientRequest.builder().name("Pasta").quantity(500).unit(UnitsOfMeasureEnum.GR).build());
        ingredientRequestList.add(IngredientRequest.builder().name("Tomato sauce").quantity(1).unit(UnitsOfMeasureEnum.L).build());

        return RecipeRequest.builder()
                .recipeName("Pasta with tomato sauce")
                .ingredientList(ingredientRequestList);
    }

}
