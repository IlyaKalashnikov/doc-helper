package com.example.demo.configuration;

import com.example.demo.mapper.DocHelperMapper;
import com.example.demo.model.dto.clinical_recommendation.ClinicalRecommendationDto;
import com.example.demo.model.dto.mkb.MkbDto;
import com.example.demo.model.entity.ClinicalRecommendationEntity;
import com.example.demo.model.entity.MkbEntity;
import com.example.demo.repository.clinrec_repository.ClinRecRepository;
import com.example.demo.repository.mkb_repository.MkbRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DocHelperConfig {

    @Bean
    CommandLineRunner commandLineRunner(
            MkbRepository mkbRepository,
            ClinRecRepository clinRecRepository) {
        return args -> {
            List<ClinicalRecommendationEntity> clinRecEntityList = getClinRecEntityList ();
            List<MkbEntity> mkbEntityList = getMkbEntityList();
            mkbRepository.saveAll(mkbEntityList);
            clinRecRepository.saveAll(clinRecEntityList);
        };
    }

    private static List<MkbEntity> getMkbEntityList() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        DocHelperMapper docHelperMapper = new DocHelperMapper();
        File file = new File("src/main/resources/MKB_codes.json");

        MkbDto mkbDto = mapper.readValue(file, MkbDto.class);
        return docHelperMapper.mkbDtoToEntity(mkbDto);
    }

    private static List<ClinicalRecommendationEntity> getClinRecEntityList() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        DocHelperMapper docHelperMapper = new DocHelperMapper();
        File file = new File("src/main/resources/Clinical_recommendations.json");

        List<ClinicalRecommendationDto> clinicalRecommendationDtos = mapper.readValue(file,
                new TypeReference<List<ClinicalRecommendationDto>>() {
                });

        List<ClinicalRecommendationEntity> entityList = new ArrayList<>();
        for (ClinicalRecommendationDto dto : clinicalRecommendationDtos
        ) {
            ClinicalRecommendationEntity entity = docHelperMapper.clinRecDtoToEntity(dto);
            entityList.add(entity);
        }
        return entityList;
    }
}
