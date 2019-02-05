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
 * BOOL QUERY API
 * */
public class Example34 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));

        String INDEX_NAME = "movie_search";
        String FIELD_NAME_MUST = "repNationNm";
        String QUERY_KEYWORD_MUST = "한국";

        String FIELD_NAME_MUST_NOT = "repGenreNm";
        String QUERY_KEYWORD_MUST_NOT="드라마";

        String FIELD_NAME_SHOULD = "prdtYear";
        String QUERY_KEYWORD_SHOULD="2017";

        String FIELD_NAME_FIELTER = "movieCd";
        String QUERY_KEYWORD_FIELTER="20173732";

        QueryBuilder queryBuilder = QueryBuilders
                .boolQuery()
                .must(termQuery(FIELD_NAME_MUST, QUERY_KEYWORD_MUST))
                .mustNot(termQuery(FIELD_NAME_MUST_NOT, QUERY_KEYWORD_MUST_NOT))
                .should(termQuery(FIELD_NAME_SHOULD, QUERY_KEYWORD_SHOULD))
                .filter(termQuery(FIELD_NAME_FIELTER, QUERY_KEYWORD_FIELTER));

        SearchResponse response = client.prepareSearch(INDEX_NAME)
                .setQuery(queryBuilder)
                .setSize(30).get();


    }
}
