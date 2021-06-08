package com.university.itis.services.sparql;

import com.university.itis.dto.semantic.EntityDto;

import java.util.LinkedHashMap;
import java.util.List;

public interface ClassesRequestsService {
    List<EntityDto> searchForEntities(String query);
    String selectEntity(String type, int offset);
    int getCountOfInstancesForClass(String ontologyClass);
    LinkedHashMap<String, String> selectPlacesInRegion(String[] region);
    List<String> selectEntities(String type, int offset);
    LinkedHashMap<String, String> findEntities(String type, String query);
}
