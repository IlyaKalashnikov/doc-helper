package com.example.dochelper.model.dto.mkb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MkbDiseaseCodePair {
    String column;
    String value;
}
