package com.elib.crawler.service;

import com.elib.crawler.repository.JdbcTemplateBookRepository;
import com.elib.domain.Book;
import com.elib.domain.Library;
import com.elib.repository.LibraryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CrawlerBookServiceTest {

    @Autowired JdbcTemplateBookRepository jdbcTemplateBookRepository;
    @Autowired LibraryRepository libraryRepository;
    @Autowired EntityManager em;

    @Test
    void saveAll() throws Exception {
        // Given
        Library library = Library.builder().name("중구").build();
        libraryRepository.saveAndFlush(library);

        em.clear();

        Book book1 = Book.builder().title("사피엔스").library(library).build();
        Book book2 = Book.builder().title("총균쇠").library(library).build();

        List<Book> books = List.of(book1, book2);

        // When
        jdbcTemplateBookRepository.saveAll(books);

        // Then

    }
}