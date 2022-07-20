package com.example.demo.dochelper.mapper;

import com.example.demo.dochelper.model.dto.clinical_recommendation.ClinicalRecommendationDto;
import com.example.demo.dochelper.model.dto.clinical_recommendation_content.ClinRecContentDto;
import com.example.demo.dochelper.model.dto.mkb.MkbDiseaseCodePair;
import com.example.demo.dochelper.model.dto.mkb.MkbDto;
import com.example.demo.dochelper.model.entity.ClinicalRecommendationEntity;
import com.example.demo.dochelper.model.entity.MkbEntity;
import com.example.demo.dochelper.model.entity.clinrec_content.ClinRecContent;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DocHelperMapper {
    public List<MkbEntity> mkbDtoToEntity(MkbDto mkbDto) {
        return mkbDto.getList().stream()
                .map(mkbDiseaseCodePairs ->
                        MkbEntity.builder()
                                .code(getMkbCode(mkbDiseaseCodePairs))
                                .disease(getMkbName(mkbDiseaseCodePairs))
                                .build())
                .toList();
    }

    public ClinicalRecommendationEntity clinRecDtoToEntity(ClinicalRecommendationDto dto) {
        return ClinicalRecommendationEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .mkb(dto.getMkb())
                .publishDate(new Date(Long.parseLong(dto.getPublishDate().substring(6, 19))))
                .author(dto.getAss())
                .build();
    }

    public List<ClinRecContent> clinRecContentDtoToEntity(ClinRecContentDto dto) {
        return dto.getTableOfContents().getSections().stream()
                .filter(section -> !section.getContent().isEmpty())
                .map(section ->
                        ClinRecContent.builder()
                                .title(section.getTitle())
                                .content(section.getContent())
                                .build())
                .toList();
    }

    private static String getMkbName(List<MkbDiseaseCodePair> pairList) {
        String name = null;
        for (MkbDiseaseCodePair pair : pairList) {
            if (pair.getColumn().equalsIgnoreCase("MKB_NAME")) {
                name = pair.getValue();
            }
        }
        return name;
    }

    private static String getMkbCode(List<MkbDiseaseCodePair> pairList) {
        String code = null;
        for (MkbDiseaseCodePair pair : pairList) {
            if (pair.getColumn().equalsIgnoreCase("MKB_CODE")) {
                code = pair.getValue();
            }
        }
        return code;
    }
}