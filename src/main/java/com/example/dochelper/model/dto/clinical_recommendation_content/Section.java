package com.example.dochelper.model.dto.clinical_recommendation_content;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Section {
    String content;
    int content_index;
    boolean found;
    boolean donotsearch;
    String id;
    boolean required;
    String title;
    int title_index;
    ArrayList<Data> data;
    int findrule;
    int start;
    ArrayList<ArrayList<String>> rules;
}

