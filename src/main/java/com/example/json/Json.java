package com.example.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
    //Will be used across the class to operate the .json file
    private static ObjectMapper objectMapper = getDefaultObjectMapper();

    //Used to create the ObjectMapper. Can be configured in comparation to constructor.
    private static ObjectMapper getDefaultObjectMapper() {
        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return defaultObjectMapper;
    }

    //Method to parse a String to a JsonNOde which keeps the information to operate afterwards.
    public static JsonNode parse(String src) throws JsonProcessingException {
        return objectMapper.readTree(src);
    }

    //Method that reads the information from a JsonNode and inserts it to a class.
    //Returns the type of the class to which it is atributed.
    public static <A> A fromJson(JsonNode node, Class<A> clazz) throws JsonProcessingException {
        return objectMapper.treeToValue(node, clazz);
    }
}
