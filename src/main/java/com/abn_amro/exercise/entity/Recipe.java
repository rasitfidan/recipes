package com.abn_amro.exercise.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private String name;
    private String description;
    private Boolean vegetarian;
    @Column(name = "serving_for", nullable = false)
    private Integer servingFor;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "recipeOfIngredient", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "recipeOfInstruction", cascade = CascadeType.ALL)
    private List<Instruction> instructions;

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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVegetarian(Boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public void setServingFor(Integer servingFor) {
        this.servingFor = servingFor;
    }

    public List<Ingredient> getIngredients() {
        return ingredients == null ? Collections.emptyList() : ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        final Recipe thisRecipe = this;

        ingredients.forEach(ing -> ing.setRecipeOfIngredient(thisRecipe));

        this.ingredients = ingredients;
    }

    public List<Instruction> getInstructions() {
        return instructions == null ? Collections.emptyList() : instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        Collections.sort(instructions, Comparator.comparing(Instruction::getSequence));

        final Recipe thisRecipe = this;
        
        instructions.stream().forEach(ins -> ins.setRecipeOfInstruction(thisRecipe));

        this.instructions = instructions;
    }
}
