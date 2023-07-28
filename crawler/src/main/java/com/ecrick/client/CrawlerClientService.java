package com.ecrick.client;

import com.ecrick.repository.LibraryRepository;
import com.ecrick.dto.LibraryCrawlerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@RequiredArgsConstructor
//@Service
public class CrawlerClientService {

    private final LibraryRepository libraryRepository;
    private final Crawler crawler;
    private final Executor executor;
//    private final CrawlerLibraryRepository crawlerLibraryRepository;

    public void crawlAll() {
        // 1. 도서관 전체 조회 후 스트림으로 리스트를 병렬 처리
//        List<CompletableFuture<Void>> libraryFutures = crawlerLibraryRepository.findAll().stream()
        List<CompletableFuture<Void>> libraryFutures = libraryRepository.findAll().stream()
                .map(lib -> CompletableFuture.runAsync(() -> crawler.crawl(LibraryCrawlerDto.from(lib)), executor))
                .collect(Collectors.toList());

        CompletableFuture.allOf(libraryFutures.toArray(new CompletableFuture[0]))
                .thenApply(Void -> libraryFutures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()))
                .join();
    }

}
