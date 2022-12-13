package com.abn_amro.exercise.api;

import com.abn_amro.exercise.dto.MessageResponse;
import com.abn_amro.exercise.dto.RecipeDTO;
import com.abn_amro.exercise.dto.RecipeRequestDTO;
import com.abn_amro.exercise.entity.Recipe;
import com.abn_amro.exercise.service.RecipeService;
import com.abn_amro.exercise.util.ObjectTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.abn_amro.exercise.util.ObjectTransformer.transformToRecipeDTO;
import static com.abn_amro.exercise.util.ObjectTransformer.transformToRecipeEntity;

/**
 * Controller implementation of Recipe api service to manage add, update, remove and list the recipes
 */
@RestController
public class RecipeApiController implements RecipeApi {
    private static final Logger log = LoggerFactory.getLogger(RecipeApiController.class);

    private static final String NOT_FOUND_ENTITY_MSG = "Recipe with id : [%s] could not be found!";
    private static final String ADDED_SUCCESSFULLY_MSG = "Recipe with id [%s] added successfully!";
    private static final String UPDATED_SUCCESSFULL_MSG = "Recipe with id [%s] updated successfully!";
    private static final String DELETED_SUCCESSFULL_MSG = "Recipe with id : [%s] deleted successfully!";
    private final RecipeService recipeService;
    @Autowired
    public RecipeApiController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Adds recipe entity and its children to database
     * @param recipeDTO
     * @return
     */
    @Override
    public ResponseEntity<MessageResponse> addRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = transformToRecipeEntity(null ,recipeDTO);

        recipeService.addRecipe(recipe);

        String messageText = String.format(ADDED_SUCCESSFULLY_MSG,recipe.getId());
        MessageResponse response = new MessageResponse();
        response.setCode(HttpStatus.OK.value());
        response.setText(messageText);
        response.setDate(LocalDateTime.now());

        log.info(messageText);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * updates the recipe entity and updates children as well
     * @param recipeDTO
     * @return
     */
    @Override
    public ResponseEntity<MessageResponse> updateRecipe(RecipeDTO recipeDTO) {
        Optional<Recipe> entityOpt = recipeService.findById(recipeDTO.getId());

        Recipe recipe = entityOpt.isPresent() ? entityOpt.get() : new Recipe();

        transformToRecipeEntity(recipe ,recipeDTO);

        recipeService.updateRecipe(recipe);

        String messageText = String.format(UPDATED_SUCCESSFULL_MSG,recipe.getId());
        MessageResponse response = new MessageResponse();
        response.setCode(HttpStatus.OK.value());
        response.setText(messageText);
        response.setDate(LocalDateTime.now());

        log.info(messageText);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Deletes the recipe entity from database with the id
     * @param recipeId
     * @return
     */
    @Override
    public ResponseEntity<MessageResponse> deleteRecipe(Integer recipeId) {
        MessageResponse response = new MessageResponse();

        try {
            recipeService.deleteRecipe(recipeId);
        } catch(EmptyResultDataAccessException ex) {
            String messageText = String.format(NOT_FOUND_ENTITY_MSG,recipeId);

            response.setCode(HttpStatus.NOT_FOUND.value());
            response.setText(messageText);
            response.setDate(LocalDateTime.now());

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        String messageText = String.format(DELETED_SUCCESSFULL_MSG,recipeId);

        response.setCode(HttpStatus.OK.value());
        response.setText(messageText);
        response.setDate(LocalDateTime.now());

        log.info(messageText);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Lists all recipes from the database based on Request parameters.
     * @param recipeRequestDTO
     * @return
     */
    @Override
    public ResponseEntity<List<RecipeDTO>> listRecipes(RecipeRequestDTO recipeRequestDTO) {
        List<Recipe> recipes = recipeService.listAll(recipeRequestDTO);

        List<RecipeDTO> dtos = recipes.stream().map(ObjectTransformer::transformToRecipeDTO).collect(Collectors.toList());
        log.info("all recipes listed");
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    /**
     * Fetches single recipe matching with the id
     *
     * @param recipeId
     * @return
     */
    @Override
    public ResponseEntity<Object> getRecipe(Integer recipeId) {
        Optional<Recipe> entityOpt = recipeService.findById(recipeId);

        if(entityOpt.isPresent()) {
            return new ResponseEntity<>(transformToRecipeDTO(entityOpt.get()), HttpStatus.OK);
        }

        String messageText = String.format(NOT_FOUND_ENTITY_MSG,recipeId);

        MessageResponse response = new MessageResponse();
        response.setCode(HttpStatus.NOT_FOUND.value());
        response.setText(messageText);
        response.setDate(LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
