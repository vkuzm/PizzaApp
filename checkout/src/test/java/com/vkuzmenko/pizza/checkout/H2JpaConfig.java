package com.vkuzmenko.pizza.checkout;

import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@TestPropertySource("classpath:application.yml")
@Transactional
//@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/import.sql")
public class H2JpaConfig {
}
