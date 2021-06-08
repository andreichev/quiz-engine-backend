package com.university.itis.services.sparql;

import com.university.itis.dto.semantic.EntityDto;
import com.university.itis.dto.semantic.TripleDto;

import java.util.LinkedHashMap;
import java.util.List;

public interface SparqlService {
    List<EntityDto> searchForEntities(String query);
    String selectEntityForQuestion(String type);
    LinkedHashMap<String, String> selectPlacesInRegion(String[] region);
    List<String> selectEntitiesForQuestion(String type);
    LinkedHashMap<String, String> findEntities(String type, String query);
    List<TripleDto> getSuitableTriples(String entityUri);
    List<String> getAlternativeAnswers(String predicateUri, String correctAnswer);
}
