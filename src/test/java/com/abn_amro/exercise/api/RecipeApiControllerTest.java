package com.abn_amro.exercise.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class RecipeApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private ResultActions addNewRecipe(String recDescription) throws Exception {
        String content = "{"+
                "\"description\": \""+recDescription+"\","+
                "\"name\": \"Omelet\","+
                "\"vegetarian\": false,"+
                "\"servingFor\": 2,"+
                "\"ingredients\": [{"+
                "\"itemName\":\"egg\","+
                "\"description\":\"fresh farm eggs\","+
                "\"amount\":3,"+
                "\"amountType\":\"PIECE\""+
                "},{"+
                "\"itemName\":\"oil\","+
                "\"description\":\"butter is the best\","+
                "\"amount\":2,"+
                "\"amountType\":\"TABLESPOON\""+
                "},{"+
                "\"itemName\":\"salt\","+
                "\"description\":\"salt\","+
                "\"amount\":1,"+
                "\"amountType\":\"TEASPOON\""+
                "}],"+
                "\"instructions\": [{"+
                "\"description\":\"put oil on a pan. heat it up.\""+
                "},{"+
                "\"description\":\"break eggs creafuly\""+
                "},{"+
                "\"description\":\"cook 3-5 mins\""+
                "},{"+
                "\"description\":\"get it off the fire. add salt put on the plates bon appetit \""+
                "}]"+
                "}";

        return mockMvc.perform(MockMvcRequestBuilders.post("/recipe").contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON));
    }
    @Test
    public void testAddRecipe() throws Exception {
        int newId = 6;
        String recDescription =  "A plain omelet recipe";

        ResultActions actions = addNewRecipe(recDescription);

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.text").value("Recipe with id ["+newId+"] added successfully!"));


        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/"+newId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(recDescription))
                .andExpect(jsonPath("$.name").value("Omelet"))
                .andExpect(jsonPath("$.id").value(newId));

        mockMvc.perform(MockMvcRequestBuilders.delete("/recipe/"+newId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    public void testUpdateRecipe() throws Exception {
        int id = 6;
        String recDescription =  "A plain omelet recipe";

        ResultActions actions = addNewRecipe(recDescription);

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.text").value("Recipe with id ["+id+"] added successfully!"));

        String newRecDesc = "Vegetable omelet recipe";
        String content = "{"+
                "\"id\": "+id+","+
                "\"description\": \""+newRecDesc+"\","+
                "\"name\": \"Omelet\","+
                "\"vegetarian\": false,"+
                "\"servingFor\": 2,"+
                "\"ingredients\": ["+
                "{"+
                "\"id\": 38,"+
                "\"itemName\": \"egg\","+
                "\"description\": \"fresh farm eggs\","+
                "\"amount\": 3.0,"+
                "\"amountType\": \"PIECE\""+
                "},"+
                "{"+
                "\"id\": 39,"+
                "\"itemName\": \"oil\","+
                "\"description\": \"butter is the best\","+
                "\"amount\": 2.0,"+
                "\"amountType\": \"TABLESPOON\""+
                "},"+
                "{"+
                "\"id\": 40,"+
                "\"itemName\": \"salt\","+
                "\"description\": \"salt\","+
                "\"amount\": 1.0,"+
                "\"amountType\": \"TEASPOON\""+
                "},"+
                "{"+
                "\"itemName\": \"dill\","+
                "\"description\": \"dill\","+
                "\"amount\": 1,"+
                "\"amountType\": \"PIECE\""+
                "}"+
                ","+
                "{"+
                "\"itemName\": \"pepper\","+
                "\"description\": \"pepper\","+
                "\"amount\": 1,"+
                "\"amountType\": \"PIECE\""+
                "}"+
                "],"+
                "\"instructions\": ["+
                "{"+
                "\"id\": 19,"+
                "\"description\": \"put oil on a pan. heat it up.\","+
                "\"sequence\": 1"+
                "},"+
                "{"+
                "\"description\": \"chop dill and pepper. fry together\","+
                "\"sequence\": 2"+
                "},"+
                "{"+
                "\"id\": 20,"+
                "\"description\": \"break eggs creafuly\","+
                "\"sequence\": 2"+
                "},"+
                "{"+
                "\"id\": 21,"+
                "\"description\": \"cook 3-5 mins\","+
                "\"sequence\": 3"+
                "},"+
                "{"+
                "\"id\": 22,"+
                "\"description\": \"get it off the fire. add salt put on the plates bon appetit \","+
                "\"sequence\": 4"+
                "}"+
                "]"+
                "}";

        mockMvc.perform(MockMvcRequestBuilders.put("/recipe").contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.text").value("Recipe with id ["+id+"] updated successfully!"));


        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value(newRecDesc))
                .andExpect(jsonPath("$.name").value("Omelet"))
                .andExpect(jsonPath("$.id").value(id));

        mockMvc.perform(MockMvcRequestBuilders.delete("/recipe/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        int id = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        mockMvc.perform(MockMvcRequestBuilders.delete("/recipe/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/"+id))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void testGetRecipe() throws Exception {
        int id = 2;

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/"+id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Oven Baked Salmon"));

        id = Integer.MAX_VALUE;

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/"+id))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    public void testListRecipes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe?vegetarian=true"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(4))
                .andExpect(jsonPath("$[0].name").value("Tofu stir-fry"))
                .andExpect(jsonPath("$[0].ingredients").isArray())
                .andExpect(jsonPath("$[0].ingredients").isNotEmpty())
                .andExpect(jsonPath("$[0].instructions").isArray())
                .andExpect(jsonPath("$[0].instructions").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe?servingFor=2"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Omelet"))
                .andExpect(jsonPath("$[0].ingredients").isArray())
                .andExpect(jsonPath("$[0].ingredients").isNotEmpty())
                .andExpect(jsonPath("$[0].instructions").isArray())
                .andExpect(jsonPath("$[0].instructions").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe?includingIngrediends=salmon"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].name").value("Oven Baked Salmon"))
                .andExpect(jsonPath("$[0].ingredients").isArray())
                .andExpect(jsonPath("$[0].ingredients").isNotEmpty())
                .andExpect(jsonPath("$[0].instructions").isArray())
                .andExpect(jsonPath("$[0].instructions").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe?excludingIngrediends=salmon"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Omelet"))
                .andExpect(jsonPath("$[0].ingredients").isArray())
                .andExpect(jsonPath("$[0].ingredients").isNotEmpty())
                .andExpect(jsonPath("$[0].instructions").isArray())
                .andExpect(jsonPath("$[0].instructions").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe?havingWordInInstructions=bake"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(2))
                .andExpect(jsonPath("$[0].name").value("Oven Baked Salmon"))
                .andExpect(jsonPath("$[0].ingredients").isArray())
                .andExpect(jsonPath("$[0].ingredients").isNotEmpty())
                .andExpect(jsonPath("$[0].instructions").isArray())
                .andExpect(jsonPath("$[0].instructions").isNotEmpty());
    }
}
