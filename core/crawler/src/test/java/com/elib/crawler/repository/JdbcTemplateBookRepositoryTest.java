package com.elib.crawler.repository;

import com.elib.domain.Book;
import com.elib.domain.Core;
import com.elib.repository.BookRepository;
import com.elib.repository.CoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class JdbcTemplateBookRepositoryTest {

    @Autowired CoreRepository coreRepository;
    @Autowired JdbcTemplateBookRepository jdbcTemplateBookRepository;
    @Autowired BookRepository bookRepository;

    @DisplayName("Core의 데이터로 Book에 저장한다.")
    @Test
    void insertFromCore1() throws Exception {
        // Given
        String title = "토지";
        String author = "박경리";
        String publisher = "김영사";
        LocalDate publicDate = LocalDate.of(2018, 1, 1);
        String coverUrl = "naver.com";

        Core core = Core.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .publicDate(publicDate)
                .coverUrl(coverUrl)
                .build();
        coreRepository.saveAndFlush(core);

        // When
        jdbcTemplateBookRepository.insertFromCore();

        // Then
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(1);

        Book book = books.get(0);
        assertThat(book.getTitle()).isEqualTo(title);
        assertThat(book.getAuthor()).isEqualTo(author);
        assertThat(book.getPublisher()).isEqualTo(publisher);
        assertThat(book.getPublicDate()).isEqualTo(publicDate);
        assertThat(book.getCoverUrl()).isEqualTo(coverUrl);
    }

    @DisplayName("중복된 데이터는 출간일이 최신인 데이터만 저장한다.")
    @Test
    void insertFromCore2() throws Exception {
        // Given
        LocalDate latestPublicDate = LocalDate.of(2012, 1, 1);
        Core core1 = Core.builder().title("토지").publicDate(latestPublicDate).build();
        Core core2 = Core.builder().title("토지").publicDate(LocalDate.of(2000,1,1)).build();

        coreRepository.saveAllAndFlush(List.of(core1, core2));

        // When
        jdbcTemplateBookRepository.insertFromCore();

        // Then
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(1);

        Book book = books.get(0);
        assertThat(book.getPublicDate()).isEqualTo(latestPublicDate);
    }
}