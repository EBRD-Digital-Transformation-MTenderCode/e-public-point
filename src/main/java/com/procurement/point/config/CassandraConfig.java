package com.procurement.point.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@Configuration
@ComponentScan(basePackages = "com.procurement.point.model.entity")
@EnableCassandraRepositories(basePackages = "com.procurement.point.repository")
public class CassandraConfig {
}
