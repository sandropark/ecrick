package com.elib.crawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.elib")
public class CrawlerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }

}
