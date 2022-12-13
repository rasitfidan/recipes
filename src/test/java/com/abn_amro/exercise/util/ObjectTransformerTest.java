package com.abn_amro.exercise.util;

import com.abn_amro.exercise.dto.RecipeDTO;
import com.abn_amro.exercise.entity.Recipe;
import com.abn_amro.exercise.service.RecipeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ObjectTransformerTest {
    @Autowired
    RecipeService recipeService;
    @Test
    public void testTransformToRecipeDTO() {
        Optional<Recipe> opt = recipeService.findById(1);
        assertTrue(opt.isPresent());

        Recipe recipe = opt.get();
        RecipeDTO dto = ObjectTransformer.transformToRecipeDTO(recipe);

        assertEquals(recipe.getName(),dto.getName());
        assertEquals(recipe.getId(),dto.getId());
        assertEquals(recipe.getDescription(),dto.getDescription());
        assertEquals(recipe.isVegetarian(),dto.isVegetarian());
        assertEquals(recipe.getInstructions().size(),dto.getInstructions().size());
        assertEquals(recipe.getIngredients().size(),dto.getIngredients().size());
    }

    @Test
    public void testTransformToRecipeEntity() {
        Optional<Recipe> opt = recipeService.findById(1);
        assertTrue(opt.isPresent());

        Recipe recipe = opt.get();
        RecipeDTO dto = ObjectTransformer.transformToRecipeDTO(recipe);


        Recipe recipe2 = ObjectTransformer.transformToRecipeEntity(null,dto);

        assertEquals(recipe.getName(),recipe2.getName());
        assertEquals(null,recipe2.getId());
        assertEquals(recipe.getDescription(),recipe2.getDescription());
        assertEquals(recipe.isVegetarian(),recipe2.isVegetarian());
        assertEquals(recipe.getInstructions().size(),recipe2.getInstructions().size());
        assertEquals(recipe.getIngredients().size(),recipe2.getIngredients().size());
    }
}
