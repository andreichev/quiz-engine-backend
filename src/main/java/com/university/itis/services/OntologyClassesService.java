package com.university.itis.services;

import com.university.itis.utils.PrefixesStorage;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class OntologyClassesService {

    @Autowired
    PrefixesStorage prefixesStorage;

    @Autowired
    SparqlHttpClient sparqlHttpClient;

    public String selectEntity(String type, int offset) {

        final ParameterizedSparqlString queryString = new ParameterizedSparqlString(
                "SELECT ?e\n" +
                        "WHERE {\n" +
                        //"?e dbo:wikiPageID ?c .\n" +
                        "?e a ?type .\n" +
                        //"?e rdfs:label ?label .\n" +
                        //"FILTER(LANG(?label) = \"\" || LANGMATCHES(LANG(?label), \"ru\"))\n" +
                        "} OFFSET ?offset\n" +
                        "LIMIT 1"
        );

        queryString.setIri("type", prefixesStorage.replaceInverse(type));
        queryString.setLiteral("offset", offset);

        try (QueryExecution queryExecution = sparqlHttpClient.queryExecution(queryString)) {
            ResultSet results = queryExecution.execSelect();

            if (results.hasNext()) {
                QuerySolution result = results.next();
                return result.get("e").toString();
            } else {
                return "None";
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return "None";
    }

    public int getCountOfInstancesForClass(String ontologyClass) {
        final QueryEngineHTTP queryEngineHTTP = new QueryEngineHTTP(sparqlHttpClient.getEndpointUrl(),
                "select count(?obj) as ?count where {\n" +
                        "?obj a <" + prefixesStorage.replaceInverse(ontologyClass) + "> .\n" +
                        "}\n"
        );

        try {
            ResultSet resultsOfQuery = queryEngineHTTP.execSelect();

            if (resultsOfQuery.hasNext()) {
                QuerySolution result = resultsOfQuery.next();
                return (int) result.get("count").asLiteral().getValue();
            } else {
                return 0;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    public String selectPlaceInRegion(String[] region) {
        final QueryEngineHTTP queryEngineHTTP = new QueryEngineHTTP(sparqlHttpClient.getEndpointUrl(),
                PrefixesStorage.generatePrefixQueryString(prefixesStorage.getReplaceMap()) +
                        "select ?place where {\n" +
                        "    ?place a dbo:Place .\n" +
                        "    ?place geopos:lat ?lat .\n" +
                        "    ?place geopos:long ?long .\n" +
                        "    FILTER (\n" +
                        "        ?lat > " + region[0] + " && \n" +
                        "        ?lat < " + region[2] + " && \n" +
                        "        ?long > " + region[1] + " && \n" +
                        "        ?long < " + region[3] + "\n" +
                        "    )\n" +
                        "}\n"
        );

        System.out.println(queryEngineHTTP.getQueryString());

        try {
            ResultSet results = queryEngineHTTP.execSelect();

            if (results.hasNext()) {
                QuerySolution result = results.next();
                return result.get("place").toString();
            } else {
                return "None";
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return "None";
    }

    public List<String> selectPlacesInRegion(String[] region) {
        final QueryEngineHTTP queryEngineHTTP = new QueryEngineHTTP(sparqlHttpClient.getEndpointUrl(),
                PrefixesStorage.generatePrefixQueryString(prefixesStorage.getReplaceMap()) +
                        "select ?place where {\n" +
                        "    ?place a dbo:Place .\n" +
                        "    ?place geopos:lat ?lat .\n" +
                        "    ?place geopos:long ?long .\n" +
                        "    FILTER (\n" +
                        "        ?lat > " + region[0] + " && \n" +
                        "        ?lat < " + region[2] + " && \n" +
                        "        ?long > " + region[1] + " && \n" +
                        "        ?long < " + region[3] + "\n" +
                        "    )\n" +
                        "}\n"
        );

        System.out.println(queryEngineHTTP.getQueryString());

        List<String> results = new ArrayList<>();
        try {
            ResultSet resultSet = queryEngineHTTP.execSelect();

            while (resultSet.hasNext()) {
                QuerySolution result = resultSet.next();
                results.add(result.get("place").toString());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return results;
    }

    public List<String> selectEntities(String type, int offset) {

        final ParameterizedSparqlString queryString = new ParameterizedSparqlString(
                "SELECT ?e\n" +
                        "WHERE {\n" +
                        //"?e dbo:wikiPageID ?c .\n" +
                        "?e a ?type .\n" +
                        //"?e rdfs:label ?label .\n" +
                        //"FILTER(LANG(?label) = \"\" || LANGMATCHES(LANG(?label), \"ru\"))\n" +
                        "} OFFSET ?offset\n" +
                        "LIMIT 100"
        );

        queryString.setIri("type", prefixesStorage.replaceInverse(type));
        queryString.setLiteral("offset", offset);

        List<String> results = new ArrayList<>();
        try (QueryExecution queryExecution = sparqlHttpClient.queryExecution(queryString)) {
            ResultSet resultSet = queryExecution.execSelect();

            while (resultSet.hasNext()) {
                QuerySolution result = resultSet.next();
                results.add(result.get("e").toString());
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return results;
    }
}
