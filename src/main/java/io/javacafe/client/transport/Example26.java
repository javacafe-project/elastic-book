package io.javacafe.client.transport;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.significant.SignificantTerms;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class Example26 {

    /**
     * Significant Aggregation 사용하기
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

        String AGGREGATION_NAME="significant_aggregation";
        String AGGREGATION_FIELD = "typeNm";


        AggregationBuilder aggregation =
                AggregationBuilders
                        .significantTerms(AGGREGATION_NAME)
                        .field(AGGREGATION_FIELD);

        SearchResponse sr = client.prepareSearch()
                .setQuery(QueryBuilders.termQuery(FIELD_NAME, QUERY))
                .addAggregation(aggregation)
                .get();
        SignificantTerms agg = sr.getAggregations().get(AGGREGATION_NAME);

        for (SignificantTerms.Bucket entry : agg.getBuckets()) {
            String typeNm = (String) entry.getKey();      // Term
            Long typeNmCount = entry.getDocCount(); // Doc count
        }

    }
}
