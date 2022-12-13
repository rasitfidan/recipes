package com.abn_amro.exercise.service;

import com.abn_amro.exercise.dto.RecipeRequestDTO;
import com.abn_amro.exercise.entity.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    Optional<Recipe> findById(Integer id);
    List<Recipe> listAll(RecipeRequestDTO recipeRequestDTO);
    void addRecipe(Recipe recipe);
    void updateRecipe(Recipe recipe);
    void deleteRecipe(Integer id);
}
