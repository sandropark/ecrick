package com.elib.crawler.service;

import com.elib.crawler.repository.CrawlerBookRepository;
import com.elib.crawler.repository.CrawlerCoreRepository;
import com.elib.domain.Core;
import com.elib.dto.CoreDto;
import com.elib.repository.BookRepository;
import com.elib.repository.CoreRepository;
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
        crawlerBookRepository.insertFromCore();

        // 2. core와 book 조인 후 core에 bookId 업데이트. 연관관계가 없는 core만 적용
        crawlerCoreRepository.updateBookIdAll();
    }

    @Transactional
    public void reduceDuplicateFromDate() {
        // TODO : 수정하기
        // 1. 중복 데이터를 가진 코어를 모두 가져온다.

        // 2. 연관관계를 통일한다.
//        Set<Book> oldBooks = cleanDbService.updateToLatestBook();

        // 3. 연관관계를 잃은 (출간일이 최신이 아닌) Book을 모두 삭제한다. 이로써 중복이 제거되었다.
//        bookRepository.deleteAll(oldBooks);
    }
}
