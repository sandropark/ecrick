package com.elib.crawler;

import com.elib.crawler.service.CrawlerBookService;
import com.elib.domain.Library;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.elib.crawler.CrawlerUtil.getResponseDto;

@Slf4j
@RequiredArgsConstructor
@Scope("prototype")
@Component
public class Crawler implements Runnable {

    private final CrawlerBookService crawlerBookService;
    private String url;
    private Library library;
    private int sleepTime;

    public void init(String url, Library library, int sleepTime) {
        this.url = url;
        this.library = library;
        this.sleepTime = sleepTime * 1000;
    }

    @Override
    public void run() {
        log.info("크롤링 시작 {} url = {}", library.getName(), url);

        sleep();

        getResponseDto(url, library).ifPresent(responseDto -> crawlerBookService.save(responseDto.toBookDtos(library)));

        log.info("크롤링 종료 {} url = {}", library.getName(), url);
    }

    private void sleep() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
