package com.procurement.point.config

import com.datastax.driver.core.Session
import com.procurement.point.infrastructure.metric.CassandraHealthIndicator
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories

@Configuration
@ComponentScan(basePackages = ["com.procurement.point.model.entity"])
@EnableCassandraRepositories(basePackages = ["com.procurement.point.repository"])
class CassandraConfig(private val session: Session) {
    @Bean
    fun cassandraHealthIndicator(): HealthIndicator {
        return CassandraHealthIndicator(session = session)
    }
}
