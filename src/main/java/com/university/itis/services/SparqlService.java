package com.university.itis.services;

import com.university.itis.dto.TripleDto;

import java.util.LinkedHashMap;
import java.util.List;

public interface SparqlService {
    String selectEntityForQuestion(String type);
    String selectPlaceInRegion(String[] region);
    LinkedHashMap<String, String> selectPlacesInRegion(String[] region);
    List<String> selectEntitiesForQuestion(String type);
    LinkedHashMap<String, String> findEntities(String type, String query);
    List<TripleDto> getSuitableTriples(String entityUri);
    List<String> getAlternativeAnswers(String predicateUri, String correctAnswer);
}
