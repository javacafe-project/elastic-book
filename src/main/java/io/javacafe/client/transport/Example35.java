package io.javacafe.client.transport;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * DISMAX QUERY API
 * */
public class Example35 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));

        String INDEX_NAME = "movie_search";

        String FIELD_NAME1 = "repNationNm";
        String QUERY_KEYWORD1 = "한국";

        String FIELD_NAME2 = "repGenreNm";
        String QUERY_KEYWORD2="드라마";

        QueryBuilder queryBuilder = QueryBuilders
                .disMaxQuery()
                .add(termQuery(FIELD_NAME1, QUERY_KEYWORD1))
                .add(termQuery(FIELD_NAME2, QUERY_KEYWORD2))
                .boost(1.5f)
                .tieBreaker(0.5f);

        SearchResponse response = client.prepareSearch(INDEX_NAME)
                .setQuery(queryBuilder)
                .setSize(30).get();

      

    }
}
