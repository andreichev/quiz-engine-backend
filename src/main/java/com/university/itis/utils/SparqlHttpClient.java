package com.university.itis.utils;

import lombok.Getter;
import lombok.Setter;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;

@Getter
@Setter
public class SparqlHttpClient {
    private String endpointUrl;
    private HttpClient httpClient;

    public SparqlHttpClient(String endpointUrl) {
        this.endpointUrl = endpointUrl;
        this.httpClient = HttpClients.createDefault();
    }

    public QueryExecution queryExecution(ParameterizedSparqlString query) {
         return QueryExecutionFactory.sparqlService(this.getEndpointUrl(), query.asQuery(), this.getHttpClient());
    }
}
