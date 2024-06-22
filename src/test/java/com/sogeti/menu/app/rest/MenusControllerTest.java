package com.sogeti.menu.app.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sogeti.menu.app.factories.MenuFactory;
import com.sogeti.menu.app.rest.controllers.MenusController;
import com.sogeti.menu.app.rest.dtos.MenuDto;
import com.sogeti.menu.app.rest.requests.RecipeIdsRequest;
import com.sogeti.menu.app.service.MenusService;
import com.sogeti.menu.app.service.RecipesService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MenusController.class)
public class MenusControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    MenusService menusService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenAValidRequest_whenCreatingAMenu_thenAMenuResponseShouldBeReturned() throws Exception {
        List<Long> recipeIdList = new ArrayList<>();
        recipeIdList.add(1L);
        recipeIdList.add(2L);
        MenuDto menuDto = MenuFactory.aMenuDto().build();

        when(menusService.createMenu(recipeIdList)).thenReturn(menuDto);

        RecipeIdsRequest recipeIdsRequest = new RecipeIdsRequest(recipeIdList);
        mockMvc.perform(post("/api/menus/add")
                        .content(objectMapper.writeValueAsString(recipeIdsRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(menuDto.getId()))
                .andExpect(jsonPath("$.menuDate").value(menuDto.getMenuDate()))
                .andExpect(jsonPath("$.recipeResponseList.[0]id").value(menuDto.getRecipeDtoList().getFirst().getId()))
                .andExpect(jsonPath("$.recipeResponseList.[0]recipeName").value(menuDto.getRecipeDtoList().getFirst().getRecipeName()))
                .andExpect(jsonPath("$.recipeResponseList.[1]id").value(menuDto.getRecipeDtoList().getLast().getId()))
                .andExpect(jsonPath("$.recipeResponseList.[1]recipeName").value(menuDto.getRecipeDtoList().getLast().getRecipeName()));

        verify(menusService, times(1)).createMenu(recipeIdList);
        verifyNoMoreInteractions(menusService);

    }

    @Test
    void givenARequestWithANonexistentRecipeId_whenCreatingAMenu_thenA404ShouldBeThrown() throws Exception {
        List<Long> recipeIdList = new ArrayList<>();
        recipeIdList.add(1L);
        recipeIdList.add(1000L);
        MenuDto menuDto = MenuFactory.aMenuDto().build();

        when(menusService.createMenu(recipeIdList)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

        RecipeIdsRequest recipeIdsRequest = new RecipeIdsRequest(recipeIdList);
        mockMvc.perform(post("/api/menus/add")
                        .content(objectMapper.writeValueAsString(recipeIdsRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());

        verify(menusService, times(1)).createMenu(recipeIdList);
        verifyNoMoreInteractions(menusService);

    }

    @Test
    void givenAValidId_whenGettingAMenu_thenAMenuResponseShouldBeReturned() throws Exception {
        MenuDto menuDto = MenuFactory.aMenuDto().build();

        when(menusService.getMenuById(menuDto.getId())).thenReturn(menuDto);

        mockMvc.perform(get("/api/menus/" + menuDto.getId())
                        .content(objectMapper.writeValueAsString(menuDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(menuDto.getId()))
                .andExpect(jsonPath("$.menuDate").value(menuDto.getMenuDate()))
                .andExpect(jsonPath("$.recipeResponseList.[0]id").value(menuDto.getRecipeDtoList().getFirst().getId()))
                .andExpect(jsonPath("$.recipeResponseList.[0]recipeName").value(menuDto.getRecipeDtoList().getFirst().getRecipeName()))
                .andExpect(jsonPath("$.recipeResponseList.[1]id").value(menuDto.getRecipeDtoList().getLast().getId()))
                .andExpect(jsonPath("$.recipeResponseList.[1]recipeName").value(menuDto.getRecipeDtoList().getLast().getRecipeName()));
        verify(menusService, times(1)).getMenuById(menuDto.getId());
        verifyNoMoreInteractions(menusService);

    }

    @Test
    void givenANotEmptyRepository_whenGettingAllMenus_thenAListOfMenuResponseShouldBeReturned() throws Exception {
        List<MenuDto> menuDtoList = new ArrayList<>();
        MenuDto menuDto1 = MenuFactory.aMenuDto().build();
        MenuDto menuDto2 = MenuFactory.aMenuDto().id(2L).build();
        menuDtoList.add(menuDto1);
        menuDtoList.add(menuDto2);

        when(menusService.getAllMenus()).thenReturn(menuDtoList);

        mockMvc.perform(get("/api/menus")
                        .content(objectMapper.writeValueAsString(menuDtoList))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]id").value(menuDtoList.getFirst().getId()))
                .andExpect(jsonPath("$.[0]menuDate").value(menuDtoList.getFirst().getMenuDate()))
                .andExpect(jsonPath("$.[0]recipeResponseList.[0]id").value(menuDtoList.getFirst().getRecipeDtoList().getFirst().getId()))
                .andExpect(jsonPath("$.[0]recipeResponseList.[0]recipeName").value(menuDtoList.getFirst().getRecipeDtoList().getFirst().getRecipeName()))
                .andExpect(jsonPath("$.[0]recipeResponseList.[1]id").value(menuDtoList.getFirst().getRecipeDtoList().getLast().getId()))
                .andExpect(jsonPath("$.[0]recipeResponseList.[1]recipeName").value(menuDtoList.getFirst().getRecipeDtoList().getLast().getRecipeName()))
                .andExpect(jsonPath("$.[1]id").value(menuDtoList.getLast().getId()))
                .andExpect(jsonPath("$.[1]menuDate").value(menuDtoList.getLast().getMenuDate()))
                .andExpect(jsonPath("$.[1]recipeResponseList.[0]id").value(menuDtoList.getLast().getRecipeDtoList().getFirst().getId()))
                .andExpect(jsonPath("$.[1]recipeResponseList.[0]recipeName").value(menuDtoList.getLast().getRecipeDtoList().getFirst().getRecipeName()))
                .andExpect(jsonPath("$.[1]recipeResponseList.[1]id").value(menuDtoList.getLast().getRecipeDtoList().getLast().getId()))
                .andExpect(jsonPath("$.[1]recipeResponseList.[1]recipeName").value(menuDtoList.getLast().getRecipeDtoList().getLast().getRecipeName()));

        verify(menusService, times(1)).getAllMenus();
        verifyNoMoreInteractions(menusService);

    }

    @Test
    void givenAnEmptyRepository_whenGettingAllMenus_thenAnEmptyListShouldBeReturned() throws Exception {
        List<MenuDto> emptyList = new ArrayList<>();

        when(menusService.getAllMenus()).thenReturn(emptyList);
        mockMvc.perform(get("/api/menus")
                        .content(objectMapper.writeValueAsString(emptyList))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
        verify(menusService, times(1)).getAllMenus();
        verifyNoMoreInteractions(menusService);
    }

    @Test
    void givenAValidId_whenDeletingAMenu_thenA204ShouldBeReturned() throws Exception {
        long menuId = 1L;
        when(menusService.deleteMenuById(menuId)).thenReturn(new ResponseEntity<>(HttpStatus.NO_CONTENT));

        mockMvc.perform(delete("/api/menus/delete/" + menuId))
                .andDo(print())
                .andExpect(status().isNoContent());
        verify(menusService, times(1)).deleteMenuById(menuId);
        verifyNoMoreInteractions(menusService);
    }

    @Test
    void givenANonexistentId_whenDeletingAMenu_thenA404ShouldBeReturned() throws Exception {
        long nonexistentId = 1L;
        when(menusService.deleteMenuById(nonexistentId)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        mockMvc.perform(delete("/api/menus/delete/" + nonexistentId))
                .andDo(print())
                .andExpect(status().isNotFound());
        verify(menusService, times(1)).deleteMenuById(nonexistentId);
        verifyNoMoreInteractions(menusService);
    }





}
