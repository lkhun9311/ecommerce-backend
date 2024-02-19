package com.ecommerce.membership.config.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan(basePackages = "com.ecommerce.db")
@EnableJpaRepositories(basePackages = "com.ecommerce.db")
public class JpaConfig {
}
