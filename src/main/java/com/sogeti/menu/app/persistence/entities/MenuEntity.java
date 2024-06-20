package com.sogeti.menu.app.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    Date menuDate;
    @ManyToMany
    @JoinTable(
            name = "Menu_Recipe",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    List<RecipeEntity> recipeList;

    public MenuEntity(List<RecipeEntity> recipeList) {
        this.menuDate = new Date();
        this.recipeList = recipeList != null ? recipeList : new ArrayList<>();
    }
}
