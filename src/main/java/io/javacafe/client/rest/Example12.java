package io.javacafe.client.rest;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
/**
 * SCROLL API
 * */
public class Example12 {
    public static void main(String[] args) throws IOException {
        
    	RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        
    	String INDEX_NAME = "movie_auto_java";
        String FIELD_NAME = "movieNm";
        String QUERY_TEXT = "캡틴아메리카";

        
        // 검색 쿼리 설정
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchQuery(FIELD_NAME, QUERY_TEXT));
       
        // 스크롤 생성
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1L));

        
        // 최초 요청
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll (scroll);

        
        // 최초 응답
        SearchResponse searchResponse = client.search(searchRequest,RequestOptions.DEFAULT);
        String scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        while (searchHits != null && searchHits.length > 0) {
            
        	// 다음 요청
        	SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
            scrollRequest.scroll(scroll);
            
            // 다음 응답
            searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();

        }

        client.close();
    }
}
