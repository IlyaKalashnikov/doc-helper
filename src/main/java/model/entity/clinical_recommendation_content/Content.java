package model.entity.clinical_recommendation_content;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Builder
@Data
public class Content {
    ArrayList<Chapter> chapters;
}
