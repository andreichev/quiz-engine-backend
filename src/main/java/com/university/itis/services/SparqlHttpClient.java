package com.university.itis.services;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;

public class SparqlHttpClient {

    private String endpointUrl;
    private HttpClient httpClient;

    public SparqlHttpClient(String endpointUrl) {
        this.endpointUrl = endpointUrl;
        this.httpClient = getInsecureHttpClient();
    }

    private static HttpClient getInsecureHttpClient() {
        return HttpClients.createDefault();
    }

    public QueryExecution queryExecution(ParameterizedSparqlString query) {
         return QueryExecutionFactory.sparqlService(this.getEndpointUrl(), query.asQuery(), this.getHttpClient());
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
