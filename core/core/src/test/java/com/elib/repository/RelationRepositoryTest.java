package com.elib.repository;

import com.elib.domain.Book;
import com.elib.domain.EbookService;
import com.elib.domain.Library;
import com.elib.domain.Relation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RelationRepositoryTest {

    @Autowired BookRepository bookRepository;
    @Autowired EbookServiceRepository ebookServiceRepository;
    @Autowired LibraryRepository libraryRepository;
    @Autowired RelationRepository relationRepository;
    @Autowired EntityManager em;

    @DisplayName("쿼리 한 번에 해당 도서관 Relation이 모두 삭제된다.")
    @Test
    void deleteByLibraryId() throws Exception {
        // Given
        Library library = Library.builder().name("은평구립도서관").build();
        libraryRepository.saveAndFlush(library);
        Relation relation1 = Relation.of(null, library, null);
        Relation relation2 = Relation.of(null, library, null);
        relationRepository.saveAndFlush(relation1);
        relationRepository.saveAndFlush(relation2);

        em.clear();
        // When
        relationRepository.deleteByLibraryId(library.getId());

        // Then
        List<Relation> all = relationRepository.findAll();
        assertThat(all).hasSize(0);
    }

    @Test
    void existsByBookAndLibraryAndEbookService() throws Exception {
        // Given
        Book book = Book.builder().build();
        bookRepository.saveAndFlush(book);
        Library library = Library.builder().name("정독").build();
        libraryRepository.saveAndFlush(library);
        EbookService service = EbookService.of("교보");
        ebookServiceRepository.saveAndFlush(service);

        Relation relation = Relation.of(book, library, service);
        relationRepository.saveAndFlush(relation);

        em.clear();

        // When
        boolean result = relationRepository.existsByBookAndLibraryAndEbookService(book, library, service);

        // Then
        assertThat(result).isTrue();
    }

}