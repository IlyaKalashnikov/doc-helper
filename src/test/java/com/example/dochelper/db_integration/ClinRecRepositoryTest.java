package com.example.dochelper.db_integration;

import com.example.dochelper.config.RepositoryTestConfig;
import com.example.dochelper.model.entity.ClinicalRecommendationEntity;
import com.example.dochelper.repository.clinrec_repository.ClinRecRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.StreamSupport;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        ClinRecRepository.class,
        RepositoryTestConfig.class
})
@TestPropertySource(locations = {
        "classpath:application_test.properties"
})
@AutoConfigureDataJpa
public class ClinRecRepositoryTest extends PostgreSQLContainerConfig{

    @Autowired
    private ClinRecRepository clinRecRepository;
    @Test
    public void containerTest(){
        Assertions.assertTrue(postgreSQLContainer.isRunning());
    }

    @Test
    public void repoBeanCreated(){
        Assertions.assertNotNull(clinRecRepository);
    }

    @Test
    @Transactional
    public void repoShouldReturnRelevantEntity(){
        List<ClinicalRecommendationEntity> actualList =
                StreamSupport.stream(clinRecRepository.findAll().spliterator(), false)
                .toList();
        assertThat(actualList).isNotEmpty();
    }
}
