package com.ecrick.crawler.domain.service;

import com.ecrick.crawler.domain.Crawler;
import com.ecrick.domain.repository.LibraryRepository;
import com.ecrick.domain.service.LibraryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class CrawlerService implements Runnable {

    private final LibraryService libraryService;
    private final LibraryRepository libraryRepository;
    private final ObjectProvider<Crawler> crawlerProvider;
    private Long libraryId;
    private int threadNum;
    private int sleepTime;

    public void init(Long libraryId, int threadNum, int sleepTime) {
        this.libraryId = libraryId;
        this.threadNum = threadNum;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
//        LibraryCrawlerDto libraryDto = libraryRepository.findById(libraryId)
//                .map(LibraryCrawlerDto::from)
//                .orElseThrow(() -> new EntityNotFoundException("도서관을 찾을 수 없습니다. libraryId = " + libraryId));
//
//        log.info("{} 크롤링 시작", libraryDto.getName());
//
//        CrawlerUtil.getResponseDto(libraryDto).ifPresent(responseDto -> {
//            libraryService.updateTotalBooks(libraryDto.getId(), responseDto.getTotalBooks());
//            crawl(libraryDto);
//        });
    }

//    private void crawl(LibraryCrawlerDto libraryDto) {
//        ExecutorService es = Executors.newFixedThreadPool(threadNum);
//        libraryDto.getDetailUrls().forEach(url ->
//                es.submit(crawlerProvider.getObject().init(url, libraryDto, sleepTime)));
//    }

}
