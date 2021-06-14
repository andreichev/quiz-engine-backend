package com.university.itis.services.sparql.wikidata_impl;

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
public class ClassesRequestsServiceWikidata implements ClassesRequestsService {
    private final PrefixesStorage prefixesStorage;
    private final SparqlHttpClient sparqlHttpClient;

    public ClassesRequestsServiceWikidata(
            @Qualifier("PrefixesStorageWikidata") PrefixesStorage prefixesStorage,
            @Qualifier("SparqlHttpClientWikidata") SparqlHttpClient sparqlHttpClient
    ) {
        this.prefixesStorage = prefixesStorage;
        this.sparqlHttpClient = sparqlHttpClient;
    }

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
    public List<EntityDto> selectPlacesInRegion(MapRegionDto region) {
        final QueryEngineHTTP queryEngineHTTP = new QueryEngineHTTP(sparqlHttpClient.getEndpointUrl(),
                PrefixesStorage.generatePrefixQueryString(prefixesStorage.getReplaceMap()) +
                        "select ?place ?placeLabel where {\n" +
                        "  ?place p:P625 ?statement .\n" +
                        "  ?statement psv:P625 ?coordinate_node .\n" +
                        "  ?coordinate_node wikibase:geoLatitude ?lat .\n" +
                        "  ?coordinate_node wikibase:geoLongitude ?long .\n" +
                        "\n" +
                        "  FILTER (\n" +
                        "    ?lat > " + region.getBottomRight().getLatitude() + " && \n" +
                        "    ?lat < " + region.getTopLeft().getLatitude() + " && \n" +
                        "    ?long > " + region.getTopLeft().getLongitude() + " && \n" +
                        "    ?long < " + region.getBottomRight().getLongitude() + "\n" +
                        "  )\n" +
                        "  \n" +
                        "  SERVICE wikibase:label {bd:serviceParam wikibase:language \"ru\".}\n" +
                        "} LIMIT 1000"
        );

        System.out.println(queryEngineHTTP.getQueryString());

        List<EntityDto> results = new ArrayList<>();
        try {
            ResultSet resultSet = queryEngineHTTP.execSelect();

            while (resultSet.hasNext()) {
                QuerySolution result = resultSet.next();

                String place = result.get("place").toString();
                String label = result.getLiteral("placeLabel").getLexicalForm();
                results.add(new EntityDto(place, label));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return results;
    }
}
