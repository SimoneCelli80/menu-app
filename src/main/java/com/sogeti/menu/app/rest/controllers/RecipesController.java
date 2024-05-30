package com.sogeti.menu.app.rest.controllers;

import com.sogeti.menu.app.mapper.RecipeMapper;
import com.sogeti.menu.app.rest.dtos.RecipeDto;
import com.sogeti.menu.app.rest.requests.RecipeRequest;
import com.sogeti.menu.app.rest.responses.RecipeResponse;
import com.sogeti.menu.app.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/recipes")
public class RecipesController {

    private final RecipeService recipeService;

    public RecipesController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/add")
    ResponseEntity<RecipeResponse> createRecipe(@RequestBody RecipeRequest recipeRequest) {
        RecipeDto recipeDto = recipeService.createRecipe(RecipeMapper.fromRequestToDto(recipeRequest));
        return new ResponseEntity<>(RecipeMapper.fromDtoToResponse(recipeDto), HttpStatus.CREATED);
    }

}
