package com.example.demo.dochelper.client;

import com.example.demo.dochelper.model.dto.clinical_recommendation_content.ClinRecContentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class ClinRecContentClient {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public ClinRecContentDto getClinRecDtoById(String id) {
        HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https")
                .host("apicr.minzdrav.gov.ru")
                .addPathSegment("api.ashx")
                .addQueryParameter("op", "GetClinrec2")
                .addQueryParameter("id", id)
                .build();
        Request request = new Request.Builder()
                .url(httpUrl)
                .build();
        ClinRecContentDto result = null;
        try {
            Response response = client.newCall(request).execute();
            result = mapper.readValue(response.body().string(),
                    ClinRecContentDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
