package com.university.itis.services;

import com.university.itis.dto.TripleDto;
import com.university.itis.utils.PrefixesStorage;
import com.university.itis.utils.UriStorage;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PredicatesRequestsWikidataService {

    @Autowired
    PrefixesStorage prefixesStorage;

    @Autowired
    SparqlHttpClient sparqlHttpClient;

    @Autowired
    UriStorage uriStorage;

    //returns suitable triples for question
    public List<TripleDto> getSuitableTriplesStepOne(String entityUri) {

        final QueryEngineHTTP queryEngineHTTP = new QueryEngineHTTP(sparqlHttpClient.getEndpointUrl(),
                PrefixesStorage.generatePrefixQueryString(prefixesStorage.getReplaceMap()) +
                        "select DISTINCT ?subjectLabel ?predicate ?predicateLabel ?object ?objectLabel where {\n" +
                        "  <" + entityUri + "> ?predicate ?object .\n" +
                        "  ?predicate1 wikibase:directClaim ?predicate .\n" +
                        "  \n" +
                        "  OPTIONAL {\n" +
                        "    <" + entityUri + "> rdfs:label ?subjectLabelLang1 .\n" +
                        "    FILTER(langMatches(lang(?subjectLabelLang1), \"ru\")) .\n" +
                        "  }\n" +
                        "  <" + entityUri + "> rdfs:label ?subjectLabelLang2 .\n" +
                        "  FILTER(langMatches(lang(?subjectLabelLang2), \"en\")) .\n" +
                        "\n" +
                        "  OPTIONAL {\n" +
                        "    ?object rdfs:label ?objectLabelLang1 .\n" +
                        "    FILTER(langMatches(lang(?objectLabelLang1), \"ru\")) .\n" +
                        "  }\n" +
                        "\n" +
                        "  ?object rdfs:label ?objectLabelLang2 .\n" +
                        "  FILTER(langMatches(lang(?objectLabelLang2), \"en\")) .\n" +
                        "\n" +
                        "  OPTIONAL {\n" +
                        "    ?predicate1 rdfs:label ?predicateLabelLang1 . \n" +
                        "    FILTER(langMatches(lang(?predicateLabelLang1), \"ru\")) .\n" +
                        "  }\n" +
                        "\n" +
                        "  ?predicate1 rdfs:label ?predicateLabelLang2 .\n" +
                        "  FILTER(langMatches(lang(?predicateLabelLang2), \"en\")) .\n" +
                        "  \n" +
                        "  BIND (COALESCE(?objectLabelLang1, ?objectLabelLang2) AS ?objectLabel)\n" +
                        "  BIND (COALESCE(?subjectLabelLang1, ?subjectLabelLang2) AS ?subjectLabel)\n" +
                        "  BIND (COALESCE(?predicateLabelLang1, ?predicateLabelLang2) AS ?predicateLabel)\n" +
                        " \n" +
                        "}\n" +
                        "limit 3000"
        );

        System.out.println(queryEngineHTTP.getQueryString());

        List<TripleDto> results = new ArrayList<>();
        try {
            ResultSet resultSet = queryEngineHTTP.execSelect();

            while (resultSet.hasNext()) {
                QuerySolution result = resultSet.next();

                TripleDto currentTriple = new TripleDto();
                currentTriple.setSubjectUri(entityUri);
                currentTriple.setSubjectLabel(result.getLiteral("subjectLabel").getLexicalForm());

                currentTriple.setPredicateUri(result.get("predicate").toString());

                if(uriStorage.getBlackList().contains(currentTriple.getPredicateUri())) {
                    continue;
                }

                Literal predicateLabel = result.getLiteral("predicateLabel");
                if(predicateLabel != null) {
                    currentTriple.setPredicateLabel(predicateLabel.getLexicalForm());
                }

                currentTriple.setObjectUri(result.get("object").toString());
                RDFNode objectLabel = result.get("objectLabel");
                if(objectLabel.isLiteral()) {
                    currentTriple.setObjectLabel(objectLabel.asLiteral().getLexicalForm());
                } else {
                    currentTriple.setObjectLabel(objectLabel.toString());
                }

                results.add(currentTriple);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        queryEngineHTTP.close();
        return results;
    }

    public List<TripleDto> getSuitableTriplesStepTwo(String entityUri) {

        final QueryEngineHTTP queryEngineHTTP = new QueryEngineHTTP(sparqlHttpClient.getEndpointUrl(),
                PrefixesStorage.generatePrefixQueryString(prefixesStorage.getReplaceMap()) +
                        "select DISTINCT ?subject ?subjectLabel ?predicate ?predicateLabel ?objectLabel where {\n" +
                        "  ?subject ?predicate <" + entityUri + "> .\n" +
                        "  ?predicate1 wikibase:directClaim ?predicate .\n" +
                        "  \n" +
                        "  OPTIONAL {\n" +
                        "    <" + entityUri + "> rdfs:label ?objectLabelLang1 .\n" +
                        "    FILTER(langMatches(lang(?objectLabelLang1), \"ru\")) .\n" +
                        "  }\n" +
                        "  <" + entityUri + "> rdfs:label ?objectLabelLang2 .\n" +
                        "  FILTER(langMatches(lang(?objectLabelLang2), \"en\")) .\n" +
                        "\n" +
                        "  OPTIONAL {\n" +
                        "    ?subject rdfs:label ?subjectLabelLang1 .\n" +
                        "    FILTER(langMatches(lang(?subjectLabelLang1), \"ru\")) .\n" +
                        "  }\n" +
                        "\n" +
                        "  ?subject rdfs:label ?subjectLabelLang2 .\n" +
                        "  FILTER(langMatches(lang(?subjectLabelLang2), \"en\")) .\n" +
                        "\n" +
                        "  OPTIONAL {\n" +
                        "    ?predicate1 rdfs:label ?predicateLabelLang1 . \n" +
                        "    FILTER(langMatches(lang(?predicateLabelLang1), \"ru\")) .\n" +
                        "  }\n" +
                        "\n" +
                        "  ?predicate1 rdfs:label ?predicateLabelLang2 .\n" +
                        "  FILTER(langMatches(lang(?predicateLabelLang2), \"en\")) .\n" +
                        "  \n" +
                        "  BIND (COALESCE(?objectLabelLang1, ?objectLabelLang2) AS ?objectLabel)\n" +
                        "  BIND (COALESCE(?subjectLabelLang1, ?subjectLabelLang2) AS ?subjectLabel)\n" +
                        "  BIND (COALESCE(?predicateLabelLang1, ?predicateLabelLang2) AS ?predicateLabel)\n" +
                        "  \n" +
                        "}\n" +
                        "limit 3000"
        );

        System.out.println(queryEngineHTTP.getQueryString());

        List<TripleDto> results = new ArrayList<>();
        int counter = 0;
        try {
            ResultSet resultSet = queryEngineHTTP.execSelect();

            while (resultSet.hasNext()) {
                QuerySolution result = resultSet.next();

                TripleDto currentTriple = new TripleDto();
                currentTriple.setCounter(counter++);
                currentTriple.setObjectUri(result.get("subject").toString());
                currentTriple.setSubjectLabel(result.getLiteral("subjectLabel").getLexicalForm());

                currentTriple.setPredicateUri(result.get("predicate").toString());

                if(uriStorage.getBlackList().contains(currentTriple.getPredicateUri())) {
                    continue;
                }

                Literal predicateLabel = result.getLiteral("predicateLabel");
                if(predicateLabel != null) {
                    currentTriple.setPredicateLabel(predicateLabel.getLexicalForm());
                }

                currentTriple.setObjectUri(entityUri);
                currentTriple.setObjectLabel(result.getLiteral("objectLabel").getLexicalForm());

                results.add(currentTriple);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        queryEngineHTTP.close();
        return results;
    }

    //returns suitable triples for question
    public String getRangeOfPredicate(String predicateUri) {

        final QueryEngineHTTP queryEngineHTTP = new QueryEngineHTTP(sparqlHttpClient.getEndpointUrl(),
                PrefixesStorage.generatePrefixQueryString(prefixesStorage.getReplaceMap()) +
                        "select ?range where {\n" +
                        "  <" + predicateUri + "> rdfs:range ?range .\n" +
                        "}"
        );

        System.out.println(queryEngineHTTP.getQueryString());

        try {
            ResultSet resultSet = queryEngineHTTP.execSelect();

            if (resultSet.hasNext()) {
                QuerySolution result = resultSet.next();

                return result.get("range").toString();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        queryEngineHTTP.close();
        return null;
    }
}

