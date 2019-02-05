package io.javacafe.client.rest;

import static java.util.Collections.singletonMap;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

public class Example08A {
    /**
     * UPSERT API
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

        
        /**
         * [업데이트 요청1]
         * 
         * 스크립트를 이용한 업데이트 방식
         */
        UpdateRequest request1 = new UpdateRequest(INDEX_NAME, TYPE_NAME, _id);

        Map<String, Object> parameters = singletonMap("count", 10);
        Script inline = 
        		new Script(ScriptType.INLINE, "painless", "ctx._source.prdtYear += params.count", parameters);
        
        request1.script(inline);

        try {
            UpdateResponse updateResponse = client.update(request1, RequestOptions.DEFAULT);        
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
            	System.out.println("업데이트 대상이 존재하지 않습니다.");
            }
        }


        

        client.close();
    }
}
