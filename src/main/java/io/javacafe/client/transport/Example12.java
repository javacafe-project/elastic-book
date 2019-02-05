package io.javacafe.client.transport;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * Scroll을 이용하여 검색하기
 * 
 * @author User
 *
 */
public class Example12 {


    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));
        
        String INDEX_NAME = "movie_search";
        String FIELD_NAME = "movieNm";
        String QUERY = "아내들";

        QueryBuilder queryBuilder = QueryBuilders.termQuery(FIELD_NAME, QUERY);
        SearchResponse scrollResp = client.prepareSearch(INDEX_NAME)
                .addSort(FieldSortBuilder.DOC_FIELD_NAME, SortOrder.ASC)
                .setScroll(new TimeValue(60000))
                .setQuery(queryBuilder)
                .setSize(30)
                .get();

        
        //해당 스크롤을 이용하여 검색
        do {

            //데이터 출력
            for (SearchHit hit : scrollResp.getHits().getHits()) {

                String movieNm= hit.getSourceAsMap().get("movieNm").toString();

            }

            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId())
                    .setScroll(new TimeValue(60000)).execute().actionGet();
            
        } while(scrollResp.getHits().getHits().length != 0);


    }
}
