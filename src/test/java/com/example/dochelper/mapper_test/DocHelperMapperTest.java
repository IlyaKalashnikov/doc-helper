package com.example.dochelper.mapper_test;

import com.example.dochelper.mapper.DocHelperMapper;
import com.example.dochelper.model.dto.clinical_recommendation_content.ClinRecContentDto;
import com.example.dochelper.model.dto.clinical_recommendation_content.Section;
import com.example.dochelper.model.dto.clinical_recommendation_content.TableOfContents;
import com.example.dochelper.model.dto.mkb.MkbDiseaseCodePair;
import com.example.dochelper.model.dto.mkb.MkbDto;
import com.example.dochelper.model.entity.MkbEntity;
import com.example.dochelper.model.entity.clinrec_content.ClinRecContent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DocHelperMapper.class)
public class DocHelperMapperTest {
    @Autowired
    private DocHelperMapper docHelperMapper;

    @Test
    public void mkbDtoToEntity_ShouldReturnRelevantMkbEntityObject(){
        MkbDiseaseCodePair mkbDiseaseCodePair = MkbDiseaseCodePair.builder()
                .column("MKB_NAME")
                .value("Температура")
                .build();
        MkbDiseaseCodePair mkbDiseaseCodePair1 = MkbDiseaseCodePair.builder()
                .column("MKB_CODE")
                .value("112")
                .build();
        ArrayList<MkbDiseaseCodePair> mkbDiseaseCodePairList = new ArrayList<>();
        mkbDiseaseCodePairList.add(mkbDiseaseCodePair);
        mkbDiseaseCodePairList.add(mkbDiseaseCodePair1);

        MkbDto mkbDto = MkbDto.builder()
                .list(List.of(mkbDiseaseCodePairList))
                .build();

        List<MkbEntity> entity = docHelperMapper.mkbDtoToEntity(mkbDto);

        assertThat(entity).isNotEmpty();
        assertThat(entity.get(0).getCode()).contains("112");
        assertThat(entity.get(0).getDisease()).contains("Температура");
    }

    @Test
    public void clinRecContentDtoToEntity_ShouldReturnRelevantClinRecContentObject(){
        Section section = Section.builder()
                .content("Content")
                .title("Title")
                .build();
        TableOfContents tableOfContents = new TableOfContents(new ArrayList<>(List.of(section)));

        Mono<ClinRecContentDto> clinRecContentDto = Mono.just(ClinRecContentDto.builder()
                .tableOfContents(tableOfContents)
                .build());

        Flux<ClinRecContent> resultList = docHelperMapper.clinRecContentDtoToEntity(clinRecContentDto);

        assertThat(resultList).isNotNull();
        assertThat(resultList.toStream()
                .filter(clinRecContent -> clinRecContent.getTitle().contains("Title"))
                .collect(Collectors.toList())).isNotEmpty();
        assertThat(resultList.toStream()
                .filter(clinRecContent -> clinRecContent.getContent().contains("Content"))
                .collect(Collectors.toList())).isNotEmpty();
    }
}
