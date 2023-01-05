package com.elib.service;

import com.elib.domain.*;
import com.elib.repository.BookRepository;
import com.elib.repository.LibraryRepository;
import com.elib.repository.RelationRepository;
import com.elib.repository.VendorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
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
    @Autowired VendorRepository vendorRepository;
    @Autowired RelationRepository relationRepository;
    @Autowired EntityManager em;

    @Disabled
    @DisplayName("도서관을 삭제하면 해당 도서관의 연관관계도 함께 삭제된다.")
    @Test
    void delete_cascade() throws Exception {
        // Given
        Library library = Library.builder().name("산들도서관").build();
        libraryRepository.saveAndFlush(library);
        Book book = Book.builder().title("사피엔스").build();
        bookRepository.saveAndFlush(book);
        Vendor vendor = Vendor.builder().name(VendorName.KYOBO).build();
        vendorRepository.saveAndFlush(vendor);
        Relation relation = Relation.of(book, library, vendor);
        relationRepository.saveAndFlush(relation);
        em.clear();

        // When
        libraryService.delete(library.getId());
        em.flush();
        em.clear();

        // Then
        Assertions.assertThat(relationRepository.findAll()).hasSize(0);
        Assertions.assertThat(libraryRepository.findAll()).hasSize(0);
    }
}