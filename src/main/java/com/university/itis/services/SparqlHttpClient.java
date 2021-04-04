package com.university.itis.services;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;

import java.util.Collections;
import java.util.List;

public class SparqlHttpClient {

    private String endpointUrl;
    private HttpClient httpClient;

    public SparqlHttpClient(String endpointUrl) {
        this.endpointUrl = endpointUrl;
        this.httpClient = getInsecureHttpClient();
    }

    private static HttpClient getInsecureHttpClient() {
        Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        List<Header> headers = Collections.singletonList(header);
        return HttpClients.custom().setDefaultHeaders(headers).build();
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
