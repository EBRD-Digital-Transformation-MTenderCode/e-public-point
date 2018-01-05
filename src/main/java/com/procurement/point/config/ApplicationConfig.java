package com.procurement.point.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.procurement.point.utils.DateUtil;
import com.procurement.point.utils.JsonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        CassandraConfig.class,
        ServiceConfig.class,
        WebConfig.class
})
public class ApplicationConfig {

    @Bean
    public DateUtil dateUtil() {
        return new DateUtil();
    }

    @Bean
    public JsonUtil jsonUtil(final ObjectMapper mapper) {
        return new JsonUtil(mapper);
    }
}
