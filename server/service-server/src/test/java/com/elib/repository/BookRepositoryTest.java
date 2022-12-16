package com.elib.repository;

import com.elib.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;
    @Autowired EntityManager em;

    @Test
    void unique() throws Exception {
        Book book1 = Book.of("사피엔스", "하라리", "믿음사", null, null);
        Book book2 = Book.of("사피엔스", "하라리", "믿음", null, null);

        bookRepository.save(book1);
        bookRepository.save(book2);

        em.flush();
        em.clear();

        List<Book> all = bookRepository.findAll();
        assertThat(all).hasSize(2);
    }

}