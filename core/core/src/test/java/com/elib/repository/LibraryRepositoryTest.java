package com.elib.repository;

import com.elib.domain.Book;
import com.elib.domain.EbookService;
import com.elib.domain.Library;
import com.elib.domain.Relation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
class LibraryRepositoryTest {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    LibraryRepository libraryRepository;
    @Autowired
    EbookServiceRepository ebookServiceRepository;
    @Autowired
    RelationRepository relationRepository;
    @Autowired EntityManager em;

    Library library;
    Library library2;

    @BeforeEach
    void setUp() {
        Book book1 = Book.builder().title("사피엔스").build();
        Book book2 = Book.builder().title("사피엔스2").build();
        bookRepository.save(book1);
        bookRepository.save(book2);

        library = Library.builder().name("산들도서관").build();
        library2 = Library.builder().name("들도서관").build();
        libraryRepository.save(library);
        libraryRepository.save(library2);

        EbookService ebookService = EbookService.of("교보");
        ebookServiceRepository.save(ebookService);

        Relation relation1 = Relation.of(book1, library, ebookService);
        Relation relation2 = Relation.of(book2, library, ebookService);
        Relation relation3 = Relation.of(book2, library2, ebookService);
        relationRepository.save(relation1);
        relationRepository.save(relation2);
        relationRepository.save(relation3);
    }

    @DisplayName("도서관 id에 해당하는 도서관의 저장된 도서수를 업데이트한다.")
    @Test
    void updateSavedBooks() throws Exception {
        // When
        libraryRepository.updateSavedBooks(library.getId());

        em.flush();
        em.clear();

        // Then
        Library findLibrary = libraryRepository.findById(library.getId())
                .orElseThrow(IllegalArgumentException::new);

        assertThat(findLibrary.getSavedBooks()).isEqualTo(2);
    }

}