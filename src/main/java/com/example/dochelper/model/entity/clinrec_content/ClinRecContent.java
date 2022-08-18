package com.example.dochelper.model.entity.clinrec_content;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Value
@Getter
@Builder
public class ClinRecContent {
    String title;
    String content;
}
