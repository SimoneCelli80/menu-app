package com.sogeti.menu.app.utils.database;

import com.sogeti.menu.app.utils.helpers.JsonFileReader;
import com.sogeti.menu.app.persistence.entities.RecipeEntity;
import com.sogeti.menu.app.persistence.repositories.RecipesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import java.util.List;

@Profile("seeder")
@SpringBootApplication(scanBasePackages = "com")
public class DBSeeder implements CommandLineRunner {

    private final RecipesRepository recipesRepository;


    public DBSeeder(RecipesRepository recipesRepository) {
        this.recipesRepository = recipesRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(DBSeeder.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        seedRecipe();
        System.exit(0);
    }

    private void seedRecipe() {
        List<RecipeEntity> recipeData = JsonFileReader.deserializeToListOfObjects("recipes.json", RecipeEntity.class);

        for (RecipeEntity recipe : recipeData) {
            recipesRepository.save(recipe);
        }
    }
}

