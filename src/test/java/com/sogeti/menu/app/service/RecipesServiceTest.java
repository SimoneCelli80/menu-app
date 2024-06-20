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

import java.util.Optional;

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
        ResponseStatusException thrownException = assertThrows(ResponseStatusException.class, ()-> recipesService.createRecipe(recipeDto));

        assertEquals(expectedException.getStatusCode(), thrownException.getStatusCode());
        assertEquals(expectedException.getMessage(), thrownException.getMessage());

        verify(recipesRepository).existsByRecipeName(recipeDto.getRecipeName());
        verifyNoMoreInteractions(recipesRepository);
    }

    @Test
    void givenAValidId_whenGettingARecipe_thenTheDtoOfTheRecipeIsReturned() {
        RecipeDto recipeDto = RecipeFactory.aRecipeDto().build();
        RecipeEntity recipeEntity = RecipeFactory.aRecipe().build();
        Optional<RecipeEntity> recipeEntityOptional = Optional.of(recipeEntity);

        when(recipesRepository.findById(recipeDto.getId())).thenReturn(recipeEntityOptional);
        RecipeDto returnedRecipeDto = recipesService.getRecipeById(recipeDto.getId());

        assertEquals(recipeDto.getId(), returnedRecipeDto.getId());
        assertEquals(recipeDto.getRecipeName(), returnedRecipeDto.getRecipeName());
        assertEquals(recipeDto.getIngredientList().size(), returnedRecipeDto.getIngredientList().size());

        verify(recipesRepository).findById(recipeDto.getId());
        verifyNoMoreInteractions(recipesRepository);
    }

    @Test
    void givenANonexistentId_whenGettingARecipe_thenA404ShouldBeThrown() {
        long nonexistentId = 1L;
        Optional<RecipeEntity> emptyOptional = Optional.empty();
        ResponseStatusException expectedException = new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, the recipe with id " + nonexistentId + " is not in the repository.");

        when(recipesRepository.findById(nonexistentId)).thenReturn(emptyOptional);
        ResponseStatusException thrownException = assertThrows(ResponseStatusException.class, ()-> recipesService.getRecipeById(nonexistentId));

        assertEquals(expectedException.getStatusCode(), thrownException.getStatusCode());
        assertEquals(expectedException.getMessage(), thrownException.getMessage());

        verify(recipesRepository).findById(nonexistentId);
        verifyNoMoreInteractions(recipesRepository);
    }

}
