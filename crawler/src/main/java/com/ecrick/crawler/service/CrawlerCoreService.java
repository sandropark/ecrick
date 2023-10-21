package com.ecrick.crawler.service;

import com.ecrick.crawler.repository.CrawlerBookRepository;
import com.ecrick.crawler.repository.CrawlerCoreRepository;
import com.ecrick.domain.Core;
import com.ecrick.dto.CoreDto;
import com.ecrick.repository.BookRepository;
import com.ecrick.repository.CoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CrawlerCoreService {
    private final CrawlerCoreRepository crawlerCoreRepository;
    private final CrawlerBookRepository crawlerBookRepository;
    private final CoreRepository coreRepository;
    private final BookRepository bookRepository;

    public Map<String, Long> getTotalCoreAndBook() {
        Map<String, Long> totalMap = new HashMap<>();
        totalMap.put("core", coreRepository.count());
        totalMap.put("book", bookRepository.count());
        return totalMap;
    }

    @Transactional
    public void saveAll(List<CoreDto> coreDtos) {
        ArrayList<Core> cores = new ArrayList<>();
        coreDtos.forEach(coreDto -> cores.add(coreDto.toEntity()));
        crawlerCoreRepository.saveAll(cores);
    }

    @Transactional
    public void insertBooksFromCoreAndSetBookId() {
        // 1. core에서 책 뽑아서 저장 (출간일은 가장 최신만 유지)
        // bookDB에 제목,저자,출판사를 유니크키를 걸어둬서 중복 데이터는 저장되지 않고 출간일은 최신으로 업데이트 됨.
        crawlerBookRepository.insertBookFromCore();
        crawlerCoreRepository.mapCoreAndBookIfCore_BookIdIsNull();
    }

}
