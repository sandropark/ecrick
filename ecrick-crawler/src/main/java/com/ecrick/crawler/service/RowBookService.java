package com.ecrick.crawler.service;

import com.ecrick.crawler.service.port.BookRepository;
import com.ecrick.crawler.service.port.RowBookRepository;
import com.ecrick.dto.RowBookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class RowBookService {
    private final RowBookRepository rowBookRepository;
    private final BookRepository bookRepository;

    @Transactional
    public void deleteByLibrary(Long libraryId) {
        rowBookRepository.deleteByLibraryId(libraryId);
    }

    public Map<String, Long> getTotalCoreAndBook() {
        Map<String, Long> totalMap = new HashMap<>();
        totalMap.put("core", rowBookRepository.count());
        totalMap.put("book", bookRepository.count());
        return totalMap;
    }

    @Transactional
    public void saveAll(List<RowBookDto> rowBookDtos) {
        ArrayList<com.ecrick.entity.RowBook> rowBooks = new ArrayList<>();
        rowBookDtos.forEach(coreDto -> rowBooks.add(coreDto.toEntity()));
        rowBookRepository.saveAll(rowBooks);
    }

    @Transactional
    public void insertBooksFromCoreAndSetBookId() {
        // 1. core에서 책 뽑아서 저장 (출간일은 가장 최신만 유지)
        // bookDB에 제목,저자,출판사를 유니크키를 걸어둬서 중복 데이터는 저장되지 않고 출간일은 최신으로 업데이트 됨.
        bookRepository.insertBookFromCore();
        rowBookRepository.mapCoreAndBookIfCore_BookIdIsNull();
    }
}