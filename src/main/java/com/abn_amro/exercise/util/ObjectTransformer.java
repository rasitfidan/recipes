package com.abn_amro.exercise.util;

import com.abn_amro.exercise.dto.IngredientDTO;
import com.abn_amro.exercise.dto.InstructionDTO;
import com.abn_amro.exercise.dto.RecipeDTO;
import com.abn_amro.exercise.entity.Ingredient;
import com.abn_amro.exercise.entity.Instruction;
import com.abn_amro.exercise.entity.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Utility class to transforn Entity classes to DTO classes.
 */
public class ObjectTransformer {
    private static final Logger log = LoggerFactory.getLogger(ObjectTransformer.class);
    private ObjectTransformer() {}

    /**
     * Transforms Recipe entity to Recipe DTO object
     * @param recipe
     * @return
     */
    public static RecipeDTO transformToRecipeDTO(Recipe recipe) {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(recipe.getId());
        dto.setDescription(recipe.getDescription());
        dto.setName(recipe.getName());
        dto.setVegetarian(recipe.isVegetarian());
        dto.setServingFor(recipe.getServingFor());

        recipe.getInstructions().stream().map(ins -> {
            InstructionDTO instructionDTO = new InstructionDTO();
            instructionDTO.setId(ins.getId());
            instructionDTO.setDescription(ins.getDescription());
            instructionDTO.setSequence(ins.getSequence());
            return instructionDTO;
        }).forEach(dto::addInstruction);

        recipe.getIngredients().stream().map(ing -> {
            IngredientDTO ingredientDTO = new IngredientDTO();
            ingredientDTO.setId(ing.getId());
            ingredientDTO.setItemName(ing.getItemName());
            ingredientDTO.setDescription(ing.getDescription());
            ingredientDTO.setAmount(ing.getAmount());
            ingredientDTO.setAmountType(ing.getAmountType());

            return ingredientDTO;
        }).forEach(dto::addIngredient);

        Collections.sort(dto.getInstructions(), Comparator.comparing(InstructionDTO::getSequence));

        log.info("Recipe entity to Recipe DTO conpleted");

        return dto;
    }

    /**
     * Transforms DTO object to Entity object. One difference from above is it takes an matching Enetity class from database
     * It is being used for add and update operations
     * @param recipe
     * @param recipeDTO
     * @return
     */
    public static Recipe transformToRecipeEntity(Recipe recipe ,RecipeDTO recipeDTO) {
        if(recipe == null) {
            recipe = new Recipe();
        }

        recipe.setDescription(recipeDTO.getDescription());
        recipe.setName(recipeDTO.getName());
        recipe.setVegetarian(recipeDTO.isVegetarian());
        recipe.setServingFor(recipeDTO.getServingFor());

        List<Ingredient> ingredients = setIngredientsEntityFields(recipe.getIngredients() , recipeDTO.getIngredients());
        List<Instruction> instructions = setInstructionsEntityFields(recipe.getInstructions(),recipeDTO.getInstructions());

        recipe.setIngredients(ingredients);
        recipe.setInstructions(instructions);

        log.info("Recipe DTO to Recipe entity conpleted");

        return recipe;
    }

    /**
     * Transforms instruction entities and sets its fields matching from the DTO objects
     * @param instructionEntities
     * @param instructions
     * @return
     */
    private static List<Instruction> setInstructionsEntityFields(List<Instruction> instructionEntities,List<InstructionDTO> instructions) {
        List<Instruction> instructionEntitiesUpdated = new ArrayList<>();

        int sequence = 1;

        for(InstructionDTO instructionDTO : instructions) {
            Instruction instruction = null;
            if(instructionDTO.getId() != null && instructionEntities != null) {
                Optional<Instruction> instOpt = instructionEntities.stream().filter(ints -> ints.getId()!=null && instructionDTO.getId() !=null && (ints.getId().equals(instructionDTO.getId()))).findFirst();

                if(instOpt.isPresent()) {
                    instruction = instOpt.get();
                }
            }

            instruction = instruction == null ? new Instruction():instruction;

            instruction.setDescription(instructionDTO.getDescription());

            instruction.setSequence(sequence++);

            instructionEntitiesUpdated.add(instruction);
        }

        log.info("instruction Entities crated and updated using dto objects. And created a new list!");
        return instructionEntitiesUpdated;
    }

    /**
     * Transforms and set ingredient entity fields matching from the DTO objects
     * @param ingredientEntities
     * @param ingredients
     * @return
     */
    private static List<Ingredient> setIngredientsEntityFields(List<Ingredient> ingredientEntities , List<IngredientDTO> ingredients) {
        List<Ingredient> ingredientEntitiesUpdated = new ArrayList<>();

        for(IngredientDTO ingredientDTO : ingredients) {
            Ingredient ingredient = null;
            if(ingredientDTO.getId() != null && ingredientEntities != null) {
                Optional<Ingredient> ingOpt = ingredientEntities.stream().filter(ing -> ing.getId()!=null && ingredientDTO.getId() !=null && (ing.getId().equals(ingredientDTO.getId()))).findFirst();

                if(ingOpt.isPresent()) {
                    ingredient = ingOpt.get();
                }
            }

            ingredient = ingredient == null ? new Ingredient():ingredient;

            ingredient.setItemName(ingredientDTO.getItemName());
            ingredient.setDescription(ingredientDTO.getDescription());
            ingredient.setAmount(ingredientDTO.getAmount());
            ingredient.setAmountType(ingredientDTO.getAmountType());

            ingredientEntitiesUpdated.add(ingredient);
        }
        log.info("ingredient Entities crated and updated using dto objects. And created a new list!");
        return ingredientEntitiesUpdated;
    }
}
