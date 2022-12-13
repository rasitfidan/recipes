package com.abn_amro.exercise.util;

import com.abn_amro.exercise.dto.RecipeRequestDTO;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueryBuilderTest {
    @Test
    public void testCreateQuery() {
        RecipeRequestDTO recipeRequestDTO = new RecipeRequestDTO();

        String sql = QueryBuilder.createQuery(recipeRequestDTO).trim();
        assertEquals("SELECT * FROM RECIPE REC WHERE 1=1",sql);

        recipeRequestDTO.setVegetarian(true);

        sql = QueryBuilder.createQuery(recipeRequestDTO).trim();
        assertEquals("SELECT * FROM RECIPE REC WHERE 1=1  AND REC.VEGETARIAN = true",sql);

        recipeRequestDTO = new RecipeRequestDTO();

        recipeRequestDTO.setServingFor(2);

        sql = QueryBuilder.createQuery(recipeRequestDTO).trim();
        assertEquals("SELECT * FROM RECIPE REC WHERE 1=1  AND REC.SERVING_FOR = 2",sql);

        recipeRequestDTO = new RecipeRequestDTO();
        recipeRequestDTO.setIncludingIngrediends(new String[] {"salmon"});

        sql = QueryBuilder.createQuery(recipeRequestDTO).trim();
        assertEquals("SELECT * FROM RECIPE REC WHERE 1=1  AND EXISTS (SELECT 1 FROM INGREDIENT ING WHERE UPPER(ING.ITEM_NAME) IN ( 'SALMON')  AND REC.ID = ING.RECIPE_ID )",sql);

        recipeRequestDTO = new RecipeRequestDTO();
        recipeRequestDTO.setExcludingIngrediends(new String[] {"salmon"});
        sql = QueryBuilder.createQuery(recipeRequestDTO).trim();
        assertEquals("SELECT * FROM RECIPE REC WHERE 1=1  AND NOT EXISTS (SELECT 1 FROM INGREDIENT ING WHERE UPPER(ING.ITEM_NAME) IN ( 'SALMON')  AND REC.ID = ING.RECIPE_ID )",sql);

        recipeRequestDTO = new RecipeRequestDTO();
        recipeRequestDTO.setHavingWordInInstructions("bake");
        sql = QueryBuilder.createQuery(recipeRequestDTO).trim();
        assertEquals("SELECT * FROM RECIPE REC WHERE 1=1  AND EXISTS (SELECT 1 FROM INSTRUCTION INS WHERE UPPER(INS.DESCRIPTION) LIKE '%BAKE%'  AND REC.ID = INS.RECIPE_ID )",sql);

    }

}
