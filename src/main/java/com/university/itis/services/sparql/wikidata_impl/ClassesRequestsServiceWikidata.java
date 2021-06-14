package com.university.itis.services.sparql.wikidata_impl;

import com.university.itis.dto.map.MapRegionDto;
import com.university.itis.dto.semantic.EntityDto;
import com.university.itis.services.sparql.ClassesRequestsService;
import com.university.itis.utils.PrefixesStorage;
import com.university.itis.utils.SparqlHttpClient;
import lombok.AllArgsConstructor;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class ClassesRequestsServiceWikidata implements ClassesRequestsService {
    private final PrefixesStorage prefixesStorage;
    private final SparqlHttpClient sparqlHttpClient;

    @Override
    public List<EntityDto> searchForEntities(String query) {
        QueryEngineHTTP queryEngineHTTP = new QueryEngineHTTP(sparqlHttpClient.getEndpointUrl(),
                PrefixesStorage.generatePrefixQueryString(prefixesStorage.getReplaceMap()) +
                        "SELECT DISTINCT ?object ?objectLabel\n" +
                        "WHERE\n" +
                        "{\n" +
                        "    SERVICE wikibase:mwapi {\n" +
                        "       bd:serviceParam wikibase:endpoint \"www.wikidata.org\";\n" +
                        "                      wikibase:api \"EntitySearch\";\n" +
                        "                      mwapi:search \"" + query + "\";\n" +
                        "                      mwapi:language \"ru\".\n" +
                        "       ?object wikibase:apiOutputItem mwapi:item.\n" +
                        "    }\n" +
                        "    SERVICE wikibase:label {bd:serviceParam wikibase:language \"ru\".}\n" +
                        "} LIMIT 100"
        );
        // System.out.println(queryEngineHTTP.getQueryString());

        List<EntityDto> results = new ArrayList<>();
        ResultSet resultSet = queryEngineHTTP.execSelect();

        while (resultSet.hasNext()) {
            QuerySolution result = resultSet.next();

            EntityDto resource = new EntityDto();
            resource.setUri(result.get("object").toString());
            resource.setLabel(result.getLiteral("objectLabel").getLexicalForm());

            results.add(resource);
        }

        queryEngineHTTP.close();
        return results;
    }

    @Override
    public String selectEntity(String type, int offset) {
        return "entity";
    }

    @Override
    public int getCountOfInstancesForClass(String ontologyClass) {
        return 0;
    }

    @Override
    public List<EntityDto> selectPlacesInRegion(MapRegionDto region) {
        return new ArrayList<>();
    }

    @Override
    public List<String> selectEntities(String type, int offset) {
        return new ArrayList<>();
    }

    @Override
    public LinkedHashMap<String, String> findEntities(String type, String query) {
        return new LinkedHashMap<>();
    }
}
