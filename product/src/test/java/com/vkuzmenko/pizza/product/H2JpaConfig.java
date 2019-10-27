package com.vkuzmenko.pizza.product;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@ActiveProfiles("test")
@TestPropertySource("classpath:application.yml")
@Transactional
//@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/import.sql")
public class H2JpaConfig {
}
