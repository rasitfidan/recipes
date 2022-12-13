package com.abn_amro.exercise.entity;

import com.abn_amro.exercise.types.AmountType;

import javax.persistence.*;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipeOfIngredient;
    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "amount", nullable = false)
    private Float amount;

    @Column(name = "amount_type", nullable = false)
    private AmountType amountType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Recipe getRecipeOfIngredient() {
        return recipeOfIngredient;
    }

    public void setRecipeOfIngredient(Recipe recipeOfIngredient) {
        this.recipeOfIngredient = recipeOfIngredient;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public AmountType getAmountType() {
        return amountType;
    }

    public void setAmountType(AmountType amountType) {
        this.amountType = amountType;
    }
}
