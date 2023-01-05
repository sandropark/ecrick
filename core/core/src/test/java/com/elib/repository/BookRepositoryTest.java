package com.elib.repository;

import com.elib.domain.Book;
import com.elib.domain.Library;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired LibraryRepository libraryRepository;
    @Autowired BookRepository bookRepository;
    @Autowired EntityManager em;

    @DisplayName("해당 도서관id를 가진 책을 모두 삭제한다.")
    @Test
    void deleteByLibraryId() throws Exception {
        // Given
        Library library = Library.builder().name("중랑").build();
        libraryRepository.saveAndFlush(library);

        Book book1 = Book.builder().title("사피엔스").library(library).build();
        Book book2 = Book.builder().title("사피엔스").library(library).build();
        bookRepository.saveAndFlush(book1);
        bookRepository.saveAndFlush(book2);

        em.clear();

        // When
        bookRepository.deleteByLibraryId(library.getId());
        List<Book> all = bookRepository.findAll();

        // Then
        assertThat(all).hasSize(0);
    }

}