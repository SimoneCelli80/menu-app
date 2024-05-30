package com.sogeti.menu.app.service;

import com.sogeti.menu.app.mapper.RecipeMapper;
import com.sogeti.menu.app.persistence.repositories.RecipesRepository;
import com.sogeti.menu.app.rest.dtos.RecipeDto;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

    private final RecipesRepository recipesRepository;

    public RecipeService(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }
    public RecipeDto createRecipe(RecipeDto recipeDto) {
        recipesRepository.save(RecipeMapper.fromDtoToEntity(recipeDto));
        return recipeDto;
    }

}
