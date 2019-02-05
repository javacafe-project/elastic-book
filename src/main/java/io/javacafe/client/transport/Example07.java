package io.javacafe.client.transport;

import java.io.IOException;
import java.net.InetAddress;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * 하나 / 여러개의 문서 삭제
 * 
 * @author User
 *
 */
public class Example07 {


    @SuppressWarnings({ "resource", "unchecked" })
	public static void main(String[] args) throws IOException {
        Settings settings = Settings.builder() .put("cluster.name", "javacafe-es").build();

        TransportClient client =
                new PreBuiltTransportClient(settings)
                        .addTransportAddress(new TransportAddress(
                                InetAddress.getByName("127.0.0.1"), 9300));
        //Index명
        String INDEX_NAME="movie_auto_java";

        //타입명
        String TYPE_NAME = "_doc";
        
        
        // 한건의 문서 삭제
        String _id = "20184623";
        DeleteResponse response = client.prepareDelete(INDEX_NAME, TYPE_NAME, _id).get();

        
        // 여러건의 문서 삭제
        BulkByScrollResponse bulkByScrollResponse = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("movieNm", "바람난 아내들2"))
                .source(INDEX_NAME)
                .get();
        
        long deleted = bulkByScrollResponse.getDeleted();

    }
}
