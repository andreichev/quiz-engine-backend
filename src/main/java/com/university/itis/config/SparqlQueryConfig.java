package com.university.itis.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.university.itis.services.SparqlHttpClient;
import com.university.itis.utils.ClassesStorage;
import com.university.itis.utils.PrefixesStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class SparqlQueryConfig {

    private ObjectMapper mapper = new ObjectMapper();

    public SparqlQueryConfig() {
        mapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
    }

    @Bean
    SparqlHttpClient configureSparqlHttpClient() {
        String endpointUrl;
        try {
            InputStream resource = new ClassPathResource(
                    "configprops.json").getInputStream();

            JsonNode tree = mapper.readTree(resource);
            endpointUrl = tree.path("endpointUrl").asText();
        } catch (IOException e){
            System.out.println("Unable to read config: " + e.getMessage());
            return null;
        }

        return new SparqlHttpClient(endpointUrl);
    }

    @Bean
    PrefixesStorage configurePrefixes() {
        TypeReference<LinkedHashMap<String, String>> typeReference = new TypeReference<LinkedHashMap<String, String>>(){};
        Map<String, String> prefixes;
        try {
            InputStream resource = new ClassPathResource(
                    "configprops.json").getInputStream();

            JsonNode tree = mapper.readTree(resource);
            prefixes = mapper.readValue(tree.path("prefixes").toString(), typeReference);
        } catch (IOException e){
            System.out.println("Unable to read config: " + e.getMessage());
            return null;
        }

        return new PrefixesStorage(prefixes);
    }

    @Bean
    ClassesStorage configureClasses() {
        TypeReference<LinkedHashMap<String, Integer>> typeReference = new TypeReference<LinkedHashMap<String, Integer>>(){};
        Map<String, Integer> classes;
        try {
            InputStream resource = new ClassPathResource(
                    "classes-instances.json").getInputStream();

            classes = mapper.readValue(resource, typeReference);
        } catch (IOException e){
            System.out.println("Unable to read config: " + e.getMessage());
            return null;
        }

        return new ClassesStorage(classes);
    }
}
