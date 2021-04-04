package com.university.itis.services;

import com.university.itis.utils.PrefixesStorage;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClassesRequestsService {

    @Autowired
    PrefixesStorage prefixesStorage;

    @Autowired
    SparqlHttpClient sparqlHttpClient;

    private String LANGUAGE1 = "ru";
    private String LANGUAGE2 = "en";

    public String selectEntity(String type, int offset) {

        final ParameterizedSparqlString queryString = new ParameterizedSparqlString(
                PrefixesStorage.generatePrefixQueryString(prefixesStorage.getReplaceMap()) +
                        "select coalesce(?labelLang1, ?labelLang2) as ?label where {\n" +
                        "  ?subject a ?type .\n" +
                        "\n" +
                        "  OPTIONAL {\n" +
                        "    ?subject rdfs:label ?labelLang1 .\n" +
                        "    FILTER(langMatches(lang(?labelLang1), \"" + LANGUAGE1 + "\")) ." +
                        "  }\n" +
                        "  ?subject rdfs:label ?labelLang2 .\n" +
                        "  FILTER(langMatches(lang(?labelLang2), \"" + LANGUAGE2 + "\")) ." +
                        "\n" +
                        "} OFFSET ?offset\n" +
                        "LIMIT 1"
        );

        queryString.setIri("type", prefixesStorage.replaceInverse(type));
        queryString.setLiteral("offset", offset);

        try (QueryExecution queryExecution = sparqlHttpClient.queryExecution(queryString)) {
            ResultSet results = queryExecution.execSelect();

            if (results.hasNext()) {
                QuerySolution result = results.next();

                RDFNode label = result.get("label");
                if(label.isLiteral()) {
                    return label.asLiteral().getLexicalForm();
                } else {
                    return label.toString();
                }

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
                PrefixesStorage.generatePrefixQueryString(prefixesStorage.getReplaceMap()) +
                        "select count(?obj) as ?count " +
                        "where {\n" +
                        "  ?obj a <" + prefixesStorage.replaceInverse(ontologyClass) + "> .\n" +
                        "  ?obj rdfs:label ?label ." +
                        "  FILTER(langMatches(lang(?label), \"" + LANGUAGE2 + "\")) ." +
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

    public LinkedHashMap<String, String> selectPlacesInRegion(String[] region) {
        final QueryEngineHTTP queryEngineHTTP = new QueryEngineHTTP(sparqlHttpClient.getEndpointUrl(),
                PrefixesStorage.generatePrefixQueryString(prefixesStorage.getReplaceMap()) +
                        "select ?place coalesce(?labelLang1, ?labelLang2) as ?label where {\n" +
                        "  ?place a dbo:Place .\n" +
                        "  ?place geopos:lat ?lat .\n" +
                        "  ?place geopos:long ?long .\n" +
                        "\n" +
                        "  OPTIONAL {\n" +
                        "    ?place rdfs:label ?labelLang1 .\n" +
                        "    FILTER(langMatches(lang(?labelLang1), \"ru\")) .\n" +
                        "  }\n" +
                        "\n" +
                        "  ?place rdfs:label ?labelLang2 .\n" +
                        "\n" +
                        "  FILTER (\n" +
                        "    ?lat > " + region[0] + " && \n" +
                        "    ?lat < " + region[2] + " && \n" +
                        "    ?long > " + region[1] + " && \n" +
                        "    ?long < " + region[3] + " && \n" +
                        "    lang(?labelLang2) = \"en\"\n" +
                        "  )\n" +
                        "} LIMIT 1000"
        );

        System.out.println(queryEngineHTTP.getQueryString());

        LinkedHashMap<String, String> results = new LinkedHashMap<>();
        try {
            ResultSet resultSet = queryEngineHTTP.execSelect();

            while (resultSet.hasNext()) {
                QuerySolution result = resultSet.next();

                String place = result.get("place").toString();
                String label = result.getLiteral("label").getLexicalForm();
                results.put(place, label);
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
                        //"FILTER(LANG(?label) = \"\" || LANGMATCHES(LANG(?label), \"" + LANGUAGE + "\"))\n" +
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

    public LinkedHashMap<String, String> findEntities(String type, String query) {

        final QueryEngineHTTP queryEngineHTTP = new QueryEngineHTTP(sparqlHttpClient.getEndpointUrl(),
                PrefixesStorage.generatePrefixQueryString(prefixesStorage.getReplaceMap()) +
                        "select distinct ?entity ?label where {\n" +
                        "  ?entity a " + type + " .\n" +
                        "  ?entity rdfs:label ?label .\n" +
                        "  FILTER contains(?label, \"" + query + "\") .\n" +
                        "  FILTER(langMatches(lang(?label), \"" + LANGUAGE1 + "\") || langMatches(lang(?label), \"" + LANGUAGE2 + "\"))\n" +
                        "}\n" +
                        "limit 100"
        );

        System.out.println(queryEngineHTTP.getQueryString());

        LinkedHashMap<String, String> results = new LinkedHashMap<>();
        try {
            ResultSet resultSet = queryEngineHTTP.execSelect();

            while (resultSet.hasNext()) {
                QuerySolution result = resultSet.next();

                String entity = result.get("entity").toString();
                String label = result.getLiteral("label").getLexicalForm();
                results.put(entity, label);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return results;
    }
}
