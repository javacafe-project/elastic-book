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
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.min.Min;
import org.elasticsearch.search.aggregations.metrics.min.MinAggregationBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class Example14 {


    /**
     * MIN Aggregation 사용하기
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

        String AGGREGATION_NAME = "MIN_AGG_NM";
        String AGGREGATION_FIELD = "movieCd";
        
        MinAggregationBuilder aggs = AggregationBuilders.min(AGGREGATION_NAME)
                .field(AGGREGATION_FIELD);
        

        // 검색 결과를 Aggregation한다.
        SearchResponse response = client.prepareSearch(INDEX_NAME)
                .setTypes(TYPE_NAME)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.termQuery(FIELD_NAME, QUERY))
                .addAggregation(aggs)
                .setSize(0)
                .get();

        //결과 출력
        Min minAgg = response.getAggregations().get(AGGREGATION_NAME);
        double value = minAgg.getValue();


    }
}
