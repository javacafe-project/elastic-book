package io.javacafe.client.transport;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * 쿼리 문서 조회
 * 
 * @author User
 *
 */
public class Example11 {

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));
        
        String INDEX_NAME1 = "movie_auto";
        String INDEX_NAME2 = "movie_search";
        String TYPE_NAME = "_doc";
        String FIELD_NAME = "movieCd";
        String QUERY = "20184623";

        SearchResponse response = client.prepareSearch(INDEX_NAME1, INDEX_NAME2)
                .setTypes(TYPE_NAME)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery(FIELD_NAME, QUERY))
                .setFrom(0).setSize(10)
                .setExplain(true)
                .get();

    }
}
