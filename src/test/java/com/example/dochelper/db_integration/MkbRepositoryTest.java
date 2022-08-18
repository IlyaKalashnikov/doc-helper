package com.example.dochelper.db_integration;

import com.example.dochelper.model.entity.MkbEntity;
import com.example.dochelper.repository.mkb_repository.MkbRepository;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        MkbRepository.class,
        MkbRepositoryTestConfig.class
})
@TestPropertySource(locations = "classpath:application_test.properties")
@AutoConfigureDataJpa
public class MkbRepositoryTest {
    @Autowired
    private MkbRepository mkbRepository;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14.4")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("12345");

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

        Assertions.assertTrue(!entities.isEmpty());
    }
}
