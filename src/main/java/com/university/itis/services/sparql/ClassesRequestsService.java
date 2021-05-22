package com.university.itis.services.sparql;

import java.util.LinkedHashMap;
import java.util.List;

public interface ClassesRequestsService {
    String selectEntity(String type, int offset);
    int getCountOfInstancesForClass(String ontologyClass);
    String selectPlaceInRegion(String[] region);
    LinkedHashMap<String, String> selectPlacesInRegion(String[] region);
    List<String> selectEntities(String type, int offset);
    LinkedHashMap<String, String> findEntities(String type, String query);
}
