package com.elib.crawler.service;

import com.elib.crawler.repository.JdbcTemplateBookRepository;
import com.elib.domain.Book;
import com.elib.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CrawlerBookService {
    private final JdbcTemplateBookRepository batchBookRepository;

    @Transactional
    public void saveAll(List<BookDto> bookDtos) {
        ArrayList<Book> books = new ArrayList<>();
        bookDtos.forEach(bookDto -> books.add(bookDto.toEntity()));
        batchBookRepository.saveAll(books);
    }

}
