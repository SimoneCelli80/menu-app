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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MenusController.class)
public class MenusControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    MenusService menusService;
    @Mock
    RecipesService recipesService;

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


}
