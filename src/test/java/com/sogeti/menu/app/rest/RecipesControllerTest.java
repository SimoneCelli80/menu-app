package com.sogeti.menu.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sogeti.menu.app.factories.RecipeFactory;
import com.sogeti.menu.app.mapper.IngredientMapper;
import com.sogeti.menu.app.mapper.RecipeMapper;
import com.sogeti.menu.app.persistence.entities.RecipeEntity;
import com.sogeti.menu.app.rest.controllers.RecipesController;
import com.sogeti.menu.app.rest.dtos.IngredientDto;
import com.sogeti.menu.app.rest.dtos.RecipeDto;
import com.sogeti.menu.app.rest.requests.RecipeRequest;
import com.sogeti.menu.app.rest.responses.RecipeResponse;
import com.sogeti.menu.app.service.RecipesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RecipesController.class)
@AutoConfigureMockMvc(addFilters = false)
class RecipesControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    RecipesService recipesService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenExistingRecipesInTheRepository_whenGettingARecipeById_thenTheRecipeShouldBeReturned() throws Exception {
        RecipeEntity recipe = RecipeFactory.aRecipe().build();
        RecipeDto recipeDto = RecipeMapper.fromEntityToDto(recipe);
        RecipeResponse recipeResponse = RecipeMapper.fromDtoToResponse(recipeDto);

        when(recipesService.getRecipeById(recipe.getId())).thenReturn(RecipeMapper.fromEntityToDto(recipe));
        mockMvc.perform(get("/api/recipes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.recipeName").value(recipeResponse.getRecipeName()))
                .andExpect(jsonPath("$.ingredientList[0].name").value(recipeResponse.getIngredientList().getFirst().getName()))
                .andExpect(jsonPath("$.ingredientList[0].quantity").value(recipeResponse.getIngredientList().getFirst().getQuantity()))
                .andExpect(jsonPath("$.ingredientList[0].unit").value(String.valueOf(recipeResponse.getIngredientList().getFirst().getUnit())))
                .andExpect(jsonPath("$.ingredientList[1].name").value(recipeResponse.getIngredientList().getLast().getName()))
                .andExpect(jsonPath("$.ingredientList[1].quantity").value(recipeResponse.getIngredientList().getLast().getQuantity()))
                .andExpect(jsonPath("$.ingredientList[1].unit").value(String.valueOf(recipeResponse.getIngredientList().getLast().getUnit())));

