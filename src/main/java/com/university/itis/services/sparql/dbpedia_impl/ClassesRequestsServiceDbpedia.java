package com.university.itis.services.sparql.dbpedia_impl;

import com.university.itis.dto.map.MapRegionDto;
import com.university.itis.dto.semantic.EntityDto;
import com.university.itis.services.sparql.ClassesRequestsService;
import com.university.itis.utils.PrefixesStorage;
import com.university.itis.utils.SparqlHttpClient;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassesRequestsServiceDbpedia implements ClassesRequestsService {
    private final PrefixesStorage prefixesStorage;
    private final SparqlHttpClient sparqlHttpClient;

    public ClassesRequestsServiceDbpedia(
            @Qualifier("PrefixesStorageDBPedia") PrefixesStorage prefixesStorage,
            @Qualifier("SparqlHttpClientDBPedia") SparqlHttpClient sparqlHttpClient
    ) {
        this.prefixesStorage = prefixesStorage;
        this.sparqlHttpClient = sparqlHttpClient;
    }

    private final String LANGUAGE1 = "ru";
    private final String LANGUAGE2 = "en";

    @Override
    public List<EntityDto> searchForEntities(String query) {
        return new ArrayList<>();
    }

    public List<EntityDto> selectPlacesInRegion(MapRegionDto region) {
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
                        "    ?lat > " + region.getBottomRight().getLatitude() + " && \n" +
                        "    ?lat < " + region.getTopLeft().getLatitude() + " && \n" +
                        "    ?long > " + region.getTopLeft().getLongitude() + " && \n" +
                        "    ?long < " + region.getBottomRight().getLongitude() + " && \n" +
                        "    lang(?labelLang2) = \"en\"\n" +
                        "  )\n" +
                        "} LIMIT 1000"
        );

        System.out.println(queryEngineHTTP.getQueryString());

        List<EntityDto> results = new ArrayList<>();
        try {
            ResultSet resultSet = queryEngineHTTP.execSelect();

            while (resultSet.hasNext()) {
                QuerySolution result = resultSet.next();

                String place = result.get("place").toString();
                String label = result.getLiteral("label").getLexicalForm();
                results.add(new EntityDto(place, label));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return results;
    }
}
