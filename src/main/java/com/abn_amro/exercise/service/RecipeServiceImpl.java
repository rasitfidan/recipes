package com.abn_amro.exercise.service;

import com.abn_amro.exercise.dto.RecipeRequestDTO;
import com.abn_amro.exercise.entity.Recipe;
import com.abn_amro.exercise.repo.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

import static com.abn_amro.exercise.util.QueryBuilder.createQuery;

/**
 * Service class to manage database operations
 */
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final EntityManager entityManager;
    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, EntityManager entityManager) {
        this.recipeRepository = recipeRepository;
        this.entityManager = entityManager;
    }

    /**
     * finds single recipe by id
     * @param id
     * @return
     */
    public Optional<Recipe> findById(Integer id) {
        return recipeRepository.findById(id);
    }

    /**
     * Lists all recipes based on Request parameters.
     * @param recipeRequestDTO
     * @return
     */
    public List<Recipe> listAll(RecipeRequestDTO recipeRequestDTO) {
        String sql = createQuery(recipeRequestDTO);

        Query query = entityManager.createNativeQuery(sql , Recipe.class);

        return query.getResultList();
    }

    /**
     * Adds a recipe to the database
     * @param recipe
     */
    public void addRecipe(Recipe recipe) {
        recipeRepository.saveAndFlush(recipe);
    }

    /**
     * updates recipe and ins children
     * @param recipe
     */
    public void updateRecipe(Recipe recipe) {
        recipeRepository.saveAndFlush(recipe);
    }

    /**
     * deletes recipe entity
     * @param id
     */
    public void deleteRecipe(Integer id) {
        recipeRepository.deleteById(id);
    }
}
