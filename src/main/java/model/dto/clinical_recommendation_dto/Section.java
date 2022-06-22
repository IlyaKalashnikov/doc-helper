package model.dto.clinical_recommendation_dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Section {
    String content;
    @JsonProperty("content_index")
    int contentIndex;
    boolean found;
    @JsonProperty("donotsearch")
    boolean doNotSearch;
    String id;
    boolean required;
    String title;
    @JsonProperty("title_index")
    int titleIndex;
    ArrayList<Data> data;
    @JsonProperty("findrule")
    int findRule;
    int start;
    ArrayList<ArrayList<String>> rules;
}
