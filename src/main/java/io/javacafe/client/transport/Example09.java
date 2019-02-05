package io.javacafe.client.transport;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * 여러개의 검색 요청
 * 
 * @author User
 *
 */
public class Example09 {


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

        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
                .add(INDEX_NAME, TYPE_NAME, "20184623")
                .add(INDEX_NAME, TYPE_NAME, "20174244")
                .get();

        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            GetResponse response1 = itemResponse.getResponse();
            if (response1.isExists()) {
                String json = response1.getSourceAsString();
            }
        }


    }
}
