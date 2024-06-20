package com.sogeti.menu.app.service;

import com.sogeti.menu.app.mapper.MenuMapper;
import com.sogeti.menu.app.mapper.RecipeMapper;
import com.sogeti.menu.app.persistence.entities.MenuEntity;
import com.sogeti.menu.app.persistence.entities.RecipeEntity;
import com.sogeti.menu.app.persistence.repositories.MenusRepository;
import com.sogeti.menu.app.rest.dtos.MenuDto;
import com.sogeti.menu.app.rest.dtos.RecipeDto;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenusService {

    MenusRepository menusRepository;
    RecipesService recipesService;

    public MenusService(MenusRepository menusRepository, RecipesService recipesService) {
        this.menusRepository = menusRepository;
        this.recipesService = recipesService;
    }

    public MenuDto createMenu(List<Long> idList) {
        List<RecipeDto> recipeDtoList = recipesService.getListOfRecipesByIds(idList);
        List<RecipeEntity> recipeList = recipeDtoList.stream()
                .map(RecipeMapper::fromDtoToEntity)
                .collect(Collectors.toList());

        return MenuMapper.fromEntityToDto(menusRepository.save(new MenuEntity(recipeList)));
    }
}
