package com.yu.yurentcar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class YUrentcarApplication {

    public static void main(String[] args) {
        SpringApplication.run(YUrentcarApplication.class, args);
    }

}
