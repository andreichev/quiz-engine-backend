package com.university.itis.services.sparql;

import com.university.itis.dto.map.MapRegionDto;
import com.university.itis.dto.semantic.EntityDto;
import com.university.itis.dto.semantic.GraphType;
import com.university.itis.dto.semantic.TripleDto;

import java.util.List;

public interface SparqlService {
    List<EntityDto> searchForEntities(String query, GraphType graphType);
    List<EntityDto> selectPlacesInRegion(MapRegionDto region, GraphType graphType);
    List<TripleDto> getSuitableTriples(String entityUri, GraphType graphType);
}
