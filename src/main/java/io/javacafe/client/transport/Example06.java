package io.javacafe.client.transport;

import java.io.IOException;
import java.net.InetAddress;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * 하나의 문서 조회
 * 
 * @author User
 *
 */
public class Example06 {


    @SuppressWarnings({ "resource", "unchecked" })
	public static void main(String[] args) throws IOException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));
        //Index명
        String INDEX_NAME="movie_auto_java";

        //타입명
        String TYPE_NAME = "_doc";
        
        //문서 키값
        String _id = "20184623";
        
        GetResponse response = client.prepareGet(INDEX_NAME, TYPE_NAME, _id).get();

    }
}
