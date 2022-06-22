package model.dto.clinical_recommendation_dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClinicalRecommendationDto {
    @JsonProperty("db_id")
    int dbId;
    int version;
    String id;
    String name;
    String mkb;
    Object ssid;
    Date created;
    ArrayList<Object> schemes;
    Object textBlock;
    Object textBlockCreatedBy;
    Object textBlockCreatedAt;
    int status;
    Object createdBy;
    int code;
    @JsonProperty("obj")
    Obj obj;
    int ver;
    boolean adult;
    boolean child;
    @JsonProperty("NPC_approved")
    boolean npcApproved;
    @JsonProperty("proff_associations")
    ArrayList<Integer> proffAssociations;
    ArrayList<Object> approved;
    @JsonProperty("publish_date")
    Date publishDate;
    ArrayList<Object> tablesArray;
    ArrayList<Object> tables;
}
