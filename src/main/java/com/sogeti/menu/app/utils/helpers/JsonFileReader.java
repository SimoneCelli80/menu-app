package com.sogeti.menu.app.utils.helpers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.util.List;

@Service
public class JsonFileReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> JsonNode deserializeToJsonNode(String jsonFileName) {
        try {
            InputStream jsonReader = JsonFileReader.class.getClassLoader().getResourceAsStream(jsonFileName);
            return objectMapper.readTree(jsonReader);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public static <T> T deserializeToObject(String jsonFileName, Class<T> clazz) {
        try {
            InputStream jsonReader = JsonFileReader.class.getClassLoader().getResourceAsStream(jsonFileName);
            return objectMapper.readValue(jsonReader, clazz);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public static <T> List<T> deserializeToListOfObjects(String jsonFileName, Class<T> clazz) {
        try {
            InputStream jsonReader = JsonFileReader.class.getClassLoader().getResourceAsStream(jsonFileName);
            return objectMapper.readValue(jsonReader, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}

