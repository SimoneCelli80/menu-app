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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
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
    void givenARecipeWithAUsedName_whenCreatingARecipe_thenA409ShouldBeThrown() {
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

    @Test
    void givenAValidListOfIds_whenGettingMoreThanOneRecipe_thenAListOfRecipeDtosShouldBeReturned() {
        RecipeDto recipeDto1 = RecipeFactory.aRecipeDto().build();
        RecipeDto recipeDto2 = RecipeFactory.aRecipeDto().id(2L).build();
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        recipeDtoList.add(recipeDto1);
        recipeDtoList.add(recipeDto2);
        List<RecipeEntity> recipeEntityList = new ArrayList<>();
        RecipeEntity recipeEntity1 = RecipeFactory.aRecipe().build();
        RecipeEntity recipeEntity2 = RecipeFactory.aRecipe().id(2L).build();
        recipeEntityList.add(recipeEntity1);
        recipeEntityList.add(recipeEntity2);
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        idList.add(2L);

        when(recipesRepository.findAllById(idList)).thenReturn(recipeEntityList);
        List<RecipeDto> returnedRecipeDtoList = recipesService.getListOfRecipesByIds(idList);
        assertEquals(recipeDtoList.getFirst().getId(), returnedRecipeDtoList.getFirst().getId());
        assertEquals(recipeDtoList.getFirst().getRecipeName(), returnedRecipeDtoList.getFirst().getRecipeName());
        assertEquals(recipeDtoList.getFirst().getIngredientList().size(), returnedRecipeDtoList.getFirst().getIngredientList().size());
        assertEquals(recipeDtoList.getLast().getId(), returnedRecipeDtoList.getLast().getId());
        assertEquals(recipeDtoList.getLast().getRecipeName(), returnedRecipeDtoList.getLast().getRecipeName());
        assertEquals(recipeDtoList.getLast().getIngredientList().size(), returnedRecipeDtoList.getLast().getIngredientList().size());
        verify(recipesRepository).findAllById(idList);
        verifyNoMoreInteractions(recipesRepository);
    }

    @Test
    void givenAListWithAtLeastANonexistentId_whenGettingMoreThanOneRecipe_thenA404ShouldBeThrown() {
        List<Long> idList = new ArrayList<>();
        idList.add(1L);
        idList.add(1000L);
        List<RecipeEntity> recipeEntityList = new ArrayList<>();
        RecipeEntity recipeEntity1 = RecipeFactory.aRecipe().build();
        recipeEntityList.add(recipeEntity1);
        ResponseStatusException expectedException = new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, 1 recipe has not be founded.");

        when(recipesRepository.findAllById(idList)).thenReturn(recipeEntityList);
        ResponseStatusException thrownException = assertThrows(ResponseStatusException.class, ()-> recipesService.getListOfRecipesByIds(idList));

        assertEquals(thrownException.getStatusCode(), expectedException.getStatusCode());
        assertEquals(thrownException.getMessage(), expectedException.getMessage());
        verify(recipesRepository).findAllById(idList);
        verifyNoMoreInteractions(recipesRepository);
    }

    @Test
    void givenANotEmptyRepository_whenGettingAllTheRecipes_thenAListWithAllTheRecipesShouldBeReturned() {
        RecipeDto recipeDto1 = RecipeFactory.aRecipeDto().build();
        RecipeDto recipeDto2 = RecipeFactory.aRecipeDto().id(2L).build();
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        recipeDtoList.add(recipeDto1);
        recipeDtoList.add(recipeDto2);
        List<RecipeEntity> recipeEntityList = new ArrayList<>();
        RecipeEntity recipeEntity1 = RecipeFactory.aRecipe().build();
        RecipeEntity recipeEntity2 = RecipeFactory.aRecipe().id(2L).build();
        recipeEntityList.add(recipeEntity1);
        recipeEntityList.add(recipeEntity2);

        when(recipesRepository.findAll()).thenReturn(recipeEntityList);
        List<RecipeDto> returnedRecipeDtoList = recipesService.getAllRecipes();

        assertEquals(recipeDtoList.getFirst().getId(), returnedRecipeDtoList.getFirst().getId());
        assertEquals(recipeDtoList.getFirst().getRecipeName(), returnedRecipeDtoList.getFirst().getRecipeName());
        assertEquals(recipeDtoList.getFirst().getIngredientList().size(), returnedRecipeDtoList.getFirst().getIngredientList().size());
        assertEquals(recipeDtoList.getLast().getId(), returnedRecipeDtoList.getLast().getId());
        assertEquals(recipeDtoList.getLast().getRecipeName(), returnedRecipeDtoList.getLast().getRecipeName());
        assertEquals(recipeDtoList.getLast().getIngredientList().size(), returnedRecipeDtoList.getLast().getIngredientList().size());
        verify(recipesRepository).findAll();
        verifyNoMoreInteractions(recipesRepository);

    }

    @Test
    void givenAnEmptyRepository_whenGettingAllTheRecipes_thenAnEmptyListShouldBeReturned() {
        List<RecipeEntity> emptyList = new ArrayList<>();

        when(recipesRepository.findAll()).thenReturn(emptyList);
        List<RecipeDto> returnedRecipeDtoList = recipesService.getAllRecipes();
        assertTrue(returnedRecipeDtoList.isEmpty());

        verify(recipesRepository).findAll();
        verifyNoMoreInteractions(recipesRepository);
    }

    @Test
    void givenAValidId_whenDeletingARecipe_thenA204ShouldBeThrown() {
        ResponseEntity<Void> expectedResponseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        long recipeId = 1L;
        when(recipesRepository.existsById(recipeId)).thenReturn(true);
        doNothing().when(recipesRepository).deleteById(recipeId);

        ResponseEntity<Void> thrownResponseEntity = recipesService.deleteRecipeById(recipeId);

        assertEquals(thrownResponseEntity, expectedResponseEntity);
        verify(recipesRepository).existsById(recipeId);
        verify(recipesRepository).deleteById(recipeId);
        verifyNoMoreInteractions(recipesRepository);
    }

    @Test
    void givenANonexistentId_whenDeletingARecipe_thenA404ShouldBeThrown() {
        ResponseEntity<Void> expectedResponseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        long recipeId = 1L;
        when(recipesRepository.existsById(recipeId)).thenReturn(false);

        ResponseEntity<Void> thrownResponseEntity = recipesService.deleteRecipeById(recipeId);

        assertEquals(thrownResponseEntity, expectedResponseEntity);
        verify(recipesRepository).existsById(recipeId);
        verifyNoMoreInteractions(recipesRepository);

    }

    @Test
    void givenAValidIdAndAValidRecipeDto_whenEditingARecipe_thenTheEditedRecipeShouldBeReturned() {
        RecipeDto recipeDtoToEdit = RecipeFactory.aRecipeDto().build();
        RecipeEntity recipeEntity = RecipeFactory.aRecipe().recipeName("Lasagne").build();
        RecipeDto expectedRecipeDto = RecipeFactory.aRecipeDto().recipeName("Lasagne").build();

        when(recipesRepository.existsById(recipeDtoToEdit.getId())).thenReturn(true);
        when(recipesRepository.save(any(RecipeEntity.class))).thenReturn(recipeEntity);

        RecipeDto returnedDto = recipesService.editRecipe(recipeDtoToEdit.getId(), expectedRecipeDto);
        assertEquals(returnedDto.getRecipeName(), expectedRecipeDto.getRecipeName());
        verify(recipesRepository).existsById(recipeDtoToEdit.getId());
        verify(recipesRepository).save(any(RecipeEntity.class));
        verifyNoMoreInteractions(recipesRepository);
    }

    @Test
    void givenANonexistentIdAndAValidRecipeDto_whenEditingARecipe_thenA404ShouldBeThrown() {
        RecipeDto recipeDtoToEdit = RecipeFactory.aRecipeDto().build();
        ResponseStatusException expectedException = new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, the recipe with id 1000 is not in the repository");

        when(recipesRepository.existsById(1000L)).thenReturn(false);
        ResponseStatusException thrownException = assertThrows(ResponseStatusException.class, () -> recipesService.editRecipe(1000L, recipeDtoToEdit));

        assertEquals(thrownException.getStatusCode(), expectedException.getStatusCode());
        assertEquals(thrownException.getMessage(), expectedException.getMessage());
        verify(recipesRepository).existsById(1000L);
        verifyNoMoreInteractions(recipesRepository);
    }


}
