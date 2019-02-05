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
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class Example25 {

    /**
     *  Aggregation 정렬하기
     * */
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));
        String INDEX_NAME = "movie_search";
        String TYPE_NAME = "_doc";
        String FIELD_NAME = "repNationNm";
        String QUERY = "한국";

        String AGGREGATION_NAME="order_aggregation";
        String AGGREGATION_FIELD_FOR_USER = "movieCd";

        //Aggregation된 문서 수를 기준으로 정렬
        AggregationBuilder aggs = AggregationBuilders
                .terms(AGGREGATION_NAME)
                .field(AGGREGATION_FIELD_FOR_USER)
                .order(BucketOrder.count(true));

        SearchResponse response = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery(FIELD_NAME, QUERY))
                .addAggregation(aggs)
                .setSize(0)
                .get();


//Aggregation된 내용의 키값의 이름을 기준으로 정렬
        AggregationBuilder aggs2 =AggregationBuilders
                .terms(AGGREGATION_NAME)
                .field(AGGREGATION_FIELD_FOR_USER)
                .order(BucketOrder.key(true));

        SearchResponse response2 = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery(FIELD_NAME, QUERY))
                .addAggregation(aggs2)
                .setSize(0)
                .get();









    }
}
