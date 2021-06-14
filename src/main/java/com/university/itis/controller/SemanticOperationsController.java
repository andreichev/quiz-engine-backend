package com.university.itis.controller;

import com.university.itis.dto.map.MapRegionDto;
import com.university.itis.dto.semantic.EntityDto;
import com.university.itis.dto.semantic.TripleDto;
import com.university.itis.services.sparql.SparqlService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/semantic")
@AllArgsConstructor
public class SemanticOperationsController {
    private final SparqlService sparqlService;

    @GetMapping("/search")
    List<EntityDto> getByQuery(@RequestParam String query) {
        return sparqlService.searchForEntities(query);
    }

    @GetMapping("/map")
    List<EntityDto> getByMap(@RequestBody MapRegionDto region) {
        return sparqlService.selectPlacesInRegion(region);
    }

    @GetMapping("/questions")
    List<TripleDto> getQuestions(@RequestParam String entityUri) {
        return sparqlService.getSuitableTriples(entityUri);
    }
}
