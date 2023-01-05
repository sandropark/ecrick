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
class LibraryRepositoryTest {

    @Autowired BookRepository bookRepository;
    @Autowired LibraryRepository libraryRepository;
    @Autowired EntityManager em;

    @DisplayName("도서관 id에 해당하는 도서관의 저장된 도서수를 업데이트한다.")
    @Test
    void updateSavedBooks() throws Exception {
        // Given
        Library library = Library.builder().name("산들도서관").build();
        libraryRepository.save(library);

        Book book = Book.builder().title("사피엔스").library(library).build();
        bookRepository.save(book);

        em.clear();

        // When
        libraryRepository.updateSavedBooks(library.getId());

        // Then
        Library findLibrary = libraryRepository.findById(library.getId())
                .orElseThrow(IllegalArgumentException::new);

        assertThat(findLibrary.getSavedBooks()).isEqualTo(1);
    }

    @DisplayName("모든 도서관의 저장된 도서수를 업데이트한다.")
    @Test
    void updateAllSavedbooks() throws Exception {
        // Given
        Library library1 = Library.builder().name("중랑").build();
        Library library2 = Library.builder().name("은평").build();
        libraryRepository.saveAndFlush(library1);
        libraryRepository.saveAndFlush(library2);

        Book book1 = Book.builder().title("사피엔스").library(library1).build();
        Book book2 = Book.builder().title("총균쇠").library(library2).build();
        Book book3 = Book.builder().title("토지").library(library2).build();
        bookRepository.saveAndFlush(book1);
        bookRepository.saveAndFlush(book2);
        bookRepository.saveAndFlush(book3);

        em.clear();

        // When
        libraryRepository.updateAllSavedBooks();
        List<Library> all = libraryRepository.findAll();

        // Then
        assertThat(all.get(0).getSavedBooks()).isEqualTo(1);
        assertThat(all.get(1).getSavedBooks()).isEqualTo(2);
    }

}