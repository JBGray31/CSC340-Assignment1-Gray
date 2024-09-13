package com.CSC340.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class RestAPIController {

    //Returns "Hello World:
    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    //Should return an object called 'bio' with characters' full name and Hogwarts house
    @GetMapping("/characters")
    public Object getBio(@RequestParam(value = "nickname", defaultValue = "Harry") String nickName) {
        try {
            String url = "https://potterapi-fedeperin.vercel.app/en/characters?search=" + nickName;
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String jsonListResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(jsonListResponse);

            //Creates bio object
            Bio bio = new Bio(root.get("fullName").asText(), root.get("hogwartsHouse").asText());

            return bio;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(RestAPIController.class.getName()).log(Level.SEVERE,null, ex);

            return "Cannot find bio";
        }
    }
}
