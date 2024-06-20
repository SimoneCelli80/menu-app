package com.sogeti.menu.app.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import javax.print.attribute.standard.DateTimeAtCompleted;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    long id;
    String menuDate;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Menu_Recipe",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    List<RecipeEntity> recipeList;

    public MenuEntity(List<RecipeEntity> recipeList) {
        this.menuDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy 'at' HH:mm"));
        this.recipeList = recipeList != null ? recipeList : new ArrayList<>();
    }
}
