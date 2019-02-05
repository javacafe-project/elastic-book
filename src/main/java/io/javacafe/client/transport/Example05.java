package io.javacafe.client.transport;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;


/**
 * 대량의 문서 추가
 * 
 * @author User
 *
 */
public class Example05 {


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
        
        //여러개의 데이터를 저장할 XContentBuilder의 리스트 오브젝트 생성
        List<XContentBuilder> xContentBuilders = new ArrayList<XContentBuilder>();

        //1번 데이터 추가
        XContentBuilder builder = jsonBuilder().startObject()
                .field("movieCd", "20184623")
                .field("movieNm", "바람난 아내들2")
                .field("movieNmEn", "")
                .endObject();
        xContentBuilders.add(builder);

        //2번 데이터 추가
        builder = jsonBuilder().startObject()
                .field("movieCd", "20174244")
                .field("movieNm", "버블 패밀리")
                .field("movieNmEn", "Family in the Bubble")
                .endObject();
        xContentBuilders.add(builder);

        //BulkRequestBuilder 객체 생성
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        for(XContentBuilder xContentBuilder: xContentBuilders){
            bulkRequest.add(client.prepareIndex(INDEX_NAME,TYPE_NAME)
                    .setSource(xContentBuilder));
        }

        //문서 전송
        BulkResponse bulkResponse = bulkRequest.get();


        client.close();

    }
}
