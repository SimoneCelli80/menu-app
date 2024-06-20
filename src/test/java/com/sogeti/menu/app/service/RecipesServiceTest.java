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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

        when(recipesRepository.save(any(RecipeEntity.class))).thenReturn(RecipeFactory.aRecipe().build());
        RecipeDto resultDto = recipesService.createRecipe(recipeDto);

        assertEquals(recipeDto.getId(), resultDto.getId());
        assertEquals(recipeDto.getRecipeName(), resultDto.getRecipeName());
        assertEquals(recipeDto.getIngredientList().size(), resultDto.getIngredientList().size());

        verify(recipesRepository).existsByRecipeName(anyString());
        verify(recipesRepository).save(any(RecipeEntity.class));
        verifyNoMoreInteractions(recipesRepository);
    }

    @Test
    void givenARecipeWithAUsedName_whenCreatingANewRecipe_thenA409ShouldBeThrown() {
        RecipeDto recipeDto = RecipeFactory.aRecipeDto().recipeName("Name already existing").build();
        ResponseStatusException expectedException = new ResponseStatusException(HttpStatus.CONFLICT, "This recipe already exists, please choose a different recipe or change its name.");

        when(recipesRepository.existsByRecipeName(recipeDto.getRecipeName())).thenReturn(true);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, ()-> recipesService.createRecipe(recipeDto));

        assertEquals(expectedException.getStatusCode(), exception.getStatusCode());
        assertEquals(expectedException.getMessage(), exception.getMessage());

        verify(recipesRepository).existsByRecipeName(recipeDto.getRecipeName());
        verifyNoMoreInteractions(recipesRepository);
    }

}
