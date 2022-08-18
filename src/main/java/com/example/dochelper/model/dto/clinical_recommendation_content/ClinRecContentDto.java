package com.example.dochelper.model.dto.clinical_recommendation_content;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClinRecContentDto {
    int db_id;
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
    TableOfContents tableOfContents;
    int ver;
    boolean adult;
    boolean child;
    @JsonProperty("NPC_approved")
    boolean nPC_approved;
    ArrayList<Integer> proff_associations;
    ArrayList<Object> approved;
    Date publish_date;
    ArrayList<Object> tablesArray;
    ArrayList<Object> tables;

    public void setName(String name) {
        this.name = name;
    }
}