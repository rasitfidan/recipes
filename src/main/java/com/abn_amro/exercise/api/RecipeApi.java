package com.abn_amro.exercise.api;

import com.abn_amro.exercise.dto.MessageResponse;
import com.abn_amro.exercise.dto.RecipeDTO;
import com.abn_amro.exercise.dto.RecipeRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/recipe")
public interface RecipeApi {
    @PostMapping(path = "" , consumes = {"application/json"} , produces = {"application/json"})
    ResponseEntity<MessageResponse> addRecipe(@RequestBody RecipeDTO recipeDTO);

    @PutMapping(path = "" , consumes = {"application/json"} , produces = {"application/json"})
    ResponseEntity<MessageResponse> updateRecipe(@RequestBody RecipeDTO recipeDTO);

    @DeleteMapping(path = "/{recipeId}" ,  produces = {"application/json"})
    ResponseEntity<MessageResponse> deleteRecipe(@PathVariable Integer recipeId);

    @GetMapping(path = "" , produces = {"application/json"})
    ResponseEntity<List<RecipeDTO>> listRecipes(RecipeRequestDTO recipeRequestDTO);

    @GetMapping(path = "/{recipeId}" , produces = {"application/json"})
    ResponseEntity<Object> getRecipe(@PathVariable Integer recipeId);
}