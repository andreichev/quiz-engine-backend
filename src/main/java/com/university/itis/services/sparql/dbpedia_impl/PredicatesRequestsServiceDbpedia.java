package com.university.itis.services.sparql.dbpedia_impl;

import com.university.itis.dto.TripleDto;
import com.university.itis.services.sparql.PredicatesRequestsService;
import com.university.itis.utils.PrefixesStorage;
import com.university.itis.utils.SparqlHttpClient;
import com.university.itis.utils.UriStorage;
import lombok.AllArgsConstructor;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class PredicatesRequestsServiceDbpedia implements PredicatesRequestsService {

    private final PrefixesStorage prefixesStorage;
    private final SparqlHttpClient sparqlHttpClient;
    private final UriStorage uriStorage;

    //returns suitable triples for question
    @Override
    public List<TripleDto> getSuitableTriplesStepOne(String entityUri) {

        final QueryEngineHTTP queryEngineHTTP = new QueryEngineHTTP(sparqlHttpClient.getEndpointUrl(),
                PrefixesStorage.generatePrefixQueryString(prefixesStorage.getReplaceMap()) +
                        "select DISTINCT coalesce(?subjectLabelLang1, ?subjectLabelLang2) as ?subjectLabel, " +
                        "?predicate, " +
                        "coalesce(?predicateLabelLang1, ?predicateLabelLang2) as ?predicateLabel, " +
                        "?object, " +
                        "coalesce(coalesce(?objectLabelLang1, ?objectLabelLang2), ?object) as ?objectLabel where {\n" +
                        "  <" + entityUri + "> ?predicate ?object .\n" +
                        "\n" +
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
                        "  OPTIONAL {\n" +
                        "    ?object rdfs:label ?objectLabelLang2 .\n" +
                        "    FILTER(langMatches(lang(?objectLabelLang2), \"en\")) .\n" +
                        "  }\n" +
                        "\n" +
                        "  OPTIONAL {\n" +
                        "    ?predicate rdfs:label ?predicateLabelLang1 . \n" +
                        "    FILTER(langMatches(lang(?predicateLabelLang1), \"ru\")) .\n" +
                        "  }\n" +
                        "\n" +
                        "  ?predicate rdfs:label ?predicateLabelLang2 .\n" +
                        "  FILTER(langMatches(lang(?predicateLabelLang2), \"en\")) ." +
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

                if (uriStorage.getBlackList().contains(currentTriple.getPredicateUri())) {
                    continue;
                }

                Literal predicateLabel = result.getLiteral("predicateLabel");
                if (predicateLabel != null) {
                    currentTriple.setPredicateLabel(predicateLabel.getLexicalForm());
                }

                currentTriple.setObjectUri(result.get("object").toString());
                RDFNode objectLabel = result.get("objectLabel");
                if (objectLabel.isLiteral()) {
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

    @Override
    public List<TripleDto> getSuitableTriplesStepTwo(String entityUri) {

        final QueryEngineHTTP queryEngineHTTP = new QueryEngineHTTP(sparqlHttpClient.getEndpointUrl(),
                PrefixesStorage.generatePrefixQueryString(prefixesStorage.getReplaceMap()) +
                        "select DISTINCT ?subject," +
                        "coalesce(?subjectLabelLang1, ?subjectLabelLang2) as ?subjectLabel, " +
                        "?predicate, " +
                        "coalesce(?predicateLabelLang1, ?predicateLabelLang2) as ?predicateLabel, " +
                        "coalesce(?objectLabelLang1, ?objectLabelLang2) as ?objectLabel " +
                        " where {\n" +
                        "  ?subject ?predicate <" + entityUri + "> .\n" +
                        "\n" +
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
                        "    ?predicate rdfs:label ?predicateLabelLang1 . \n" +
                        "    FILTER(langMatches(lang(?predicateLabelLang1), \"ru\")) .\n" +
                        "  }\n" +
                        "\n" +
                        "  ?predicate rdfs:label ?predicateLabelLang2 .\n" +
                        "  FILTER(langMatches(lang(?predicateLabelLang2), \"en\")) ." +
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

                if (uriStorage.getBlackList().contains(currentTriple.getPredicateUri())) {
                    continue;
                }

                Literal predicateLabel = result.getLiteral("predicateLabel");
                if (predicateLabel != null) {
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
    @Override
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
