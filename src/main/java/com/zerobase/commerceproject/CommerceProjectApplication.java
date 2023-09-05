package com.zerobase.commerceproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
public class CommerceProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommerceProjectApplication.class, args);
    }

}
