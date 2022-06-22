package model.entity.clinical_recommendation_content;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Builder
@Data
public class Chapter {
    String title;
    String text;
}
