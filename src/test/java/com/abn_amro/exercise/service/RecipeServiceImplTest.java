package com.abn_amro.exercise.service;

import com.abn_amro.exercise.dto.RecipeRequestDTO;
import com.abn_amro.exercise.entity.Recipe;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class RecipeServiceImplTest {
    @Autowired RecipeServiceImpl recipeService;

    @Test
    public void testfindById() {
        Optional<Recipe> opt = recipeService.findById(1);
        assertTrue(opt.isPresent());

        Recipe recipe = opt.get();
        assertEquals(1,recipe.getId().intValue());
        assertEquals("Omelet",recipe.getName());
        assertFalse(recipe.getIngredients().isEmpty());
        assertFalse(recipe.getDescription().isEmpty());
    }

    @Test
    public void testListAll() {
        RecipeRequestDTO recipeRequestDTO = new RecipeRequestDTO();

        List<Recipe>  list = recipeService.listAll(recipeRequestDTO);
        assertFalse(list.isEmpty());
        assertEquals(5,list.size());

        Recipe recipe = list.get(0);
        assertEquals(1,recipe.getId().intValue());
        assertEquals("Omelet",recipe.getName());
        assertFalse(recipe.getIngredients().isEmpty());
        assertFalse(recipe.getDescription().isEmpty());
    }

    @Test
    public void testAddRecipe() {
        List<Recipe>  list = recipeService.listAll(new RecipeRequestDTO());

        int recordCount = list.size();

        Recipe recipe = new Recipe();

        recipe.setName("Test");
        recipe.setDescription("Test Desc");
        recipe.setServingFor(2);
        recipe.setVegetarian(true);

        recipeService.addRecipe(recipe);

        list = recipeService.listAll(new RecipeRequestDTO());

        assertEquals(recordCount+1 , list.size());

        Optional<Recipe> optional = list.stream().filter(rec -> rec.getName().equals(recipe.getName())).findFirst();

        assertTrue(optional.isPresent());

    }

    @Test
    public void updateRecipe() {
        Optional<Recipe> opt = recipeService.findById(1);
        assertTrue(opt.isPresent());

        Recipe recipe = opt.get();

        String testName = "Test Name";

        recipe.setName(testName);

        recipeService.updateRecipe(recipe);

        opt = recipeService.findById(1);
        assertTrue(opt.isPresent());

        Recipe recipe2 = opt.get();

        assertEquals(recipe.getId() ,recipe2.getId());

        assertEquals(testName ,recipe2.getName());
    }

    @Test
    public void testDeleteRecipe() {
        assertTrue(recipeService.findById(1).isPresent());

        recipeService.deleteRecipe(1);

        assertFalse(recipeService.findById(1).isPresent());

    }
}
