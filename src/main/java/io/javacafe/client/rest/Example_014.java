package io.javacafe.client.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;


/**
 * Search Template API
 * */
public class Example_014 {
    public static void main(String[] args) throws IOException {
        
    	RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        String INDEX_NAME = "movie_auto_java";
        String FIELD_NAME = "movieNm";
        String QUERY_TEXT = "캡틴아메리카";

        SearchTemplateRequest searchRequest = new SearchTemplateRequest();
        searchRequest.setRequest(new SearchRequest(INDEX_NAME));

        searchRequest.setScriptType(ScriptType.INLINE);
        searchRequest.setScript(
                "{" +
                        "  \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } }," +
                        "  \"size\" : \"{{size}}\"" +
                        "}");

        Map<String, Object> scriptParams = new HashMap<>();
        scriptParams.put("field", FIELD_NAME);
        scriptParams.put("value", QUERY_TEXT);
        scriptParams.put("size", 10);
        searchRequest.setScriptParams(scriptParams);

        SearchTemplateResponse searchTemplateResponse = client.searchTemplate(searchRequest, RequestOptions.DEFAULT);
        SearchResponse response = searchTemplateResponse.getResponse();


        client.close();
    }
}
