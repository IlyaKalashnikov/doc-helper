package com.example.dochelper.service_test;

import com.example.dochelper.model.entity.ClinicalRecommendationEntity;
import com.example.dochelper.repository.clinrec_repository.ClinRecRepository;
import com.example.dochelper.service.ClinRecService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        ClinRecService.class
})
class ClinRecServiceTest {
    @Autowired
    private ClinRecService clinRecService;

    @MockBean
    private ClinRecRepository clinRecRepository;

    ClinicalRecommendationEntity clinicalRecommendation1 = new ClinicalRecommendationEntity();
    ClinicalRecommendationEntity clinicalRecommendation2 = new ClinicalRecommendationEntity();
    ClinicalRecommendationEntity clinicalRecommendation3 = new ClinicalRecommendationEntity();
    Iterable<ClinicalRecommendationEntity> entities =
            List.of(clinicalRecommendation1,
                    clinicalRecommendation2,
                    clinicalRecommendation3);

    @Test
    public void serviceShouldReturnEntitiesWithRelevantMkbFields() {
        clinicalRecommendation1.setMkb(new ArrayList<>(List.of("111")));
        clinicalRecommendation2.setMkb(new ArrayList<>(List.of("111", "222")));
        clinicalRecommendation3.setMkb(new ArrayList<>(List.of("333")));

        Mockito.when(clinRecRepository.findAll()).thenReturn(entities);
        List<ClinicalRecommendationEntity> foundList = clinRecService.findClinRecByMkb("111");

        Assertions.assertAll("Should return List of clinRec1 & clinRec2",
                () -> assertTrue(foundList.contains(clinicalRecommendation1)),
                () -> assertTrue(foundList.contains(clinicalRecommendation2)),
                () -> assertEquals(2, foundList.size())
        );
    }
}