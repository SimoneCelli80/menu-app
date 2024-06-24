package com.sogeti.menu.app.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    @NotNull(message = "Please enter the name of the recipe.")
    @NotBlank(message = "Please enter the name of the recipe.")
    private String recipeName;

    @NotNull(message = "Please enter the name of the ingredient.")
    @NotEmpty(message = "Please enter at least one ingredient for the recipe.")
    @ElementCollection
    @CollectionTable(name = "ingredient", joinColumns = @JoinColumn(name = "recipe_id"))
    private List<IngredientEntity> ingredientList;


    public RecipeEntity(String recipeName, List<IngredientEntity> ingredientList) {
        this.recipeName = recipeName;
        this.ingredientList = ingredientList;
    }



    public long getId() {
        return id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public List<IngredientEntity> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<IngredientEntity> ingredientList) {
        this.ingredientList = ingredientList;
    }
}
