package com.university.itis.controller;

import com.university.itis.dto.semantic.EntityDto;
import com.university.itis.dto.semantic.TripleDto;
import com.university.itis.services.sparql.SparqlService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/semantic")
@AllArgsConstructor
public class SemanticOperationsController {
    private final SparqlService sparqlService;

    @GetMapping("/search")
    List<EntityDto> get(@RequestParam String query) {
        return sparqlService.searchForEntities(query);
    }

    @GetMapping("/questions")
    List<TripleDto> getQuestions(@RequestParam String entityUri) {
        return sparqlService.getSuitableTriples(entityUri);
    }
}
