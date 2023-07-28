package com.ecrick.client;

import com.ecrick.dto.LibraryCrawlerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@RequiredArgsConstructor
//@Component
public class Crawler {

    private final RequestCrawler requestCrawler;
    private final Executor executor;

    public void crawl(LibraryCrawlerDto library) {
        // 1. url 생성
        // 2. url 리스트를 순회하면서 데이터를 요청 (병렬)
        log.info("{} 크롤링 시작", library.getName());
        library.getDetailUrls()
                .forEach(url -> CompletableFuture.runAsync(() -> requestCrawler.crawl(url, library), executor));
    }

}
