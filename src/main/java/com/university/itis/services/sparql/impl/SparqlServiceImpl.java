package com.university.itis.services.sparql.impl;

import com.university.itis.dto.map.MapRegionDto;
import com.university.itis.dto.semantic.EntityDto;
import com.university.itis.dto.semantic.GraphType;
import com.university.itis.dto.semantic.TripleDto;
import com.university.itis.exceptions.NotFoundException;
import com.university.itis.services.sparql.ClassesRequestsService;
import com.university.itis.services.sparql.PredicatesRequestsService;
import com.university.itis.services.sparql.SparqlService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SparqlServiceImpl implements SparqlService {
    private final ClassesRequestsService findOntologyClassServiceDBPedia;
    private final PredicatesRequestsService triplesServiceDBPedia;
    private final ClassesRequestsService findOntologyClassServiceWikidata;
    private final PredicatesRequestsService triplesServiceWikidata;

    public SparqlServiceImpl(
            @Qualifier("classesRequestsServiceDbpedia") ClassesRequestsService findOntologyClassServiceDBPedia,
            @Qualifier("predicatesRequestsServiceDbpedia") PredicatesRequestsService triplesServiceDBPedia,
            @Qualifier("classesRequestsServiceWikidata") ClassesRequestsService findOntologyClassServiceWikidata,
            @Qualifier("predicatesRequestsServiceWikidata") PredicatesRequestsService triplesServiceWikidata
    ) {
        this.findOntologyClassServiceDBPedia = findOntologyClassServiceDBPedia;
        this.triplesServiceDBPedia = triplesServiceDBPedia;
        this.findOntologyClassServiceWikidata = findOntologyClassServiceWikidata;
        this.triplesServiceWikidata = triplesServiceWikidata;
    }

    @Override
    public List<EntityDto> searchForEntities(String query, GraphType graphType) {
        switch (graphType) {
            case DBPEDIA:
                return findOntologyClassServiceDBPedia.searchForEntities(query);
            case WIKIDATA:
                return findOntologyClassServiceWikidata.searchForEntities(query);
        }
        throw new NotFoundException("Graph type not determined");
    }

    @Override
    public List<EntityDto> selectPlacesInRegion(MapRegionDto region, GraphType graphType) {
        switch (graphType) {
            case DBPEDIA:
                return findOntologyClassServiceDBPedia.selectPlacesInRegion(region);
            case WIKIDATA:
                return findOntologyClassServiceWikidata.selectPlacesInRegion(region);
        }
        throw new NotFoundException("Graph type not determined");
    }

    @Override
    public List<TripleDto> getSuitableTriples(String entityUri, GraphType graphType) {
        List<TripleDto> results = new ArrayList<>();
        switch (graphType) {
            case DBPEDIA:
                results.addAll(triplesServiceDBPedia.getSuitableTriplesStepOne(entityUri));
                results.addAll(triplesServiceDBPedia.getSuitableTriplesStepTwo(entityUri));
            case WIKIDATA:
                results.addAll(triplesServiceWikidata.getSuitableTriplesStepOne(entityUri));
                results.addAll(triplesServiceWikidata.getSuitableTriplesStepTwo(entityUri));
        }
        return results;
    }
}
