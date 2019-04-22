package com.university.itis.services;

import com.university.itis.utils.PrefixesStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class SparqlQueryService {

	@Autowired
    PrefixesStorage prefixesStorage;

	@Autowired
    SparqlHttpClient sparqlHttpClient;

    @Autowired
    OntologyClassesService ontologyClassesService;

    private Random random = new Random();

    public String selectEntityForQuestion(String type) {
        int countOfClasses = ontologyClassesService.getCountOfInstancesForClass(type);
        if (countOfClasses == 0) {
            return "None";
        }
        return ontologyClassesService.selectEntity(type, random.nextInt(countOfClasses));
    }

    public String selectPlaceInRegion(String[] region) {
        return ontologyClassesService.selectPlaceInRegion(region);
    }

    public List<String> selectPlacesInRegion(String[] region) {
        return ontologyClassesService.selectPlacesInRegion(region);
    }

    public List<String> selectEntitiesForQuestion(String type) {
        int countOfClasses = ontologyClassesService.getCountOfInstancesForClass(type);
        if (countOfClasses == 0) {
            return Collections.singletonList("None");
        }
        return ontologyClassesService.selectEntities(type, random.nextInt(countOfClasses));
    }
}
