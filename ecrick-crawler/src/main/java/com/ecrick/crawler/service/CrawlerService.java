package com.ecrick.crawler.service;

import com.ecrick.crawler.domain.webclient.CrawlResult;
import com.ecrick.crawler.domain.webclient.WebClient;
import com.ecrick.crawler.service.port.RowBookRepository;
import com.ecrick.entity.Library;
import com.ecrick.repository.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CrawlerService {
    private final LibraryRepository libraryRepository;
    private final WebClient webClient;
    private final RowBookRepository rowBookRepository;

    public void crawl(Long libraryId) {
        // 1. 도서관 조회 (infra)
        Library library = libraryRepository.findById(libraryId).orElseThrow();

        // 요청을 보내고 응답을 받아온다. (infra)
        CrawlResult crawlResult = webClient.execute(library, 3);

        // 저장 (infra)
//        rowBookRepository.saveAll(rowBooks);
    }

}
