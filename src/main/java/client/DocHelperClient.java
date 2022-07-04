package client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Cleanup;
import lombok.experimental.FieldDefaults;
import model.dto.clinical_recommendation.ClinicalRecommendationDto;
import model.dto.mkb.MkbDto;
import model.dto.clinical_recommendation_content.ClinicalRecommendationContentDto;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
public class DocHelperClient {
    OkHttpClient okHttpClient;
    ObjectMapper objectMapper;

    public MkbDto getMkbDtoByDisease(String disease) throws IOException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("nsi.rosminzdrav.ru")
                .addPathSegments("port/rest/data")
                .addQueryParameter("userKey", System.getenv("userKey"))
                .addQueryParameter("identifier", System.getenv("identifier"))
                .addQueryParameter("filters", "MKB_NAME|" + disease + "|LIKE")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();
        @Cleanup Response response = okHttpClient.newCall(request).execute();
        return objectMapper.readValue(response.body().string(), MkbDto.class);
    }

    public List<ClinicalRecommendationDto> getAvailableClinRecs() throws IOException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("apicr.minzdrav.gov.ru")
                .addPathSegments("api.ashx")
                .addQueryParameter("op", "GetJsonClinrecs")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        @Cleanup Response response = okHttpClient.newCall(request).execute();
        return objectMapper.readValue(response.body().string(),
                new TypeReference<List<ClinicalRecommendationDto>>() {
                });
    }

    public ClinicalRecommendationContentDto getClinRecById(String id) throws IOException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("apicr.minzdrav.gov.ru")
                .addPathSegments("api.ashx")
                .addQueryParameter("op", "GetClinrec")
                .addQueryParameter("id", id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        @Cleanup Response response = okHttpClient.newCall(request).execute();
        return objectMapper.readValue(response.body().string(), ClinicalRecommendationContentDto.class);
    }
}
