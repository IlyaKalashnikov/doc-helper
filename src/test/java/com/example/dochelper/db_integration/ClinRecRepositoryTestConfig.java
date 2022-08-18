package com.example.dochelper.db_integration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@TestConfiguration
@EnableJpaRepositories(basePackages = {"com.example.demo.dochelper.repository.clinrec_repository"})
@EntityScan(basePackages = {"com.example.demo.dochelper.model.entity"})
@EnableTransactionManagement
public class ClinRecRepositoryTestConfig {
}

