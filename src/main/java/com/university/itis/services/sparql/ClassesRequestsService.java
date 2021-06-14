package com.university.itis.services.sparql;

import com.university.itis.dto.map.MapRegionDto;
import com.university.itis.dto.semantic.EntityDto;

import java.util.LinkedHashMap;
import java.util.List;

public interface ClassesRequestsService {
    List<EntityDto> searchForEntities(String query);
    String selectEntity(String type, int offset);
    int getCountOfInstancesForClass(String ontologyClass);
    List<EntityDto> selectPlacesInRegion(MapRegionDto region);
    List<String> selectEntities(String type, int offset);
    LinkedHashMap<String, String> findEntities(String type, String query);
}
