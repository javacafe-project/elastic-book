package io.javacafe.client.transport;

import static org.elasticsearch.index.query.QueryBuilders.geoShapeQuery;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.ShapeRelation;
import org.elasticsearch.common.geo.builders.CoordinatesBuilder;
import org.elasticsearch.common.geo.builders.MultiPointBuilder;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.GeoShapeQueryBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.locationtech.jts.geom.Coordinate;


/**
 * GEO QUERY API
 * */
public class Example36 {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));

        String INDEX_NAME = "movie_search_datatype";
        String FIELD_NAME = "filmLocation";

        List<Coordinate> coodinates = new CoordinatesBuilder()        
	        .coordinate(0, 0)
	        .coordinate(0, 10)
	        .coordinate(10, 10)
	        .coordinate(10, 0)
	        .coordinate(0, 0)
	        .build();
        
        GeoShapeQueryBuilder queryBuilder = geoShapeQuery(
        		FIELD_NAME,                                      
                new MultiPointBuilder(coodinates)
        );
        
        queryBuilder.relation(ShapeRelation.WITHIN); 
        
        
        
        SearchResponse response = client.prepareSearch(INDEX_NAME)
                .setQuery(queryBuilder)
                .setSize(30).get();




    }
}
