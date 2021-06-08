package com.university.itis.services.sparql;

import com.university.itis.dto.semantic.TripleDto;

import java.util.List;

public interface PredicatesRequestsService {
    List<TripleDto> getSuitableTriplesStepOne(String entityUri);
    List<TripleDto> getSuitableTriplesStepTwo(String entityUri);
    String getRangeOfPredicate(String predicateUri);
}
