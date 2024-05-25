package com.ecrick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
public class CrawlerApp {

    @EventListener(ApplicationReadyEvent.class)
    public void timeZoneConfig() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        Locale.setDefault(Locale.KOREA);
    }

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApp.class, args);
    }

}
