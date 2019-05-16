package com.university.itis.services;

import com.university.itis.dto.*;
import com.university.itis.utils.PrefixesStorage;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PredicatesRequestsService {

    @Autowired
    PrefixesStorage prefixesStorage;

    @Autowired
    SparqlHttpClient sparqlHttpClient;

    //returns suitable triples for question
    public List<TripleDto> getSuitableTriples(String entityUri, String entityLabel) {

        final QueryEngineHTTP queryEngineHTTP = new QueryEngineHTTP(sparqlHttpClient.getEndpointUrl(),
                PrefixesStorage.generatePrefixQueryString(prefixesStorage.getReplaceMap()) +
                        "select ?predicate, ?predicateLabel, ?object, coalesce(?objectLabelLang1, ?objectLabelLang2) as ?objectLabel where {\n" +
                        "  <http://dbpedia.org/resource/Alexander_Pushkin> ?predicate ?object .\n" +
                        "\n" +
                        "  OPTIONAL {\n" +
                        "    ?object rdfs:label ?objectLabelLang1 .\n" +
                        "    FILTER(langMatches(lang(?objectLabelLang1), \"ru\")) .\n" +
                        "  }\n" +
                        "  ?object rdfs:label ?objectLabelLang2 .\n" +
                        "  FILTER(langMatches(lang(?objectLabelLang2), \"en\")) ." +
                        "\n" +
                        "  OPTIONAL {\n" +
                        "    ?predicate rdfs:label ?predicateLabel .\n" +
                        "    FILTER(langMatches(lang(?predicateLabel), \"en\") || langMatches(lang(?predicateLabel), \"ru\")) .\n" +
                        "  }\n" +
                        "}\n" +
                        "limit 100"
        );

        System.out.println(queryEngineHTTP.getQueryString());

        List<TripleDto> results = new ArrayList<>();
        try {
            ResultSet resultSet = queryEngineHTTP.execSelect();

            while (resultSet.hasNext()) {
                QuerySolution result = resultSet.next();

                TripleDto currentTriple = new TripleDto();
                currentTriple.setSubjectUri(entityUri);

                String predicateUri;
                String predicateLabel;

                String objectUri;
                String objectLabel;

                results.add(currentTriple);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return results;
    }
}
