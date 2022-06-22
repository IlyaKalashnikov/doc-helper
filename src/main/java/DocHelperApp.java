import client.DocHelperClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import mapper.DocHelperMapper;
import okhttp3.OkHttpClient;
import service.DocHelperService;

import java.io.IOException;

public class DocHelperApp {
    public static void main(String[] args) {
        DocHelperService service = DocHelperService.builder()
                .docHelperClient(DocHelperClient.builder()
                        .okHttpClient(new OkHttpClient())
                        .objectMapper(new ObjectMapper())
                        .build())
                .docHelperMapper(new DocHelperMapper())
                .build();
        service.checkClinRecAvailability("L93");
    }
}

