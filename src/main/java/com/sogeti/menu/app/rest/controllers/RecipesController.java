package com.sogeti.menu.app.rest.controllers;

import com.sogeti.menu.app.mapper.RecipeMapper;
import com.sogeti.menu.app.rest.dtos.RecipeDto;
import com.sogeti.menu.app.rest.requests.RecipeRequest;
import com.sogeti.menu.app.rest.responses.RecipeResponse;
import com.sogeti.menu.app.service.RecipesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/recipes")
public class RecipesController {

    private final RecipesService recipeService;

    public RecipesController(RecipesService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/add")
    ResponseEntity<RecipeResponse> createRecipe(@RequestBody @Valid RecipeRequest recipeRequest) {
        RecipeDto recipeDto = recipeService.createRecipe(RecipeMapper.fromRequestToDto(recipeRequest));
        return new ResponseEntity<>(RecipeMapper.fromDtoToResponse(recipeDto), HttpStatus.CREATED);
    }

    @GetMapping("/{recipeId}")
    ResponseEntity<RecipeResponse> getRecipeById(@PathVariable long recipeId) {
        RecipeDto recipeDto = recipeService.getRecipeById(recipeId);
        return new ResponseEntity<>(RecipeMapper.fromDtoToResponse(recipeDto), HttpStatus.OK);
    }

    @GetMapping("/ids")
    ResponseEntity<List<RecipeResponse>> getListOfRecipesByIds(@RequestParam List<Long> ids) {
        List<RecipeDto> recipeDtoList = recipeService.getListOfRecipesByIds(ids);
        return new ResponseEntity<>(recipeDtoList.stream().map(RecipeMapper::fromDtoToResponse).collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{recipeId}")
    ResponseEntity<Void> deleteRecipeById(@PathVariable Long recipeId) {
        return recipeService.deleteRecipeById(recipeId);
    }

    @GetMapping
    ResponseEntity<List<RecipeResponse>> getAllRecipes() {
        return new ResponseEntity<>(recipeService.getAllRecipes()
                .stream()
                .map(RecipeMapper::fromDtoToResponse)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/edit/{recipeId}")
    ResponseEntity<RecipeResponse> editRecipe(@PathVariable Long recipeId, @RequestBody RecipeRequest recipeRequest) {
        RecipeDto recipeDto = RecipeMapper.fromRequestToDto(recipeRequest);
        return new ResponseEntity<>(RecipeMapper.fromDtoToResponse(recipeService.editRecipe(recipeId, recipeDto)), HttpStatus.OK);
    }



}
