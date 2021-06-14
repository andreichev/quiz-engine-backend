package com.university.itis.services.sparql;

import com.university.itis.dto.map.MapRegionDto;
import com.university.itis.dto.semantic.EntityDto;

import java.util.List;

public interface ClassesRequestsService {
    List<EntityDto> searchForEntities(String query);
    List<EntityDto> selectPlacesInRegion(MapRegionDto region);
}
