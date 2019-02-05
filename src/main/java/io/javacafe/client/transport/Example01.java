package io.javacafe.client.transport;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Transport 연결
 * 
 * @author User
 *
 */
public class Example01 {
	
    @SuppressWarnings({ "resource", "unchecked" })
	public static void main(String[] args) throws UnknownHostException {


        Settings settings = Settings.builder() .put("cluster.name", "elasticsearch").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));

        client.close();


    }
}
