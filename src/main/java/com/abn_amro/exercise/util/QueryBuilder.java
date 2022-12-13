package com.abn_amro.exercise.util;

import com.abn_amro.exercise.dto.RecipeRequestDTO;

import java.util.Arrays;
import java.util.Optional;

/**
 * Utility class to create dynamic native recipe select queries
 */
public class QueryBuilder {

    private QueryBuilder() {}
    /**
     * Takes url Request parameters and creates a native query using request parameters.
     * The select query is being used to retrieve the recipes
     * @return
     */
    public static String createQuery(RecipeRequestDTO recipeRequestDTO) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM RECIPE REC WHERE 1=1 ");

        Optional.ofNullable(recipeRequestDTO.isVegetarian()).ifPresent(val ->
            builder.append(" AND REC.VEGETARIAN = " + val)
        );

        Optional.ofNullable(recipeRequestDTO.getServingFor()).ifPresent(val ->
            builder.append(" AND REC.SERVING_FOR = " + val)
        );

        Optional.ofNullable(recipeRequestDTO.getIncludingIngrediends()).ifPresent(val -> {
            builder.append(" AND EXISTS (SELECT 1 FROM INGREDIENT ING WHERE UPPER(ING.ITEM_NAME) IN ( ");

            Arrays.stream(val).forEach(arrVal -> builder.append("'").append(arrVal.toUpperCase()).append("'").append(","));
            builder.deleteCharAt(builder.length()-1);

            builder.append(") ").append(" AND REC.ID = ING.RECIPE_ID ");
            builder.append(")");
        });

        Optional.ofNullable(recipeRequestDTO.getExcludingIngrediends()).ifPresent(val -> {
            builder.append(" AND NOT EXISTS (SELECT 1 FROM INGREDIENT ING WHERE UPPER(ING.ITEM_NAME) IN ( ");

            Arrays.stream(val).forEach(arrVal -> builder.append("'").append(arrVal.toUpperCase()).append("'").append(","));
            builder.deleteCharAt(builder.length()-1);

            builder.append(") ").append(" AND REC.ID = ING.RECIPE_ID ");
            builder.append(")");
        });

        Optional.ofNullable(recipeRequestDTO.getHavingWordInInstructions()).ifPresent(val ->
            builder.append(" AND EXISTS (SELECT 1 FROM INSTRUCTION INS WHERE UPPER(INS.DESCRIPTION) LIKE '%").append(val.toUpperCase()).append("%' ").append(" AND REC.ID = INS.RECIPE_ID )")
        );

        return builder.toString();
    }
}
