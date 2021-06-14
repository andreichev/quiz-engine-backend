package com.university.itis.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.university.itis.utils.PrefixesStorage;
import com.university.itis.utils.SparqlHttpClient;
import com.university.itis.utils.UriStorage;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
@AllArgsConstructor
public class SparqlQueryConfig {

    private final ObjectMapper mapper;

    @Bean(name = "SparqlHttpClientWikidata")
    SparqlHttpClient configureSparqlHttpClientWikidata() {
        String endpointUrl;
        try {
            InputStream resource = new ClassPathResource(
                    "configprops_wikidata.json").getInputStream();

            JsonNode tree = mapper.readTree(resource);
            endpointUrl = tree.path("endpointUrl").asText();
        } catch (IOException e){
            System.out.println("Unable to read config: " + e.getMessage());
            return null;
        }

        return new SparqlHttpClient(endpointUrl);
    }

    @Bean(name = "PrefixesStorageWikidata")
    PrefixesStorage configurePrefixesWikidata() {
        TypeReference<LinkedHashMap<String, String>> typeReference = new TypeReference<LinkedHashMap<String, String>>(){};
        Map<String, String> prefixes;
        try {
            InputStream resource = new ClassPathResource(
                    "configprops_wikidata.json").getInputStream();

            JsonNode tree = mapper.readTree(resource);
            prefixes = mapper.readValue(tree.path("prefixes").toString(), typeReference);
        } catch (IOException e){
            System.out.println("Unable to read config: " + e.getMessage());
            return null;
        }

        return new PrefixesStorage(prefixes);
    }

    @Bean(name = "SparqlHttpClientDBPedia")
    SparqlHttpClient configureSparqlHttpClientDBPedia() {
        String endpointUrl;
        try {
            InputStream resource = new ClassPathResource(
                    "configprops_dbpedia.json").getInputStream();

            JsonNode tree = mapper.readTree(resource);
            endpointUrl = tree.path("endpointUrl").asText();
        } catch (IOException e){
            System.out.println("Unable to read config: " + e.getMessage());
            return null;
        }

        return new SparqlHttpClient(endpointUrl);
    }

    @Bean(name = "PrefixesStorageDBPedia")
    PrefixesStorage configurePrefixesDBPedia() {
        TypeReference<LinkedHashMap<String, String>> typeReference = new TypeReference<LinkedHashMap<String, String>>(){};
        Map<String, String> prefixes;
        try {
            InputStream resource = new ClassPathResource(
                    "configprops_dbpedia.json").getInputStream();

            JsonNode tree = mapper.readTree(resource);
            prefixes = mapper.readValue(tree.path("prefixes").toString(), typeReference);
        } catch (IOException e){
            System.out.println("Unable to read config: " + e.getMessage());
            return null;
        }

        return new PrefixesStorage(prefixes);
    }

    @Bean(name = "configureUriStorage")
    UriStorage configureUriStorage() {
        TypeReference<LinkedHashMap<String, String>> typeReferenceClasses = new TypeReference<LinkedHashMap<String, String>>(){};
        Map<String, String> classes;
        try {
            InputStream resource = new ClassPathResource("classes.json").getInputStream();

            classes = mapper.readValue(resource, typeReferenceClasses);
        } catch (IOException e){
            System.out.println("Unable to read config: " + e.getMessage());
            return null;
        }

        TypeReference<List<String>> typeReferenceBlackList = new TypeReference<List<String>>(){};
        List<String> blacklist;
        try {
            InputStream resource = new ClassPathResource("blacklist.json").getInputStream();

            blacklist = mapper.readValue(resource, typeReferenceBlackList);
        } catch (IOException e){
            System.out.println("Unable to read config: " + e.getMessage());
            return null;
        }

        return new UriStorage(classes, blacklist);
    }
}
