package com.rss.feed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RssFeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(RssFeedApplication.class, args);
    }
}
