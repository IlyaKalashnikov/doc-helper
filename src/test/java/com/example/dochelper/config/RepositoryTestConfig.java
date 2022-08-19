package com.example.dochelper.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@TestConfiguration
@EnableJpaRepositories(basePackages = {"com.example.dochelper.repository"})
@EntityScan(basePackages = {"com.example.dochelper.model.entity"})
@EnableTransactionManagement
public class RepositoryTestConfig {
}

