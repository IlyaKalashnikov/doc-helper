package model.dto.clinical_recommendations_passport_dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClinicalRecommendationPassportDto {
    String id;
    int status;
    @JsonProperty("db_id")
    int dbId;
    int code;
    int version;
    int ver;
    int age;
    String name;
    @JsonProperty("Mkb")
    ArrayList<String> mkb;
    String ass;
    @JsonProperty("publish_date")
    String publishDate;
    @JsonProperty("NPC_approved")
    boolean npcApproved;
}
