package com.abn_amro.exercise.repo;

import com.abn_amro.exercise.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
}
