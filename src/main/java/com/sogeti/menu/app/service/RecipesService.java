package com.sogeti.menu.app.service;

import com.sogeti.menu.app.mapper.IngredientMapper;
import com.sogeti.menu.app.mapper.RecipeMapper;
import com.sogeti.menu.app.persistence.entities.IngredientEntity;
import com.sogeti.menu.app.persistence.entities.RecipeEntity;
import com.sogeti.menu.app.persistence.repositories.RecipesRepository;
import com.sogeti.menu.app.rest.dtos.RecipeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipesService {

    private final RecipesRepository recipesRepository;

    public RecipesService(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }
    public RecipeDto createRecipe(RecipeDto recipeDto) {
        if (recipesRepository.existsByRecipeName(recipeDto.getRecipeName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This recipe already exists, please choose a different recipe or change its name.");
        }
        RecipeEntity recipeEntity = recipesRepository.save(RecipeMapper.fromDtoToEntity(recipeDto));
        return RecipeMapper.fromEntityToDto(recipeEntity);
    }

    public RecipeDto getRecipeById(long recipeId) {
        RecipeEntity recipe = recipesRepository.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Sorry, the recipe with id %d is not in the repository.", recipeId)));
        return RecipeMapper.fromEntityToDto(recipe);
    }

    public List<RecipeDto> getListOfRecipesByIds(List<Long> ids) {
        List<RecipeEntity> recipeEntityList = recipesRepository.findAllById(ids);

        int notFoundRecipesNumber = ids.size() - recipeEntityList.size();
        String verbAndNumber = notFoundRecipesNumber > 1 ? "recipes have" : "recipe has";
        if (notFoundRecipesNumber != 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Sorry, %d %s not be founded.", notFoundRecipesNumber, verbAndNumber));
        }

        return recipeEntityList.stream().map(RecipeMapper::fromEntityToDto).collect(Collectors.toList());
    }

    public List<RecipeDto> getAllRecipes() {
        List<RecipeEntity> allRecipes = recipesRepository.findAll();
        return allRecipes.stream()
                .map(RecipeMapper::fromEntityToDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<Void> deleteRecipeById(Long recipeId) {
        if (recipesRepository.existsById(recipeId)) {
            recipesRepository.deleteById(recipeId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public RecipeDto editRecipe(Long recipeId, RecipeDto recipe) {
        if (!recipesRepository.existsById(recipeId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        List<IngredientEntity> ingredientEntityList= recipe.getIngredientList()
                .stream()
                .map(IngredientMapper::fromDtoToEntity)
                .collect(Collectors.toList());
        RecipeEntity editedRecipe = RecipeEntity.builder()
                .id(recipeId)
                .recipeName(recipe.getRecipeName())
                .ingredientList(ingredientEntityList)
                .build();
        recipesRepository.save(editedRecipe);
        return RecipeMapper.fromEntityToDto(editedRecipe);
    }
}
