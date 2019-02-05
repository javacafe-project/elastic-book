package io.javacafe.client.rest;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;

public class Example08B {
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
         * [업데이트 요청2]
         * 
         * 문서의 부분을 업데이트 방식
         */
        XContentBuilder builder = jsonBuilder();
        builder.startObject();
        builder.field("createdAt", new Date());
        builder.field("prdtYear", "2019");
        builder.field("typeNm", "장편");
        builder.endObject();
        
        UpdateRequest request2 = new UpdateRequest(INDEX_NAME, TYPE_NAME, _id).doc(builder);

        try {
            UpdateResponse updateResponse = client.update(request2, RequestOptions.DEFAULT);        
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
            	System.out.println("업데이트 대상이 존재하지 않습니다.");
            }
        }


        client.close();
    }
}
