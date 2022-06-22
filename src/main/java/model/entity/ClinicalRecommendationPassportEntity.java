package model.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ClinicalRecommendationPassportEntity {
    String id;
    String name;
    ArrayList<String> mkb;
    Date publishDate;
    String author;
}
