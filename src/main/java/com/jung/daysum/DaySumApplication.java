package com.jung.daysum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
@EnableJpaAuditing
public class DaySumApplication {

    public static void main(String[] args) {
        SpringApplication.run(DaySumApplication.class, args);
    }

}
