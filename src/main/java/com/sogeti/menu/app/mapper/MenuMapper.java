package com.sogeti.menu.app.mapper;

import com.sogeti.menu.app.persistence.entities.MenuEntity;
import com.sogeti.menu.app.persistence.entities.RecipeEntity;
import com.sogeti.menu.app.rest.dtos.MenuDto;
import com.sogeti.menu.app.rest.dtos.RecipeDto;
import com.sogeti.menu.app.rest.responses.MenuResponse;
import com.sogeti.menu.app.rest.responses.RecipeResponse;

import java.util.List;
import java.util.stream.Collectors;

public class MenuMapper {

    public static MenuDto fromEntityToDto(MenuEntity menuEntity) {
        List<RecipeDto> recipeDtoList = menuEntity.getRecipeList()
                .stream()
                .map(RecipeMapper::fromEntityToDto)
                .collect(Collectors.toList());

        return MenuDto.builder()
                .id(menuEntity.getId())
                .menuDate(menuEntity.getMenuDate())
                .recipeDtoList(recipeDtoList)
                .build();
    }

    public static MenuResponse fromDtoToResponse(MenuDto menuDto) {
        List<RecipeResponse> recipeResponseList = menuDto.getRecipeDtoList()
                .stream()
                .map(RecipeMapper::fromDtoToResponse)
                .collect(Collectors.toList());

        return MenuResponse.builder()
                .id(menuDto.getId())
                .menuDate(menuDto.getMenuDate())
                .recipeResponseList(recipeResponseList)
                .build();
    }

    public static MenuEntity fromDtoToEntity(MenuDto menuDto) {
        List<RecipeEntity> recipeEntityList = menuDto.getRecipeDtoList()
                .stream()
                .map(RecipeMapper::fromDtoToEntity)
                .collect(Collectors.toList());

        return MenuEntity.builder()
                .id(menuDto.getId())
                .menuDate(menuDto.getMenuDate())
                .recipeList(recipeEntityList)
                .build();
    }

}
