package com.sogeti.menu.app.service;

import com.sogeti.menu.app.factories.RecipeFactory;
import com.sogeti.menu.app.mapper.RecipeMapper;
import com.sogeti.menu.app.persistence.entities.RecipeEntity;
import com.sogeti.menu.app.persistence.repositories.RecipesRepository;
import com.sogeti.menu.app.rest.dtos.RecipeDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipesServiceTest {

    @Mock
    RecipesRepository recipesRepository;
    @InjectMocks
    RecipesService recipesService;

    @Test
    void givenANewRecipeDto_whenCreatingARecipe_thenTheRecipeIsSavedAndTheDtoReturned() {
        RecipeDto recipeDto = RecipeFactory.aRecipeDto().build();
        RecipeEntity recipeEntity = RecipeMapper.fromDtoToEntity(recipeDto);

        when(recipesRepository.save(any(RecipeEntity.class))).thenReturn(RecipeFactory.aRecipe().build());
        RecipeDto resultDto = recipesService.createRecipe(recipeDto);

        assertEquals(recipeDto.getId(), resultDto.getId());
        assertEquals(recipeDto.getRecipeName(), resultDto.getRecipeName());
        assertEquals(recipeDto.getIngredientList().size(), resultDto.getIngredientList().size());

        verify(recipesRepository).existsByRecipeName(anyString());
        verify(recipesRepository).save(any(RecipeEntity.class));
        verifyNoMoreInteractions(recipesRepository);
    }

}
