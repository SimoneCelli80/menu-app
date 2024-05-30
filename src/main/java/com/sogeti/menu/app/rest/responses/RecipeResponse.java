package com.sogeti.menu.app.rest.responses;

import com.sogeti.menu.app.persistence.entities.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.List;

@Builder
@AllArgsConstructor
public class RecipeResponse {

    private long id;
    private String recipeName;
    private List<Ingredient> ingredientList;

}
