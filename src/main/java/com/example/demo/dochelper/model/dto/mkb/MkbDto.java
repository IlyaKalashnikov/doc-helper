package com.example.demo.dochelper.model.dto.mkb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MkbDto {
    String result;
    String resultText;
    String resultCode;
    int total;
    final List<ArrayList<MkbDiseaseCodePair>> list = new ArrayList<>();
}
