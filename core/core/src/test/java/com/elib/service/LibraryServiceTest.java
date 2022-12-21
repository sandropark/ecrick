package com.elib.service;

import com.elib.domain.Book;
import com.elib.domain.EbookService;
import com.elib.domain.Library;
import com.elib.domain.Relation;
import com.elib.repository.BookRepository;
import com.elib.repository.EbookServiceRepository;
import com.elib.repository.LibraryRepository;
import com.elib.repository.RelationRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LibraryServiceTest {
    @Autowired LibraryService libraryService;
    @Autowired LibraryRepository libraryRepository;
    @Autowired BookRepository bookRepository;
    @Autowired EbookServiceRepository ebookServiceRepository;
    @Autowired RelationRepository relationRepository;
    @Autowired EntityManager em;

    @DisplayName("도서관을 삭제하면 해당 도서관의 연관관계도 함께 삭제된다.")
    @Test
    void delete_cascade() throws Exception {
        // Given
        Library library = Library.of("산들도서관");
        this.libraryRepository.saveAndFlush(library);
        Book book = Book.of("사피엔스");
        this.bookRepository.saveAndFlush(book);
        EbookService ebookService = EbookService.of("교보");
        this.ebookServiceRepository.saveAndFlush(ebookService);
        Relation relation = Relation.of(book, library, ebookService);
        this.relationRepository.saveAndFlush(relation);
        this.em.clear();

        // When
        this.libraryService.delete(library.getId());
        this.em.flush();
        this.em.clear();

        // Then
        Assertions.assertThat(this.relationRepository.findAll()).hasSize(0);
        Assertions.assertThat(this.libraryRepository.findAll()).hasSize(0);
    }
}