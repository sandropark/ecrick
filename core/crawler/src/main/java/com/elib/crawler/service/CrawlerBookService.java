package com.elib.crawler.service;

import com.elib.crawler.repository.JdbcTemplateBookRepository;
import com.elib.domain.Book;
import com.elib.dto.BookDto;
import com.elib.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CrawlerBookService {
    private final BookRepository bookRepository;
    private final JdbcTemplateBookRepository batchBookRepository;


    // TODO : 배치 인서트 확인하기
    // TODO : 저장시 중복 효율적으로 다루기

    public int save(List<BookDto> bookDtos) {
        ArrayList<Book> books = new ArrayList<>();

        for (BookDto bookDto : bookDtos) {
            Book book = bookDto.toEntity();
            if (bookRepository.notExist(book)) {
                books.add(book);
            }
        }

        batchInsert(books);
        return bookDtos.size();
    }

    @Transactional
    private void batchInsert(List<Book> books) {
        batchBookRepository.saveAll(books);
    }
}
