package io.javacafe.client.rest;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Example_005 {
    /**
     * GET API
     * */
    public static void main(String[] args) throws IOException {
        
    	RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("127.0.0.1", 9200, "http")));

        // 인덱스명
        String INDEX_NAME = "movie_auto_java";
        
        // 타입명
        String TYPE_NAME="_doc";
        
        // 문서 키값
        String _id = "1";

        // 요청
        GetRequest request = new GetRequest( INDEX_NAME, TYPE_NAME, _id);

        // 응답 
        GetResponse response = client.get(request, RequestOptions.DEFAULT);

        // 응답의 결과를 Map 형태로 제공받는다.
        if (response.isExists()) {
            long version = response.getVersion();
            Map<String, Object> sourceAsMap = response.getSourceAsMap();
        
        } else {
        	System.out.println("결과가 존재하지 않습니다.");
        }

        client.close();
    }
}
