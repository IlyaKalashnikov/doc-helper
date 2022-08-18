package com.example.dochelper.db_integration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@TestConfiguration
@EnableJpaRepositories(basePackages = {"com.example.dochelper.repository.clinrec_repository"})
@EntityScan(basePackages = {"com.example.dochelper.model.entity"})
@EnableTransactionManagement
public class ClinRecRepositoryTestConfig {
}

