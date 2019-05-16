package com.university.itis.services;

import com.university.itis.dto.TripleDto;
import com.university.itis.utils.PrefixesStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SparqlQueryService {

	@Autowired
    PrefixesStorage prefixesStorage;

	@Autowired
    SparqlHttpClient sparqlHttpClient;

    @Autowired
    ClassesRequestsService findOntologyClassService;

    @Autowired
    PredicatesRequestsService triplesService;

    private Random random = new Random();

    public String selectEntityForQuestion(String type) {
        int countOfClasses = findOntologyClassService.getCountOfInstancesForClass(type);
        if (countOfClasses == 0) {
            return "None";
        }
        return findOntologyClassService.selectEntity(type, random.nextInt(countOfClasses));
    }

    public String selectPlaceInRegion(String[] region) {
        return findOntologyClassService.selectPlaceInRegion(region);
    }

    public LinkedHashMap<String, String> selectPlacesInRegion(String[] region) {
        return findOntologyClassService.selectPlacesInRegion(region);
    }

    public List<String> selectEntitiesForQuestion(String type) {
        int countOfClasses = findOntologyClassService.getCountOfInstancesForClass(type);
        if (countOfClasses == 0) {
            return Collections.singletonList("None");
        }
        return findOntologyClassService.selectEntities(type, random.nextInt(countOfClasses));
    }

    public LinkedHashMap<String, String> findEntities(String type, String query) {
        return findOntologyClassService.findEntities(type, query);
    }

    public List<TripleDto> getSuitableTriples(String entityUri) {
        return triplesService.getSuitableTriples(entityUri);
    }
}
