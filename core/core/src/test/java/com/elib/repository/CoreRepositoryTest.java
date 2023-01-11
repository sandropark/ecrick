package com.elib.repository;

import com.elib.domain.Book;
import com.elib.domain.Core;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
class CoreRepositoryTest {

    @Autowired BookRepository bookRepository;
    @Autowired CoreRepository coreRepository;
    @Autowired EntityManager em;

    @DisplayName("bookId가 null인 core만 조회한다.")
    @Test
    void findAllDuplicatePublicDate() throws Exception {
        // Given
        Book book = Book.builder().title("총균쇠").build();
        bookRepository.saveAndFlush(book);

        Core core1 = Core.builder().title("토지").build();
        Core core2 = Core.builder().title("총균쇠").book(book).build();

        coreRepository.saveAllAndFlush(List.of(core1, core2));

        em.clear();

        // When & Then
        List<Core> newCores = coreRepository.findNewAll();
        assertThat(newCores).hasSize(1);
        Core findCore = newCores.get(0);
        assertThat(findCore).isEqualTo(core1);
    }

}