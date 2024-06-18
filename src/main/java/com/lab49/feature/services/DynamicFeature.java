package com.lab49.feature.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Component
public class DynamicFeature {
    private final Map<String, String> featureProperties = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${filewatcher.file-to-watch}")
    private String fileToWatch;

    @PostConstruct
    public void readFile() {
        readFileContentAsJson(Path.of(fileToWatch));
//        readFileContentAsJson(Path.of("C:\\Users\\sujeet.kumar\\app\\feature.json"));
    }
    public Map<String, String> getFeatureState() {

        return featureProperties;
    }

    private void readFileContentAsJson(Path path) {
        try {
            // Read the file content as a String
            String content = Files.readString(path);

            // Parse the content as JSON
            Map properties = objectMapper.readValue(content, Map.class);

            // Process the JSON content
            System.out.println("JSON content of " + path + ":");
            System.out.println(properties);

            properties.forEach( (k, v) -> {
                featureProperties.put(k.toString(), v.toString());
            });
        } catch (IOException e) {
            System.err.println("Failed to read or parse content of " + path);
            e.printStackTrace();
        }
    }
}