        verify(recipesService, times(1)).getRecipeById(recipe.getId());
        verifyNoMoreInteractions(recipesService);
    }

    @Test
    void givenNoRecipesInTheRepositoryOrAnInvalidId_whenGettingARecipeById_thenA404ShouldBeReturned() throws Exception {
        long invalidId = 1L;

        when(recipesService.getRecipeById(invalidId)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        mockMvc.perform(get("/api/recipes/" + invalidId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(recipesService, times(1)).getRecipeById(invalidId);
        verifyNoMoreInteractions(recipesService);
    }

    @Test
    void givenAValidListOfIds_whenGettingAListOfRecipes_thenAListOfRecipesShouldBeReturned() throws Exception {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        RecipeDto recipe1 = RecipeFactory.aRecipeDto()
                .id(1L)
                .build();
        RecipeDto recipe2 = RecipeFactory.aRecipeDto()
                .id(2L)
                .build();
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        recipeDtoList.add(recipe1);
        recipeDtoList.add(recipe2);

        when(recipesService.getListOfRecipesByIds(ids)).thenReturn(recipeDtoList);
        mockMvc.perform(get("/api/recipes/ids")
                        .param("ids", "1", "2")
                        .content(objectMapper.writeValueAsString(recipeDtoList))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0]id").value(recipeDtoList.getFirst().getId()))
                .andExpect(jsonPath("$.[0]recipeName").value(recipeDtoList.getFirst().getRecipeName()))
                .andExpect(jsonPath("$.[0]ingredientList[0].name").value(recipeDtoList.getFirst().getIngredientList().getFirst().getName()))
                .andExpect(jsonPath("$.[0]ingredientList[0].quantity").value(recipeDtoList.getFirst().getIngredientList().getFirst().getQuantity()))
                .andExpect(jsonPath("$.[0]ingredientList[0].unit").value(String.valueOf(recipeDtoList.getFirst().getIngredientList().getFirst().getUnit())))
                .andExpect(jsonPath("$.[0]ingredientList[1].name").value(recipeDtoList.getFirst().getIngredientList().getLast().getName()))
                .andExpect(jsonPath("$.[0]ingredientList[1].quantity").value(recipeDtoList.getFirst().getIngredientList().getLast().getQuantity()))
                .andExpect(jsonPath("$.[0]ingredientList[1].unit").value(String.valueOf(recipeDtoList.getFirst().getIngredientList().getLast().getUnit())))
                .andExpect(jsonPath("$.[1]id").value(recipeDtoList.getLast().getId()))
                .andExpect(jsonPath("$.[1]recipeName").value(recipeDtoList.getLast().getRecipeName()))
                .andExpect(jsonPath("$.[1]ingredientList[0].name").value(recipeDtoList.getLast().getIngredientList().getFirst().getName()))
                .andExpect(jsonPath("$.[1]ingredientList[0].quantity").value(recipeDtoList.getLast().getIngredientList().getFirst().getQuantity()))
                .andExpect(jsonPath("$.[1]ingredientList[0].unit").value(String.valueOf(recipeDtoList.getLast().getIngredientList().getFirst().getUnit())))
                .andExpect(jsonPath("$.[1]ingredientList[1].name").value(recipeDtoList.getLast().getIngredientList().getLast().getName()))
                .andExpect(jsonPath("$.[1]ingredientList[1].quantity").value(recipeDtoList.getLast().getIngredientList().getLast().getQuantity()))
                .andExpect(jsonPath("$.[1]ingredientList[1].unit").value(String.valueOf(recipeDtoList.getLast().getIngredientList().getLast().getUnit())));

        verify(recipesService, times(1)).getListOfRecipesByIds(ids);
        verifyNoMoreInteractions(recipesService);

    }

    @Test
    void givenALIstOfIdsWithAtLeast1UnmatchingId_whenGettingAListOfRecipes_thenA404ShouldBeReturned() throws Exception {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(3L);

        when(recipesService.getListOfRecipesByIds(ids)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        mockMvc.perform(get("/api/recipes/ids")
                        .param("ids", "1", "3"))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(recipesService, times(1)).getListOfRecipesByIds(ids);
        verifyNoMoreInteractions(recipesService);

    }

    @Test
    void givenExistingRecipesInTheRepository_whenGettingAllTheRecipes_thenAListOfRecipesShouldBeReturned() throws Exception {
        RecipeEntity recipe = RecipeFactory.aRecipe().build();
        RecipeDto recipeDto = RecipeMapper.fromEntityToDto(recipe);
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        recipeDtoList.add(recipeDto);
        List<RecipeResponse> recipeResponseList = recipeDtoList.stream().map(RecipeMapper::fromDtoToResponse).collect(Collectors.toList());

        when(recipesService.getAllRecipes()).thenReturn(recipeDtoList);
        mockMvc.perform(get("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0]recipeName").value(recipeResponseList.getFirst().getRecipeName()))
                .andExpect(jsonPath("$.[0]ingredientList[0].name").value(recipeResponseList.getFirst().getIngredientList().getFirst().getName()))
                .andExpect(jsonPath("$.[0]ingredientList[0].quantity").value(recipeResponseList.getFirst().getIngredientList().getFirst().getQuantity()))
                .andExpect(jsonPath("$.[0]ingredientList[0].unit").value(String.valueOf(recipeResponseList.getFirst().getIngredientList().getFirst().getUnit())))
                .andExpect(jsonPath("$.[0]ingredientList[1].name").value(recipeResponseList.getFirst().getIngredientList().getLast().getName()))
                .andExpect(jsonPath("$.[0]ingredientList[1].quantity").value(recipeResponseList.getFirst().getIngredientList().getLast().getQuantity()))
                .andExpect(jsonPath("$.[0]ingredientList[1].unit").value(String.valueOf(recipeResponseList.getFirst().getIngredientList().getLast().getUnit())));

        verify(recipesService, times(1)).getAllRecipes();
        verifyNoMoreInteractions(recipesService);
    }

    @Test
    void givenAnEmptyRepository_whenGettingAllTheRecipes_thenAnEmptyListShouldBeReturned() throws Exception {
        List<RecipeDto> emptyRecipeList = new ArrayList<>();

        when(recipesService.getAllRecipes()).thenReturn(emptyRecipeList);
        mockMvc.perform(get("/api/recipes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(recipesService, times(1)).getAllRecipes();
        verifyNoMoreInteractions(recipesService);
    }



    @Test
    void givenAValidRequest_whenCreatingARecipe_thenTheNewRecipeShouldBeCreatedAndReturned() throws Exception {
        RecipeRequest recipeRequest = RecipeFactory.aRecipeRequest().build();
        List<IngredientDto> ingredientDtoList = recipeRequest.ingredientList()
                .stream()
                .map(IngredientMapper::fromRequestToDto)
                .collect(Collectors.toList());

        RecipeDto recipeDto = RecipeDto.builder()
                .id(0L)
                .recipeName(recipeRequest.recipeName())
                .ingredientList(ingredientDtoList)
                .build();

        when(recipesService.createRecipe(any(RecipeDto.class))).thenReturn(recipeDto);
        mockMvc.perform(post("/api/recipes/add")
                        .content(objectMapper.writeValueAsString(recipeDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.recipeName").value(recipeDto.getRecipeName()))
                .andExpect(jsonPath("$.ingredientList[0].name").value(recipeDto.getIngredientList().getFirst().getName()))
                .andExpect(jsonPath("$.ingredientList[0].quantity").value(recipeDto.getIngredientList().getFirst().getQuantity()))
                .andExpect(jsonPath("$.ingredientList[0].unit").value(String.valueOf(recipeDto.getIngredientList().getFirst().getUnit())))
                .andExpect(jsonPath("$.ingredientList[1].name").value(recipeDto.getIngredientList().getLast().getName()))
                .andExpect(jsonPath("$.ingredientList[1].quantity").value(recipeDto.getIngredientList().getLast().getQuantity()))
                .andExpect(jsonPath("$.ingredientList[1].unit").value(String.valueOf(recipeDto.getIngredientList().getLast().getUnit())));
        verify(recipesService, times(1)).createRecipe(any(RecipeDto.class));
        verifyNoMoreInteractions(recipesService);
    }

    @Test
    void givenAValidId_whenDeletingARecipe_thenA204ShouldBeReturned() throws Exception {
        long recipeId = 1L;

        when(recipesService.deleteRecipeById(recipeId)).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        mockMvc.perform(delete("/api/recipes/delete/" + recipeId))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(recipesService, times(1)).deleteRecipeById(recipeId);
        verifyNoMoreInteractions(recipesService);
    }

    @Test
    void givenAnInvalidId_whenDeletingARecipe_thenA404ShouldBeReturned() throws Exception {
        long invalidId = 1L;

        when(recipesService.deleteRecipeById(invalidId)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        mockMvc.perform(delete("/api/recipes/delete/" + invalidId))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(recipesService, times(1)).deleteRecipeById(invalidId);
        verifyNoMoreInteractions(recipesService);
    }

    @Test
    void givenAValidRequestAndAValidId_whenEditingARecipe_thenTheEditedRecipeShouldBeReturned() throws Exception {
        RecipeRequest recipeRequest = RecipeFactory.aRecipeRequest().build();
        List<IngredientDto> ingredientDtoList = recipeRequest.ingredientList()
                .stream()
                .map(IngredientMapper::fromRequestToDto)
                .collect(Collectors.toList());
        RecipeDto recipeDto = RecipeDto.builder()
                .id(1L)
                .recipeName(recipeRequest.recipeName())
                .ingredientList(ingredientDtoList)
                .build();

        when(recipesService.editRecipe(anyLong(), any(RecipeDto.class))).thenReturn(recipeDto);
        mockMvc.perform(put("/api/recipes/edit/" + recipeDto.getId())
                    .content(objectMapper.writeValueAsString(recipeDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(recipeDto.getId()))
                .andExpect(jsonPath("$.recipeName").value(recipeDto.getRecipeName()))
                .andExpect(jsonPath("$.ingredientList[0].name").value(recipeDto.getIngredientList().getFirst().getName()))
                .andExpect(jsonPath("$.ingredientList[0].quantity").value(recipeDto.getIngredientList().getFirst().getQuantity()))
                .andExpect(jsonPath("$.ingredientList[0].unit").value(String.valueOf(recipeDto.getIngredientList().getFirst().getUnit())))
                .andExpect(jsonPath("$.ingredientList[1].name").value(recipeDto.getIngredientList().getLast().getName()))
                .andExpect(jsonPath("$.ingredientList[1].quantity").value(recipeDto.getIngredientList().getLast().getQuantity()))
                .andExpect(jsonPath("$.ingredientList[1].unit").value(String.valueOf(recipeDto.getIngredientList().getLast().getUnit())));
        verify(recipesService, times(1)).editRecipe(anyLong(), any(RecipeDto.class));
        verifyNoMoreInteractions(recipesService);
    }

    @Test
    void givenAValidRequestAndANonExistentId_whenEditingARecipe_thenA404ShouldBeReturned() throws Exception {
        long nonExistentId = 1L;
        RecipeRequest recipeRequest = RecipeFactory.aRecipeRequest().build();
        List<IngredientDto> ingredientDtoList = recipeRequest.ingredientList()
                .stream()
                .map(IngredientMapper::fromRequestToDto)
                .collect(Collectors.toList());
        RecipeDto recipeDto = RecipeDto.builder()
                .id(1L)
                .recipeName(recipeRequest.recipeName())
                .ingredientList(ingredientDtoList)
                .build();

        when(recipesService.editRecipe(anyLong(), any(RecipeDto.class))).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        mockMvc.perform(put("/api/recipes/edit/" + nonExistentId)
                    .content(objectMapper.writeValueAsString(recipeRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(recipesService, times(1)).editRecipe(anyLong(), any(RecipeDto.class));
        verifyNoMoreInteractions(recipesService);
    }

}
