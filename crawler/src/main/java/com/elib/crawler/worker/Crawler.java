package com.elib.crawler.worker;

import com.elib.crawler.dto.LibraryCrawlerDto;
import com.elib.crawler.service.CrawlerCoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.elib.crawler.worker.CrawlerUtil.getResponseDto;

@Slf4j
@RequiredArgsConstructor
@Scope("prototype")
@Component
public class Crawler implements Runnable {

    private final CrawlerCoreService crawlerCoreService;
    private String url;
    private LibraryCrawlerDto libraryDto;
    private int sleepTime;

    public Crawler init(String url, LibraryCrawlerDto libraryDto, int sleepTime) {
        this.url = url;
        this.libraryDto = libraryDto;
        this.sleepTime = sleepTime * 1000;
        return this;
    }

    @Override
    public void run() {
        log.info("크롤링 시작 {} url = {}", libraryDto.getName(), url);

        sleep();

        getResponseDto(url, libraryDto).ifPresent(responseDto ->
            crawlerCoreService.saveAll(responseDto.toCoreDtos(libraryDto.toEntity())));

        log.info("크롤링 종료 {} url = {}", libraryDto.getName(), url);
    }

    private void sleep() {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}