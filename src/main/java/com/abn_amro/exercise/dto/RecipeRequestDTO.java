package com.abn_amro.exercise.dto;

/**
 * DTO object of the Request parameters
 */
public class RecipeRequestDTO {
    private Boolean vegetarian;

    private Integer servingFor;

    private String[] includingIngrediends;

    private String[] excludingIngrediends;

    private String havingWordInInstructions;
    public Boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public Integer getServingFor() {
        return servingFor;
    }

    public void setServingFor(Integer servingFor) {
        this.servingFor = servingFor;
    }

    public String[] getIncludingIngrediends() {
        return includingIngrediends;
    }

    public void setIncludingIngrediends(String[] includingIngrediends) {
        this.includingIngrediends = includingIngrediends;
    }

    public String[] getExcludingIngrediends() {
        return excludingIngrediends;
    }

    public void setExcludingIngrediends(String[] excludingIngrediends) {
        this.excludingIngrediends = excludingIngrediends;
    }

    public String getHavingWordInInstructions() {
        return havingWordInInstructions;
    }

    public void setHavingWordInInstructions(String havingWordInInstructions) {
        this.havingWordInInstructions = havingWordInInstructions;
    }
}
