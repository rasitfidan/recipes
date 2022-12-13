package com.abn_amro.exercise.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO object of the Recipe entity
 */
public class RecipeDTO {
    private Integer id;
    private String description;
    private String name;
    private Boolean vegetarian;
    private Integer servingFor;

    private List<IngredientDTO> ingredientDTOList = new ArrayList<>();
    private List<InstructionDTO> instructionDTOList = new ArrayList<>();

    public void setId(Integer id) {
        this.id=id;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public void setVegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServingFor(Integer servingFor) {
        this.servingFor = servingFor;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Boolean isVegetarian() {
        return vegetarian;
    }

    public Integer getServingFor() {
        return servingFor;
    }

    public List<IngredientDTO> getIngredients() {
        return ingredientDTOList;
    }

    public List<InstructionDTO> getInstructions() {
        return instructionDTOList;
    }

    public void setIngredients( List<IngredientDTO> ingredientDTOList) {
        this.ingredientDTOList = ingredientDTOList;
    }

    public void setInstructions(List<InstructionDTO> instructionDTOList) {
        this.instructionDTOList = instructionDTOList;
    }

    public void addIngredient( IngredientDTO ingredientDTO) {
        if(ingredientDTO !=null) {
            ingredientDTOList.add(ingredientDTO);
        }
    }

    public void addInstruction(InstructionDTO instructionDTO) {
        if(instructionDTO !=null) {
            instructionDTOList.add(instructionDTO);
        }
    }
}
