package io.javacafe.client.transport;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.net.InetAddress;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * 하나의 문서 추가
 * 
 * @author User
 *
 */
public class Example04 {


    @SuppressWarnings({ "resource", "unchecked" })
	public static void main(String[] args) throws IOException {

    	Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));

        // Index명
        String INDEX_NAME="movie_auto_java";

        // 타입명
        String TYPE_NAME = "_doc";

        // 문서 키값
        String _id = "1";

        
        IndexResponse response = client.prepareIndex(INDEX_NAME, TYPE_NAME, _id)
                .setSource(jsonBuilder()
                        .startObject()
                        .field("movieCd", "20173732")
                        .field("movieNm", "살아남은 아이")
                        .field("movieNmEn", "Last Child")
                        .endObject())
                .get();


        client.close();

    }
}
