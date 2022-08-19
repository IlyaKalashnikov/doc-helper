package com.example.dochelper.db_integration;

import com.example.dochelper.config.RepositoryTestConfig;
import com.example.dochelper.model.entity.MkbEntity;
import com.example.dochelper.repository.mkb_repository.MkbRepository;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        MkbRepository.class,
        RepositoryTestConfig.class
})
@TestPropertySource(locations = "classpath:application_test.properties")
@AutoConfigureDataJpa
public class MkbRepositoryTest extends PostgreSQLContainerConfig {
    @Autowired
    private MkbRepository mkbRepository;

    @Test
    public void containerTest(){
        Assertions.assertTrue(postgreSQLContainer.isRunning());
    }

    @Test
    public void repoBeanCreated(){
        Assertions.assertNotNull(mkbRepository);
    }

    @Test
    @Transactional
    public void nativeQueryMethod_ShouldReturnRelevantEntity() {
        List<MkbEntity> entities = mkbRepository.findMkbByDisease("Саркоидоз");

        assertThat(entities).isNotEmpty();
        assertThat(entities.get(0).getDisease()).contains("саркоидоз");
    }
}
