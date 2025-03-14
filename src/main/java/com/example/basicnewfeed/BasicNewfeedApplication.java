package com.example.basicnewfeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BasicNewfeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicNewfeedApplication.class, args);
    }

}
