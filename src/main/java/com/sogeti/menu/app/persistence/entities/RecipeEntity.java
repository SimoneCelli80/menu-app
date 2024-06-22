package com.sogeti.menu.app.persistence.entities;

import jakarta.persistence.*;
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
    private String recipeName;

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
