package com.university.itis.services;

import com.university.itis.dto.*;
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
public class PredicatesRequestsService {

    @Autowired
    PrefixesStorage prefixesStorage;

    @Autowired
    SparqlHttpClient sparqlHttpClient;

    @Autowired
    UriStorage uriStorage;

    //returns suitable triples for question
    public List<TripleDto> getSuitableTriples(String entityUri) {

        final QueryEngineHTTP queryEngineHTTP = new QueryEngineHTTP(sparqlHttpClient.getEndpointUrl(),
                PrefixesStorage.generatePrefixQueryString(prefixesStorage.getReplaceMap()) +
                        "select coalesce(?subjectLabelLang1, ?subjectLabelLang2) as ?subjectLabel, ?predicate, ?predicateLabel, ?object, coalesce(?objectLabelLang1, ?objectLabelLang2) as ?objectLabel where {\n" +
                        "  <" + entityUri + "> ?predicate ?object .\n" +
                        "\n" +
                        "  OPTIONAL {\n" +
                        "    <" + entityUri + "> rdfs:label ?subjectLabelLang1 .\n" +
                        "    FILTER(langMatches(lang(?subjectLabelLang1), \"ru\")) .\n" +
                        "  }\n" +
                        "  <http://dbpedia.org/resource/Alexander_Pushkin> rdfs:label ?subjectLabelLang2 .\n" +
                        "  FILTER(langMatches(lang(?subjectLabelLang2), \"en\")) .\n" +
                        "\n" +
                        "  OPTIONAL {\n" +
                        "    ?object rdfs:label ?objectLabelLang1 .\n" +
                        "    FILTER(langMatches(lang(?objectLabelLang1), \"ru\")) .\n" +
                        "  }\n" +
                        "  ?object rdfs:label ?objectLabelLang2 .\n" +
                        "  FILTER(langMatches(lang(?objectLabelLang2), \"en\")) .\n" +
                        "\n" +
                        "  OPTIONAL {\n" +
                        "    ?predicate rdfs:label ?predicateLabel . \n" +
                        "    FILTER(langMatches(lang(?predicateLabel), \"ru\") || langMatches(lang(?predicateLabel), \"en\")) .\n" +
                        "  }\n" +
                        "}\n" +
                        "limit 1000"
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
                currentTriple.setObjectLabel(result.getLiteral("objectLabel").getLexicalForm());

                results.add(currentTriple);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        queryEngineHTTP.close();
        return results;
    }
}
