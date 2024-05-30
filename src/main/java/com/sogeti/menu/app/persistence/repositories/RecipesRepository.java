package com.sogeti.menu.app.persistence.repositories;

import com.sogeti.menu.app.persistence.entities.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipesRepository extends JpaRepository<RecipeEntity, Long> {
}
