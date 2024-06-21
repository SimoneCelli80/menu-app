package com.sogeti.menu.app.service;

import com.sogeti.menu.app.factories.MenuFactory;
import com.sogeti.menu.app.factories.RecipeFactory;
import com.sogeti.menu.app.mapper.MenuMapper;
import com.sogeti.menu.app.persistence.entities.MenuEntity;
import com.sogeti.menu.app.persistence.repositories.MenusRepository;
import com.sogeti.menu.app.rest.dtos.MenuDto;
import com.sogeti.menu.app.rest.dtos.RecipeDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MenusServiceTest {

    @Mock
    MenusRepository menusRepository;
    @InjectMocks
    MenusService menusService;
    @Mock
    RecipesService recipesService;

    @Test
    void givenAValidRecipeIdList_whenCreatingAMenu_thenAMenuDtoShouldBeReturned() {
        List<Long> recipeIdList = new ArrayList<>();
        recipeIdList.add(1L);
        recipeIdList.add(2L);
        MenuDto expectedMenuDto = MenuFactory.aMenuDto().build();
        List<RecipeDto> recipeDtoList = new ArrayList<>();
        recipeDtoList.add(RecipeFactory.aRecipeDto().build());
        recipeDtoList.add(RecipeFactory.aRecipeDto().id(2L).build());
        MenuEntity menuEntity = MenuMapper.fromDtoToEntity(expectedMenuDto);

        when(recipesService.getListOfRecipesByIds(recipeIdList)).thenReturn(recipeDtoList);
        when(menusRepository.save(any(MenuEntity.class))).thenReturn(menuEntity);

        MenuDto returnedMenuDto = menusService.createMenu(recipeIdList);

        assertEquals(returnedMenuDto.getId(), expectedMenuDto.getId());
        assertEquals(returnedMenuDto.getMenuDate(), expectedMenuDto.getMenuDate());
        assertEquals(returnedMenuDto.getRecipeDtoList().size(), expectedMenuDto.getRecipeDtoList().size());
        verify(menusRepository).save(any(MenuEntity.class));
        verifyNoMoreInteractions(menusRepository);

    }

    @Test
    void givenARecipeIdListWithAtLeastANonexistentId_whenCreatingAMenu_thenA404ShouldBeReturned() {
        List<Long> recipeIdList = new ArrayList<>();
        recipeIdList.add(1L);
        recipeIdList.add(1000L);
        ResponseStatusException expectedException = new ResponseStatusException(HttpStatus.NOT_FOUND);

        when(recipesService.getListOfRecipesByIds(recipeIdList)).thenThrow(expectedException);

        ResponseStatusException thrownException =  assertThrows(ResponseStatusException.class, () -> menusService.createMenu(recipeIdList));

        assertEquals(thrownException, expectedException);
        verify(recipesService).getListOfRecipesByIds(recipeIdList);
        verifyNoMoreInteractions(recipesService);
        verifyNoInteractions(menusRepository);

    }

    @Test
    void givenAValidId_whenGettingAMenu_thenAMenuDtoShouldBeReturned() {
        MenuDto expectedMenuDto = MenuFactory.aMenuDto().build();
        MenuEntity menuEntity = MenuMapper.fromDtoToEntity(expectedMenuDto);


        when(menusRepository.findById(expectedMenuDto.getId())).thenReturn(Optional.ofNullable(menuEntity));

        MenuDto returnedMenuDto = menusService.getMenuById(expectedMenuDto.getId());

        assertEquals(returnedMenuDto.getId(), expectedMenuDto.getId());
        assertEquals(returnedMenuDto.getMenuDate(), expectedMenuDto.getMenuDate());
        assertEquals(returnedMenuDto.getRecipeDtoList().size(), expectedMenuDto.getRecipeDtoList().size());

        verify(menusRepository).findById(expectedMenuDto.getId());
        verifyNoMoreInteractions(menusRepository);
    }

    @Test
    void givenANonexistentId_whenGettingAMenu_thenA404ShouldBeReturned() {
        long nonexistentId = 1000L;
        ResponseStatusException expectedException = new ResponseStatusException(HttpStatus.NOT_FOUND, "Sorry, the menu with id 1000 is not in the repository");

        when(menusRepository.findById(nonexistentId)).thenReturn(Optional.empty());

        ResponseStatusException thrownException = assertThrows(ResponseStatusException.class, () -> menusService.getMenuById(nonexistentId));

        assertEquals(thrownException.getStatusCode(), expectedException.getStatusCode());
        assertEquals(thrownException.getMessage(), expectedException.getMessage());

        verify(menusRepository).findById(nonexistentId);
        verifyNoMoreInteractions(menusRepository);
    }

    

}
