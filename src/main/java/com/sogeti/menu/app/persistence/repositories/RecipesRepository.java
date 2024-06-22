package com.sogeti.menu.app.persistence.repositories;

import com.sogeti.menu.app.persistence.entities.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipesRepository extends JpaRepository<RecipeEntity, Long> {
    boolean existsByRecipeName(String recipeName);
}
