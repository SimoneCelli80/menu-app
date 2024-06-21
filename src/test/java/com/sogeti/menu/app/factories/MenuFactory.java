package com.sogeti.menu.app.factories;

import com.sogeti.menu.app.persistence.entities.MenuEntity;
import com.sogeti.menu.app.persistence.entities.RecipeEntity;
import com.sogeti.menu.app.rest.dtos.MenuDto;
import com.sogeti.menu.app.rest.dtos.RecipeDto;
import com.sogeti.menu.app.rest.responses.MenuResponse;
import com.sogeti.menu.app.rest.responses.RecipeResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MenuFactory {
    static String date = "juni 20, 2024 at 01:44";

    public  static MenuEntity.MenuEntityBuilder aMenuEntity() {
        List<RecipeEntity> recipeList = new ArrayList<>();
        recipeList.add(RecipeFactory.aRecipe().build());
        recipeList.add(RecipeFactory.aRecipe().id(2L).build());

        return MenuEntity.builder()
                .id(1L)
                .menuDate(date)
                .recipeList(recipeList);
    }

    public static MenuDto.MenuDtoBuilder aMenuDto() {
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        recipeDtoList.add(RecipeFactory.aRecipeDto().build());
        recipeDtoList.add(RecipeFactory.aRecipeDto().id(2L).build());

        return MenuDto.builder()
                .id(1L)
                .menuDate(date)
                .recipeDtoList(recipeDtoList);
    }

    public static MenuResponse.MenuResponseBuilder aMenuResponse() {
        List<RecipeResponse> recipeResponseList = new ArrayList<>();
        recipeResponseList.add(RecipeFactory.aRecipeResponse().build());
        recipeResponseList.add(RecipeFactory.aRecipeResponse().id(2L).build());

        return MenuResponse.builder()
                .id(1L)
                .menuDate(date)
                .recipeResponseList(recipeResponseList);
    }

}
