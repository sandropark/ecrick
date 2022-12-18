package com.elib.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.elib.domain.Book;
import com.elib.domain.EbookService;
import com.elib.domain.Library;
import com.elib.domain.Relation;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
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
        Book book1 = Book.of("사피엔스");
        Book book2 = Book.of("사피엔스2");
        bookRepository.save(book1);
        bookRepository.save(book2);

        library = Library.of("산들도서관");
        library2 = Library.of("들도서관");
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

    @DisplayName("모든 도서관의 저장된 도서수를 업데이트한다.")
    @Test
    void updateAllSavedBooks() throws Exception {
        // When
        libraryRepository.updateAllSavedBooks();

        em.flush();
        em.clear();

        // Then
        List<Library> all = libraryRepository.findAll();

        assertThat(all.get(0).getSavedBooks()).isEqualTo(2);
        assertThat(all.get(1).getSavedBooks()).isEqualTo(1);
    }
}