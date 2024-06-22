package com.sogeti.menu.app.rest.controllers;

import com.sogeti.menu.app.mapper.MenuMapper;
import com.sogeti.menu.app.persistence.entities.MenuEntity;
import com.sogeti.menu.app.rest.dtos.MenuDto;
import com.sogeti.menu.app.rest.requests.RecipeIdsRequest;
import com.sogeti.menu.app.rest.responses.MenuResponse;
import com.sogeti.menu.app.service.MenusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/menus")
public class MenusController {

    MenusService menuService;

    public MenusController(MenusService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/add")
    ResponseEntity<MenuResponse> createMenu(@RequestBody RecipeIdsRequest recipeIdsRequest) {
        List<Long> recipeIdList = recipeIdsRequest.recipeIdList();
        MenuDto menuDto = menuService.createMenu(recipeIdList);
        System.out.println(menuDto.getMenuDate() + "here");
        return new ResponseEntity<>(MenuMapper.fromDtoToResponse(menuDto), HttpStatus.CREATED);
    }

    @GetMapping("/{menuId}")
    ResponseEntity<MenuResponse> getMenuById(@PathVariable long menuId) {
        MenuDto menuDto = menuService.getMenuById(menuId);
        return new ResponseEntity<>(MenuMapper.fromDtoToResponse(menuDto), HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<List<MenuResponse>> getAllMenus() {
        List<MenuDto> menuDtoList = menuService.getAllMenus();
        return new ResponseEntity<>(menuDtoList.stream().map(MenuMapper::fromDtoToResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<Void> deleteMenuById(@PathVariable long id) {
        return menuService.deleteMenuById(id);

    }
}
