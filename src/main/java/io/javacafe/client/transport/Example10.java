package io.javacafe.client.transport;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * BulkProcessor 이용하여 문서 추가하기
 * 
 * @author User
 *
 */
public class Example10 {

    @SuppressWarnings({ "resource", "unchecked" })
	public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));
 
        //Index명
        String INDEX_NAME="movie_auto_java";

        //타입명
        String TYPE_NAME = "_doc";

        BulkProcessor bulkProcessor = BulkProcessor.builder(
                client,
                new BulkProcessor.Listener() {
                    public void beforeBulk(long executionId,
                                          BulkRequest request) {  }

                    public void afterBulk(long executionId,
                                          BulkRequest request,
                                          BulkResponse response) { }

                    public void afterBulk(long executionId,
                                          BulkRequest request,
                                          Throwable failure) {  }
                })
                .setBulkActions(1000)
                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
                .setConcurrentRequests(1)
                .build();

        //벌크 데이터 추가
        bulkProcessor.add(new IndexRequest(INDEX_NAME, TYPE_NAME, "20184623")
                .source(jsonBuilder()
                        .startObject()
                        .field("movieCd", "20184623")
                        .field("movieNm", "바람난 아내들2")
                        .field("movieNmEn", "")
                        .endObject()));

        bulkProcessor.add(new IndexRequest(INDEX_NAME, TYPE_NAME, "20174244")
                .source(jsonBuilder()
                        .startObject()
                        .field("movieCd", "20174244")
                        .field("movieNm", "버블 패밀리")
                        .field("movieNmEn", "Family in the Bubble")
                        .endObject()));


    }
}
