package com.sogeti.menu.app.rest.dtos;

import com.sogeti.menu.app.persistence.entities.Ingredient;
import com.sogeti.menu.app.persistence.entities.RecipeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Builder
@AllArgsConstructor
@Getter
@Setter
public class RecipeDto {

    private long id;
    private String recipeName;
    private List<Ingredient> ingredientList;

}
