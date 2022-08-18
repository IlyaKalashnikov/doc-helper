package com.example.dochelper.mapper;

import com.example.dochelper.model.dto.clinical_recommendation_content.ClinRecContentDto;
import com.example.dochelper.model.dto.mkb.MkbDiseaseCodePair;
import com.example.dochelper.model.dto.mkb.MkbDto;
import com.example.dochelper.model.entity.MkbEntity;
import com.example.dochelper.model.entity.clinrec_content.ClinRecContent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

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
    public Flux<ClinRecContent> clinRecContentDtoToEntity(Mono<ClinRecContentDto> dto) {
        return dto.map(clinRecContentDto ->
                        clinRecContentDto.getTableOfContents().getSections().stream()
                                .filter(section -> !section.getContent().isEmpty())
                                .map(section ->
                                        ClinRecContent.builder()
                                                .title(section.getTitle())
                                                .content(section.getContent())
                                                .build())
                                .toList())
                .flatMapMany(Flux::fromIterable);
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