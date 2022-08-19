package com.example.dochelper.db_integration;

import org.junit.ClassRule;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLContainerConfig {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14.4")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("12345");

}
