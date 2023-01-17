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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CrawlerBookRepositoryTest {

    @Autowired CoreRepository coreRepository;
    @Autowired CrawlerBookRepository crawlerBookRepository;
    @Autowired BookRepository bookRepository;
    @Autowired EntityManager em;

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
        crawlerBookRepository.insertFromCore();

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
        crawlerBookRepository.insertFromCore();

        // Then
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(1);

        Book book = books.get(0);
        assertThat(book.getPublicDate()).isEqualTo(latestPublicDate);
    }

    @DisplayName("Book에 저장된 데이터와 제목,저자,출판사가 같다면 저장되지 않는다.")
    @Test
    void insertFromCore3() throws Exception {
        // Given
        String title = "토지";
        String author = "박경리";
        String publisher = "김영사";
        Book book = Book.builder().title(title).author(author).publisher(publisher).build();
        bookRepository.saveAndFlush(book);

        Core core = Core.builder().title(title).author(author).publisher(publisher).build();
        coreRepository.saveAndFlush(core);

        // When
        crawlerBookRepository.insertFromCore();

        // Then
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(1);
    }

    @DisplayName("Book에 저장된 데이터와 제목,저자,출판사가 같다면 출간일이 최신으로 업데이트된다.")
    @Test
    void insertFromCore4() throws Exception {
        // Given
        String title = "토지";
        String author = "박경리";
        String publisher = "김영사";
        LocalDate oldDate = LocalDate.of(1999, 1, 1);
        Book book = Book.builder().title(title).author(author).publisher(publisher).publicDate(oldDate).build();
        bookRepository.saveAndFlush(book);

        LocalDate newDate = LocalDate.of(2022, 1, 1);
        Core core1 = Core.builder().title(title).author(author).publisher(publisher).publicDate(oldDate).build();
        Core core2 = Core.builder().title(title).author(author).publisher(publisher).publicDate(newDate).build();
        coreRepository.saveAndFlush(core1);
        coreRepository.saveAndFlush(core2);

        em.clear();

        // When
        crawlerBookRepository.insertFromCore();

        // Then
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(1);

        Book savedBook = books.get(0);
        assertThat(savedBook.getPublicDate()).isEqualTo(newDate);
    }

}