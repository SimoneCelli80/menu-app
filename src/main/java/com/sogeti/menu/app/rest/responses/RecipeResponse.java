package com.sogeti.menu.app.rest.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class RecipeResponse {

    private long id;
    private String recipeName;
    private List<IngredientResponse> ingredientList;

}
