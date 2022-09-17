package com.localsearch.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.localsearch.feign.model.CodingSessionInfo;
import com.localsearch.model.BusinessDetails;

import java.io.File;
import java.io.IOException;

public class BusinessTestsUtil {

    public static final ObjectMapper objectMapper = new ObjectMapper();
    private final ClassLoader classLoader = getClass().getClassLoader();

    public String getInputInfoFrom(String fileName) throws IOException {
        File json = new File(classLoader.getResource("files/input/" + fileName).getFile());
        JsonNode jsonNode = objectMapper.readTree(json);
        ObjectReader reader = objectMapper.readerFor(new TypeReference<CodingSessionInfo>() {
        });
        return objectMapper.writeValueAsString(reader.readValue(jsonNode));
    }

    public String getExpectedInfoFrom(String fileName) throws IOException {
        File json = new File(classLoader.getResource("files/expected/" + fileName).getFile());
        JsonNode jsonNode = objectMapper.readTree(json);
        ObjectReader reader = objectMapper.readerFor(new TypeReference<BusinessDetails>() {
        });
        return objectMapper.writeValueAsString(reader.readValue(jsonNode));
    }
}
