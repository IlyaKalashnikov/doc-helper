package model.entity.clinical_recommendation_content;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import model.dto.clinical_recommendation_content.Obj;

import java.util.Date;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Builder
@Data
public class ClinicalRecommendationContentEntity {
    String id;
    String name;
    String mkb;
    @JsonProperty("publish_date")
    Date publishDate;
    Obj obj;
    Content content;
}
