package com.university.itis.services;

import com.university.itis.dto.TripleDto;
import com.university.itis.services.answers.AlternativeAnswersHandler;
import com.university.itis.services.answers.AnswerClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SparqlService {

    @Autowired
    ClassesRequestsService findOntologyClassService;

    @Autowired
    PredicatesRequestsService triplesService;

    @Autowired
    AlternativeAnswersHandler alternativeAnswersHandler;

    private Random random = new Random();

    public String selectEntityForQuestion(String type) {
        int countOfClasses = findOntologyClassService.getCountOfInstancesForClass(type);
        if (countOfClasses == 0) {
            return "None";
        }
        return findOntologyClassService.selectEntity(type, random.nextInt(countOfClasses));
    }

    public String selectPlaceInRegion(String[] region) {
        return findOntologyClassService.selectPlaceInRegion(region);
    }

    public LinkedHashMap<String, String> selectPlacesInRegion(String[] region) {
        return findOntologyClassService.selectPlacesInRegion(region);
    }

    public List<String> selectEntitiesForQuestion(String type) {
        int countOfClasses = findOntologyClassService.getCountOfInstancesForClass(type);
        if (countOfClasses == 0) {
            return Collections.singletonList("None");
        }
        return findOntologyClassService.selectEntities(type, random.nextInt(countOfClasses));
    }

    public LinkedHashMap<String, String> findEntities(String type, String query) {
        return findOntologyClassService.findEntities(type, query);
    }

    public List<TripleDto> getSuitableTriples(String entityUri) {
        List<TripleDto> results = new ArrayList<>();
        results.addAll(triplesService.getSuitableTriplesStepOne(entityUri));
        results.addAll(triplesService.getSuitableTriplesStepTwo(entityUri));
        return results;
    }

    public List<String> getAlternativeAnswers(String predicateUri, String correctAnswer) {

        String typeUri = triplesService.getRangeOfPredicate(predicateUri);
        if(typeUri == null) {
            return null;
        }

        AnswerClass answerClass = alternativeAnswersHandler.extractAnswerClass(typeUri, correctAnswer);
        if(answerClass.equals(AnswerClass.OTHER)) {

            int countOfClasses = findOntologyClassService.getCountOfInstancesForClass(typeUri);
            List<String> result = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                String label = findOntologyClassService.selectEntity(typeUri, random.nextInt(countOfClasses));
                result.add(label);
            }

            return result;

        } else {
            return alternativeAnswersHandler.getAlternativeAnswersForClass(answerClass);
        }
    }
}
