package com.sogeti.menu.app.rest.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/recipes")
public class RecipesController {

    @PostMapping("/add")
    RecipeResponse createRecipe(@RequestBody RecipeRequest recipeRequest) {
        return recipeService.createRecipe(recipeRequest);
    }

}
