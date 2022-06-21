import client.DocHelperClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.dto.MkbDto;
import model.dto.MkbTableColumn;
import model.dto.clinical_recommendation_dto.ClinicalRecommendationDto;
import okhttp3.OkHttpClient;

import java.io.IOException;

public class DocHelperApp {
    public static void main(String[] args) throws IOException {
        DocHelperClient client = new DocHelperClient(new OkHttpClient(), new ObjectMapper());
        ClinicalRecommendationDto clinicalRecommendationDto = client.getClinRecById("736_1");
        System.out.println("Sucksass");
        System.out.println(clinicalRecommendationDto.getTableOfContents().getSections().get(15).getContent());
    }
}
