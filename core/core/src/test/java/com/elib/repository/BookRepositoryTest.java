package com.elib.repository;

import com.elib.domain.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class BookRepositoryTest {

    @Autowired BookRepository bookRepository;
    @Autowired EntityManager em;

    @DisplayName("제목,저자,출판사로 책 조회")
    @Test
    void findByTitleAndAuthorAndPublisher() throws Exception {
        // Given
        String title = "사피엔스";
        String author = "하라리";
        String publisher = "김영사";
        Book book = Book.builder().title(title).author(author).publisher(publisher).build();
        bookRepository.saveAndFlush(book);

        em.clear();

        // When
        Optional<Book> findBook = bookRepository.findByTitleAndAuthorAndPublisher(title, author, publisher);

        // Then
        assertThat(findBook.isPresent()).isTrue();
    }

    @DisplayName("반환 결과가 여러 개일 때는 예외가 발생한다.")
    @Test
    void findByTitleAndAuthorAndPublisher_exception() throws Exception {
        // Given
        String title = "사피엔스";
        String author = "하라리";
        String publisher = "김영사";
        Book book = Book.builder().title(title).author(author).publisher(publisher).build();
        bookRepository.saveAndFlush(book);

        Book book2 = Book.builder().title(title).author(author).publisher(publisher).build();
        bookRepository.saveAndFlush(book2);

        em.clear();

        // When & Then
        assertThatThrownBy(() -> bookRepository.findByTitleAndAuthorAndPublisher(title, author, publisher))
                .isInstanceOf(IncorrectResultSizeDataAccessException.class);
    }

}