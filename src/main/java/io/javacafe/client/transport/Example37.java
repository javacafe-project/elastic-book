package io.javacafe.client.transport;

import static org.elasticsearch.index.query.QueryBuilders.geoBoundingBoxQuery;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * GEO QUERY API
 * */
public class Example37 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));

        String INDEX_NAME = "movie_search_datatype";
        String FIELD_NAME = "filmLocation";

        QueryBuilder queryBuilder = geoBoundingBoxQuery(FIELD_NAME)
                .setCorners(40.73, -74.1,
                        40.717, -73.99);

        SearchResponse response = client.prepareSearch(INDEX_NAME)
                .setQuery(queryBuilder)
                .setSize(30).get();





    }
}
